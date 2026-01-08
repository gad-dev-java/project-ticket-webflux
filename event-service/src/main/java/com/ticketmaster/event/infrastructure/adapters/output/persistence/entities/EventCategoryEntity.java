package com.ticketmaster.event.infrastructure.adapters.output.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Getter
@Setter
@Table("event_categories")
@NoArgsConstructor
@AllArgsConstructor
public class EventCategoryEntity {
    @Id
    @Column(value = "category_id")
    private UUID categoryId;

    @Column(value = "category_name")
    private String categoryName;

    private String description;
}
