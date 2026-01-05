package com.ticketmaster.user.infrastructure.adapters.output.persistence.repository;

import com.ticketmaster.user.infrastructure.adapters.output.persistence.entities.GenreEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface GenreRepository extends R2dbcRepository<GenreEntity, UUID> {

    @Query("""
            SELECT g.* FROM genres g
            JOIN user_favorite_genres ufg ON g.genre_id = ufg.genre_id
            WHERE ufg.user_id = :userId
            """)
    Flux<GenreEntity> findFavoritesByUserId(UUID userId);
}
