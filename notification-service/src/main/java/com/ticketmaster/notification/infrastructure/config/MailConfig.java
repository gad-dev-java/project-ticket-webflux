package com.ticketmaster.notification.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {
    private static final String PROPERTY_VALUE_FALSE = "false";
    private static final String PROPERTY_MAIL_SMTP = "smtp";

    @Value("${spring.mail.host}")
    private String mailHost;

    @Value("${spring.mail.port}")
    private int mailPort;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(mailHost);
        mailSender.setPort(mailPort);
        mailSender.setProtocol(PROPERTY_MAIL_SMTP);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", PROPERTY_VALUE_FALSE);
        props.put("mail.smtp.starttls.enable", PROPERTY_VALUE_FALSE);
        props.put("mail.debug", PROPERTY_VALUE_FALSE);
        props.put("mail.transport.protocol", PROPERTY_MAIL_SMTP);
        return  mailSender;
    }
}
