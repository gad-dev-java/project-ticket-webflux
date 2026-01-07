package com.ticketmaster.notification.infrastructure.adapters.input.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticketmaster.notification.application.ports.input.NotificationStrategy;
import com.ticketmaster.notification.domain.event.UserCreatedEvent;
import com.ticketmaster.notification.domain.exception.EventProcessingException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverRecord;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class EventConsumer {
    private final KafkaReceiver<String, Object> kafkaReceiver;
    private final ObjectMapper objectMapper;
    private final Map<Class<?>, NotificationStrategy<?>> strategyMap;

    public EventConsumer(KafkaReceiver<String, Object> kafkaReceiver,
                         ObjectMapper objectMapper,
                         List<NotificationStrategy<?>> strategies) {
        this.kafkaReceiver = kafkaReceiver;
        this.objectMapper = objectMapper;
        this.strategyMap = strategies.stream()
                .collect(Collectors.toMap(NotificationStrategy::getEventType, Function.identity()));
    }

    @PostConstruct
    public void startConsuming() {
        kafkaReceiver.receive()
                .doOnNext(r -> log.info("Message received Topic: {}", r.topic()))
                .flatMap(this::processRecord)
                .retry()
                .subscribe();
    }

    private Mono<Void> processRecord(ReceiverRecord<String, Object> receiverRecord) {
        return Mono.defer(() -> {
            Class<?> targetClass = determinateEventClass(receiverRecord.topic());
            if (targetClass == null) {
                log.warn("No handler found for topic {}", receiverRecord.topic());
                receiverRecord.receiverOffset().acknowledge();
                return Mono.empty();
            }

            return handleEvent(receiverRecord, targetClass);
        });
    }

    private <T> Mono<Void> handleEvent(ReceiverRecord<String, Object> receiverRecord, Class<T> eventClass) {
        try {
            @SuppressWarnings("unchecked")
            NotificationStrategy<T> strategy = (NotificationStrategy<T>) strategyMap.get(eventClass);

            if (strategy == null) {
                log.warn("No handler found for event type {}", eventClass.getSimpleName());
                receiverRecord.receiverOffset().acknowledge();
                return Mono.empty();
            }

            T event = objectMapper.convertValue(receiverRecord.value(), eventClass);

            return strategy.handle(event)
                    .doOnSuccess(v -> {
                        log.info("Successfully processed event for topic: {}", receiverRecord.topic());
                        receiverRecord.receiverOffset().acknowledge();
                    })
                    .doOnError(e -> log.error("Error handling event from topic {}: {}",
                            receiverRecord.topic(), e.getMessage(), e))
                    .onErrorResume(e ->
                            Mono.error(new EventProcessingException(
                                    "Failed to process event from topic: " + receiverRecord.topic(), e))
                    );
        } catch (IllegalArgumentException e) {
            log.error("Failed to convert event from topic {}: {}",
                    receiverRecord.topic(), e.getMessage(), e);
            receiverRecord.receiverOffset().acknowledge();
            return Mono.empty();
        }
    }

    private Class<?> determinateEventClass(String topic) {
        if (topic.contains("user.created")) {
            return UserCreatedEvent.class;
        }
        return null;
    }
}
