package com.ticketmaster.user.infrastructure.adapters.output.kafka.producer;

import com.ticketmaster.user.application.ports.output.EventPublisherPort;
import com.ticketmaster.user.domain.event.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

import java.time.Duration;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventProducer implements EventPublisherPort {
    private final KafkaSender<String, Object> kafkaSender;

    @Value("${app.kafka.topics.user-created}")
    private String userCreatedEventTopic;

    @Override
    public Mono<Void> publishUserCreated(UserCreatedEvent event) {
        ProducerRecord<String, Object> register = new ProducerRecord<>(
                userCreatedEventTopic,
                event.userId().toString(),
                event
        );

        return kafkaSender
                .send(Mono.just(SenderRecord.create(register, event.userId())))
                .doOnNext(result -> log.info(
                        result.recordMetadata().topic(),
                        result.recordMetadata().partition(),
                        result.recordMetadata().offset()
                ))
                .doOnError(error -> log.error("Error publishing event to kafka"))
                .timeout(Duration.ofSeconds(5))
                .retry(2)
                .then();
    }
}
