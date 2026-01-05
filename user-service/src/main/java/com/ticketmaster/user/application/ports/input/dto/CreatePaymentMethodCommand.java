package com.ticketmaster.user.application.ports.input.dto;

public record CreatePaymentMethodCommand(
        String token,
        String brand,
        String lastFour,
        String holderName,
        Boolean isDefault
) {
}
