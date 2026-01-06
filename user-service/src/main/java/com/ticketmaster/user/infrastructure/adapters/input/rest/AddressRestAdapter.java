package com.ticketmaster.user.infrastructure.adapters.input.rest;

import com.ticketmaster.user.application.ports.input.AddressUseCase;
import com.ticketmaster.user.application.ports.input.dto.CreateAddressCommand;
import com.ticketmaster.user.infrastructure.adapters.input.rest.mapper.AddressRestMapper;
import com.ticketmaster.user.infrastructure.adapters.input.rest.model.request.CreateAddressRequest;
import com.ticketmaster.user.infrastructure.adapters.input.rest.model.response.AddressResponse;
import com.ticketmaster.user.infrastructure.adapters.input.rest.model.response.DataResponse;
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
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddressRestAdapter {
    private final AddressUseCase addressUseCase;
    private final AddressRestMapper addressRestMapper;

    @PostMapping("/{userId}")
    public Mono<ResponseEntity<DataResponse<AddressResponse>>> createAddress(@PathVariable String userId,
                                                                @RequestBody CreateAddressRequest request,
                                                                ServerWebExchange serverRequest) {
        CreateAddressCommand command = addressRestMapper.toCommand(request);
        return addressUseCase.addAddress(UUID.fromString(userId), command)
                .map(addressDto -> {
                    AddressResponse addressResponse = addressRestMapper.toResponse(addressDto);

                    return DataResponse.<AddressResponse>builder()
                            .status(HttpStatus.CREATED.value())
                            .message("Address created successfully")
                            .data(addressResponse)
                            .timestamp(LocalDateTime.now())
                            .build();
                })
                .map(body -> {
                    URI location = UriComponentsBuilder
                            .fromUri(serverRequest.getRequest().getURI())
                            .path("/{id}")
                            .buildAndExpand(body.data().addressId())
                            .toUri();
                    return ResponseEntity.created(location).body(body);
                });
    }

    @GetMapping("/{userId}/address")
    public Mono<ResponseEntity<DataResponse<List<AddressResponse>>>> getUserAddress(@PathVariable String userId){
        return addressUseCase.getUserAddress(UUID.fromString(userId))
                .map(addressRestMapper::toResponse)
                .collectList()
                .map(addressList -> DataResponse.<List<AddressResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Address retrieved successfully")
                        .data(addressList)
                        .timestamp(LocalDateTime.now())
                        .build())
                .map(ResponseEntity::ok);
    }
}
