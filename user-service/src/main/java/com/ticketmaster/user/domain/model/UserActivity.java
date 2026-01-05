package com.ticketmaster.user.domain.model;

import com.ticketmaster.user.domain.enums.ActivityType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class UserActivity {
    private UUID activityId;
    private UUID userId;
    private ActivityType activityType;
    private String details;
    private LocalDateTime createdAt;
}
