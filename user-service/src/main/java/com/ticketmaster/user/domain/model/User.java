package com.ticketmaster.user.domain.model;

import com.ticketmaster.user.domain.enums.UserStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    private UUID userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private UserStatus userStatus;
    private LocalDateTime createdAt;
}
