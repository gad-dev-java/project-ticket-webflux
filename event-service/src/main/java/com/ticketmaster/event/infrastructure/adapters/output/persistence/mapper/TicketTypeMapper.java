package com.ticketmaster.event.infrastructure.adapters.output.persistence.mapper;

import com.ticketmaster.event.domain.model.TicketType;
import com.ticketmaster.event.infrastructure.adapters.output.persistence.entities.TicketTypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN)
public interface TicketTypeMapper {
    TicketTypeEntity toEntity(TicketType ticketType);

    TicketType toDomain(TicketTypeEntity ticketType);

    default List<TicketTypeEntity> toEntityList(List<TicketType> ticketTypes) {
        return ticketTypes.stream()
                .map(this::toEntity)
                .toList();
    }
}
