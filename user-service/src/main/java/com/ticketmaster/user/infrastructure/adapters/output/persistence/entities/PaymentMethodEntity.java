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
@Table("payment_methods")
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodEntity {
    @Id
    @Column(value = "payment_method_id")
    private UUID paymentMethodId;

    @Column(value = "user_id")
    private UUID userId;

    private String token;
    private String brand;

    @Column(value = "last_four")
    private String lastFour;

    @Column(value = "holder_name")
    private String holderName;

    @Column(value = "is_default")
    private Boolean isDefault;

    @CreatedDate
    @Column(value = "created_at")
    private LocalDateTime createdAt;
}
