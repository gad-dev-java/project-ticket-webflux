package com.ticketmaster.user.infrastructure.adapters.input.rest.model.request;

import lombok.Builder;

@Builder
public record CreateAddressRequest(
        String street,
        String city,
        String country,
        String zipCode
) {
}
