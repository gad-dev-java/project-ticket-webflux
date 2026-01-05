package com.ticketmaster.user.infrastructure.adapters.input.rest.model.request;

public record CreatePaymentMethodRequest(
        String token,
        String brand,
        String lastFour,
        String holderName,
        Boolean isDefault
) {
}
