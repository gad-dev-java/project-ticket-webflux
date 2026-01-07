package com.ticketmaster.user.infrastructure.adapters.output.kafka.producer;

import com.ticketmaster.user.application.ports.output.EventPublisherPort;
import com.ticketmaster.user.domain.event.DomainEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

import java.time.Duration;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventProducer implements EventPublisherPort {
    private final KafkaSender<String, Object> kafkaSender;
    private final Map<Class<? extends DomainEvent>, String> topicMap;

    @Override
    public Mono<Void> publish(DomainEvent event) {
        String topic = topicMap.get(event.getClass());

        if (topic == null) {
            return Mono.error(new IllegalArgumentException("No topic configured for event type " + event.getClass().getName()));
        }

        log.info("📤 Publishing event {} to topic {}", event.getClass().getSimpleName(), topic);

        ProducerRecord<String, Object> register = new ProducerRecord<>(
                topic,
                event.key(),
                event
        );

        return kafkaSender
                .send(Mono.just(SenderRecord.create(register, event.key())))
                .doOnNext(result -> log.info("✅ Sent offset: {}", result.recordMetadata().offset()))
                .doOnError(e -> log.error("❌ Error publishing event", e))
                .timeout(Duration.ofSeconds(5))
                .retry(2)
                .then();
    }
}
