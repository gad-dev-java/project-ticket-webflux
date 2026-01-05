package com.ticketmaster.user.domain.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class PaymentMethod {
    private UUID paymentMethodId;
    private UUID userId;
    private String token;
    private String brand;
    private String lastFour;
    private String holderName;
    private Boolean isDefault;
    private LocalDateTime createdAt;
}
