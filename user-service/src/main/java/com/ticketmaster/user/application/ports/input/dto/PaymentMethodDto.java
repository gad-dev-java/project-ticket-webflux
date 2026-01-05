package com.ticketmaster.user.application.ports.input.dto;

import lombok.Builder;
import java.util.UUID;

@Builder
public record PaymentMethodDto(
         UUID paymentMethodId,
         String token,
         String brand,
         String holderName
) {
}
