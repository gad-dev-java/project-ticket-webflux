package com.ticketmaster.user.infrastructure.adapters.output.persistence;

import com.ticketmaster.user.application.ports.output.GenrePersistencePort;
import com.ticketmaster.user.domain.exception.GenreNotFoundException;
import com.ticketmaster.user.domain.model.Genre;
import com.ticketmaster.user.infrastructure.adapters.output.persistence.mapper.GenreMapper;
import com.ticketmaster.user.infrastructure.adapters.output.persistence.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class GenrePersistenceAdapter implements GenrePersistencePort {
    private final GenreRepository genreRepository;
    private final GenreMapper  genreMapper;
    private final DatabaseClient databaseClient;

    @Override
    public Flux<Genre> findAllGenres() {
        return genreRepository.findAll()
                .map(genreMapper::toDomain);
    }

    @Override
    public Flux<Genre> findFavoritesByUserId(UUID userId) {
        return genreRepository.findFavoritesByUserId(userId)
                .map(genreMapper::toDomain)
                .doOnNext(genre -> log.info("Genres: {}", genre));
    }

    @Override
    public Mono<Void> addFavoriteGenre(UUID userId, UUID genreId) {
        final String sql = """
                INSERT INTO user_favorite_genres (user_id, genre_id)
                VALUES (:userId, :genreId)
                """;
        return databaseClient.sql(sql)
                .bind("userId", userId)
                .bind("genreId", genreId)
                .fetch()
                .rowsUpdated()
                .flatMap(rows -> {
                    if (rows == 0){
                        return Mono.error(new GenreNotFoundException("Genre not found"));
                    }
                    return Mono.empty();
                });
    }

    @Override
    public Mono<Void> removeFavoriteGenre(UUID userId, UUID genreId) {
        final String sql = """
                DELETE FROM user_favorite_genres
                WHERE user_id = :userId AND genre_id = :genreId
                """;

        return databaseClient.sql(sql)
                .bind("userId", userId)
                .bind("genreId", genreId)
                .fetch().rowsUpdated()
                .doOnNext(rows -> log.info("Deleted {} favorite genre(s)", rows))
                .then();
    }
}
