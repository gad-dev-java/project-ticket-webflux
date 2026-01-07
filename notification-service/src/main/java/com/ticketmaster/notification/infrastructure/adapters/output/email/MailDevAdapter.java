package com.ticketmaster.notification.infrastructure.adapters.output.email;

import com.ticketmaster.notification.application.ports.output.EmailPort;
import com.ticketmaster.notification.domain.exception.EmailSendException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Component
@RequiredArgsConstructor
public class MailDevAdapter implements EmailPort {
    private final JavaMailSender mailSender;

    @Override
    public Mono<Void> sendEmail(String to, String subject, String content) {
        return Mono.fromRunnable(() -> {
                    if (to == null || to.trim().isEmpty()) {
                        log.warn("Cannot send email: Recipient address is NULL or Empty");
                        throw new EmailSendException("Recipient address cannot be null or empty");
                    }
                    try {
                        MimeMessage message = mailSender.createMimeMessage();
                        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
                        helper.setTo(to);
                        helper.setSubject(subject);
                        helper.setText(content, true);
                        helper.setFrom("no-reply@ticketmaster-clone.com");
                        mailSender.send(message);
                        log.info("Email sent to {} via MailDev", to);
                    } catch (MessagingException e) {
                        log.error(e.getMessage(), e);
                        throw new EmailSendException("Failed to send email to: " + to, e);
                    }
                })
                .subscribeOn(Schedulers.boundedElastic())
                .then();
    }
}
