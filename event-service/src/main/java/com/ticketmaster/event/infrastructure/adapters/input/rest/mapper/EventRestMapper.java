package com.ticketmaster.event.infrastructure.adapters.input.rest.mapper;

import com.ticketmaster.event.application.ports.dto.CreateEventCommand;
import com.ticketmaster.event.application.ports.dto.EventDetailDto;
import com.ticketmaster.event.application.ports.dto.EventDto;
import com.ticketmaster.event.infrastructure.adapters.input.rest.model.request.CreateEventRequest;
import com.ticketmaster.event.infrastructure.adapters.input.rest.model.response.EventDetailResponse;
import com.ticketmaster.event.infrastructure.adapters.input.rest.model.response.EventResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN,
        uses = {TicketTypeRestMapper.class})
public interface EventRestMapper {
    CreateEventCommand toCommand(CreateEventRequest request);

    EventResponse toResponse(EventDto eventDto);

    EventDetailResponse toDetailResponse(EventDetailDto eventDetailDto);
}
