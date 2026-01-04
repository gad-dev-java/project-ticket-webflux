package com.ticketmaster.user.application.ports.output;

import com.ticketmaster.user.domain.model.Address;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface AddressPersistencePort {
    Mono<Address> saveAddress(Address address);
    Flux<Address> findAddressesByUserId(UUID userId);
    Mono<Void> deleteAddress(UUID addressId);
}
