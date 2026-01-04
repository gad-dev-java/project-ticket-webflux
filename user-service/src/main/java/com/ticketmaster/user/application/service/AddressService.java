package com.ticketmaster.user.application.service;

import com.ticketmaster.user.application.ports.input.AddressUseCase;
import com.ticketmaster.user.application.ports.input.dto.AddressDto;
import com.ticketmaster.user.application.ports.input.dto.CreateAddressCommand;
import com.ticketmaster.user.application.ports.output.AddressPersistencePort;
import com.ticketmaster.user.application.ports.output.UserPersistencePort;
import com.ticketmaster.user.domain.exception.UserNotFoundException;
import com.ticketmaster.user.domain.model.Address;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressService implements AddressUseCase {
    private final AddressPersistencePort addressPersistencePort;
    private final UserPersistencePort userPersistencePort;

    @Override
    public Mono<AddressDto> addAddress(UUID userId, CreateAddressCommand command) {
        return userPersistencePort.findUserById(userId)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User not found with id: " + userId)))
                .flatMap(user -> {
                    Address newAddress = Address.builder()
                            .addressId(UUID.randomUUID())
                            .userId(userId)
                            .street(command.street())
                            .city(command.city())
                            .country(command.country())
                            .zipCode(command.zipCode()).
                            createdAt(LocalDateTime.now())
                            .build();
                    return addressPersistencePort.saveAddress(newAddress);
                })
                .map(this::toDto)
                .doOnNext(addressDto -> log.info("AddressDto: {}", addressDto))
                .doOnError(error -> log.error("Error while saving address", error));
    }

    @Override
    public Flux<AddressDto> getUserAddress(UUID userId) {
        return addressPersistencePort.findAddressesByUserId(userId)
                .map(this::toDto)
                .doOnNext(addressDto -> log.info("AddressDto: {}", addressDto))
                .doOnError(error -> log.error("Error while getting user address", error));
    }

    private AddressDto toDto(Address address) {
        return AddressDto.builder()
                .addressId(address.getAddressId())
                .userId(address.getUserId())
                .street(address.getStreet())
                .city(address.getCity())
                .country(address.getCountry())
                .zipCode(address.getZipCode())
                .build();
    }
}
