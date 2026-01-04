package com.ticketmaster.user.application.ports.input;

import com.ticketmaster.user.application.ports.input.dto.AddressDto;
import com.ticketmaster.user.application.ports.input.dto.CreateAddressCommand;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface AddressUseCase {
    Mono<AddressDto> addAddress(UUID userId, CreateAddressCommand command);
    Flux<AddressDto> getUserAddress(UUID userId);
}
