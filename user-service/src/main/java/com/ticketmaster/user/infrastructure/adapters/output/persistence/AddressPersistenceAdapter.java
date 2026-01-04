package com.ticketmaster.user.infrastructure.adapters.output.persistence;

import com.ticketmaster.user.application.ports.output.AddressPersistencePort;
import com.ticketmaster.user.domain.model.Address;
import com.ticketmaster.user.infrastructure.adapters.output.persistence.mapper.AddressMapper;
import com.ticketmaster.user.infrastructure.adapters.output.persistence.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class AddressPersistenceAdapter implements AddressPersistencePort {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final R2dbcEntityTemplate template;

    @Override
    public Mono<Address> saveAddress(Address address) {
        return Mono.just(address)
                .map(addressMapper::toEntity)
                .flatMap(template::insert)
                .map(addressMapper::toDomain)
                .doOnNext(addressSaved -> log.info("Address created successfully"))
                .doOnError(error -> log.error("Error creating address"));
    }

    @Override
    public Flux<Address> findAddressesByUserId(UUID userId) {
        return addressRepository.findByUserId(userId)
                .map(addressMapper::toDomain)
                .doOnNext(addressSaved -> log.info("Address found successfully"))
                .doOnError(error -> log.error("Error finding address"));
    }

    @Override
    public Mono<Void> deleteAddress(UUID addressId) {
        return addressRepository.deleteById(addressId);
    }
}
