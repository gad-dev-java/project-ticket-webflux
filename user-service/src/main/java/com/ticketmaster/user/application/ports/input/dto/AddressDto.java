package com.ticketmaster.user.application.ports.input.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record AddressDto(
        UUID addressId,
        UUID userId,
        String street,
        String city,
        String country,
        String zipCode
) {
}
