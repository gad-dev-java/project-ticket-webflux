package com.ticketmaster.event.infrastructure.adapters.input.rest;

import com.ticketmaster.event.application.ports.input.ManageEventUseCase;
import com.ticketmaster.event.infrastructure.adapters.input.rest.mapper.EventRestMapper;
import com.ticketmaster.event.infrastructure.adapters.input.rest.model.request.CreateEventRequest;
import com.ticketmaster.event.infrastructure.adapters.input.rest.model.response.DataResponse;
import com.ticketmaster.event.infrastructure.adapters.input.rest.model.response.EventResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventRestAdapter {
    private final ManageEventUseCase eventUseCase;
    private final EventRestMapper eventRestMapper;

    @PostMapping
    public Mono<ResponseEntity<DataResponse<EventResponse>>> createEvent(@RequestBody CreateEventRequest request,
                                                                         ServerWebExchange serverRequest) {
        return eventUseCase.createEvent(eventRestMapper.toCommand(request))
                .map(eventRestMapper::toResponse)
                .map(event -> {
                    var bodyResponse = DataResponse.<EventResponse>builder()
                            .status(HttpStatus.OK.value())
                            .message("Event created successfully")
                            .data(event)
                            .timestamp(LocalDateTime.now())
                            .build();
                    URI location = UriComponentsBuilder
                            .fromUri(serverRequest.getRequest().getURI())
                            .path("/{id}")
                            .buildAndExpand(event.eventId())
                            .toUri();
                    return ResponseEntity.created(location).body(bodyResponse);
                });
    }
}
