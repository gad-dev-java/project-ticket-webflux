package com.ticketmaster.user.infrastructure.adapters.output.persistence.entities;

import com.ticketmaster.user.domain.enums.ActivityType;
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
@Table("user_activities")
@NoArgsConstructor
@AllArgsConstructor
public class UserActivityEntity {
    @Id
    @Column(value = "activity_id")
    private UUID activityId;

    @Column(value = "user_id")
    private UUID userId;

    @Column(value = "activity_type")
    private ActivityType activityType;

    private String details;

    @CreatedDate
    @Column(value = "created_at")
    private LocalDateTime createdAt;
}
