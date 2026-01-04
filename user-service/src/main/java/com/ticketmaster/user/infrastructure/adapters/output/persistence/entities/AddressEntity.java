package com.ticketmaster.user.infrastructure.adapters.output.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Table("addresses")
@NoArgsConstructor
@AllArgsConstructor
public class AddressEntity {
    @Id
    @Column(value = "address_id")
    private UUID addressId;

    @Column(value = "user_id")
    private UUID userId;

    private String street;
    private String city;
    private String country;

    @Column(value = "zip_code")
    private String zipCode;

    @Column(value = "is_default")
    private Boolean isDefault;

    @Column(value = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;
}
