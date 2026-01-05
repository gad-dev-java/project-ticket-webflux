package com.ticketmaster.user.infrastructure.adapters.output.persistence.entities;

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
@Table("genres")
@NoArgsConstructor
@AllArgsConstructor
public class GenreEntity {
    @Id
    @Column(value = "genre_id")
    private UUID genreId;
    private String name;
}
