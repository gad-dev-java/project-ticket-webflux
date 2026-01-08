package com.ticketmaster.event.infrastructure.adapters.output.persistence.mapper;

import com.ticketmaster.event.domain.model.Event;
import com.ticketmaster.event.infrastructure.adapters.output.persistence.entities.EventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN)
public interface EventMapper {
    EventEntity toEntity(Event event);

    Event toDomain(EventEntity eventEntity);
}
