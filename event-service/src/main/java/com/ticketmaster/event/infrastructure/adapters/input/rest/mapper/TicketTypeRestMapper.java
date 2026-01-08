package com.ticketmaster.event.infrastructure.adapters.input.rest.mapper;

import com.ticketmaster.event.application.ports.dto.CreateTicketTypeCommand;
import com.ticketmaster.event.domain.model.TicketType;
import com.ticketmaster.event.infrastructure.adapters.input.rest.model.request.CreateTicketTypeRequest;
import com.ticketmaster.event.infrastructure.adapters.input.rest.model.response.TicketTypeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN)
public interface TicketTypeRestMapper {
    CreateTicketTypeCommand toCommand(CreateTicketTypeRequest request);
    TicketTypeResponse toResponse(TicketType ticketType);
}
