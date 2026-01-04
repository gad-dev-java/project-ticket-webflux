package com.ticketmaster.user.infrastructure.adapters.input.rest.model.response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record AddressResponse(
        UUID addressId,
        UUID userId,
        String street,
        String city,
        String country,
        String zipCode
) {
}
