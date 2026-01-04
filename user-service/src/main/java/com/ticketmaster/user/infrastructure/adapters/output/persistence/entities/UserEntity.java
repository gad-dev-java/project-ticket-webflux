package com.ticketmaster.user.infrastructure.adapters.output.persistence.entities;

import com.ticketmaster.user.domain.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Table("users")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity{
    @Id
    private UUID userId;

    private String username;
    private String email;

    @Column(value = "first_name")
    private String firstName;

    @Column(value = "last_name")
    private String lastName;

    private String phone;
    private UserStatus status;

    @Column(value = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(value = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
