package com.ticketmaster.user.application.ports.input.dto;

import lombok.Builder;

@Builder
public record CreateAddressCommand(
         String street,
         String city,
         String country,
         String zipCode
) {
}
