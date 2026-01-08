package com.ticketmaster.event.infrastructure.adapters.output.persistence;

import com.ticketmaster.event.application.ports.output.TicketTypePersistencePort;
import com.ticketmaster.event.domain.model.TicketType;
import com.ticketmaster.event.infrastructure.adapters.output.persistence.mapper.TicketTypeMapper;
import com.ticketmaster.event.infrastructure.adapters.output.persistence.repository.TicketTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class TicketTypePersistenceAdapter implements TicketTypePersistencePort {
    private final TicketTypeRepository ticketTypeRepository;
    private final TicketTypeMapper ticketTypeMapper;

    @Override
    public Flux<TicketType> saveTicketTypes(List<TicketType> ticketTypes) {
        return ticketTypeRepository.saveAll(ticketTypeMapper.toEntityList(ticketTypes))
                .doOnNext(ticketType -> log.info("Ticket type saved: {}", ticketType))
                .doOnError(error -> log.error("Error while saving ticket types", error))
                .map(ticketTypeMapper::toDomain);
    }

    @Override
    public Flux<TicketType> findByEventId(UUID eventId) {
        return ticketTypeRepository.findByEventId(eventId)
                .map(ticketTypeMapper::toDomain);
    }
}
