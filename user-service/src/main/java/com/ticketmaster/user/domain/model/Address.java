package com.ticketmaster.user.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class Address {
    private UUID addressId;
    private UUID userId;
    private String street;
    private String city;
    private String country;
    private String zipCode;
    private LocalDateTime createdAt;
}
