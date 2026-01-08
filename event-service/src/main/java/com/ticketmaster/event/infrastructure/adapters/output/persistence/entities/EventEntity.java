package com.ticketmaster.event.infrastructure.adapters.output.persistence.entities;

import com.ticketmaster.event.domain.enums.EventStatus;
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
@Table("events")
@NoArgsConstructor
@AllArgsConstructor
public class EventEntity {
    @Id
    private UUID eventId;
    private String name;
    private String description;

    @Column(value = "category_id")
    private UUID categoryId;

    @Column(value = "event_date")
    private LocalDateTime eventDate;

    private String venue;
    private String address;
    private String city;
    private String country;
    private EventStatus status;
    private String imageUrl;

    @CreatedDate
    @Column(value = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(value = "updated_at")
    private LocalDateTime updatedAt;
}
