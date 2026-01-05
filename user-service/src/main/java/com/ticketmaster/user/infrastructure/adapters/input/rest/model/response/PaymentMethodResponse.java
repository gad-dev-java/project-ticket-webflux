package com.ticketmaster.user.infrastructure.adapters.input.rest.model.response;

import java.util.UUID;

public record PaymentMethodResponse(
        UUID paymentMethodId,
        String token,
        String brand,
        String holderName
) {
}
