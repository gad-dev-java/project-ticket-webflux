package com.ticketmaster.notification.application.service;

import com.ticketmaster.notification.application.ports.input.NotificationStrategy;
import com.ticketmaster.notification.application.ports.output.EmailPort;
import com.ticketmaster.notification.domain.event.UserCreatedEvent;
import com.ticketmaster.notification.infrastructure.adapters.output.email.ThymeleafTemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCreatedNotificationStrategy implements NotificationStrategy<UserCreatedEvent> {
    private final EmailPort emailPort;
    private final ThymeleafTemplateService templateService;

    @Override
    public Class<UserCreatedEvent> getEventType() {
        return UserCreatedEvent.class;
    }

    @Override
    public Mono<Void> handle(UserCreatedEvent event) {
        log.info("📢 Handling User Created Notification for: {}", event.email());

        Map<String,Object> variables = Map.of(
                "name", event.email(),
                "userId", event.userId()
        );

        String htmlContent = templateService.generateHtml("welcome-mail", variables);

        return emailPort.sendEmail(event.email(), "¡Bienvenido a TicketMaster Clone!", htmlContent);
    }
}
