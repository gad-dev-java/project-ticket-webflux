package com.ticketmaster.event.infrastructure.adapters.input.rest;

import com.ticketmaster.event.application.ports.input.GetEventUseCase;
import com.ticketmaster.event.application.ports.input.ManageEventUseCase;
import com.ticketmaster.event.infrastructure.adapters.input.rest.mapper.EventRestMapper;
import com.ticketmaster.event.infrastructure.adapters.input.rest.model.request.CreateEventRequest;
import com.ticketmaster.event.infrastructure.adapters.input.rest.model.response.DataResponse;
import com.ticketmaster.event.infrastructure.adapters.input.rest.model.response.EventDetailResponse;
import com.ticketmaster.event.infrastructure.adapters.input.rest.model.response.EventResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventRestAdapter {
    private final ManageEventUseCase eventUseCase;
    private final GetEventUseCase getEventUseCase;
    private final EventRestMapper eventRestMapper;

    @PostMapping
    public Mono<ResponseEntity<DataResponse<EventResponse>>> createEvent(@RequestBody CreateEventRequest request,
                                                                         ServerWebExchange serverRequest) {
        return eventUseCase.createEvent(eventRestMapper.toCommand(request))
                .map(eventRestMapper::toResponse)
                .map(event -> {
                    var bodyResponse = DataResponse.<EventResponse>builder()
                            .status(HttpStatus.CREATED.value())
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

    @GetMapping("/{id}")
    public Mono<ResponseEntity<DataResponse<EventDetailResponse>>> getEvent(@PathVariable String id) {
        return getEventUseCase.getEventDetail(UUID.fromString(id))
                .map(eventRestMapper::toDetailResponse)
                .map(event -> {
                    var bodyResponse = DataResponse.<EventDetailResponse>builder()
                            .status(HttpStatus.OK.value())
                            .message("Event fetch successfully")
                            .data(event)
                            .timestamp(LocalDateTime.now())
                            .build();

                    return ResponseEntity.ok(bodyResponse);
                });
    }

    @GetMapping
    public Mono<ResponseEntity<DataResponse<List<EventDetailResponse>>>> listEvents() {
        return getEventUseCase.getAllEvents()
                .map(eventRestMapper::toDetailResponse)
                .collectList()
                .map(events -> {
                    var bodyResponse = DataResponse.<List<EventDetailResponse>>builder()
                            .status(HttpStatus.OK.value())
                            .message("Events fetch successfully")
                            .data(events)
                            .timestamp(LocalDateTime.now())
                            .build();

                    return ResponseEntity.ok(bodyResponse);
                });
    }
}
