package com.ticketmaster.user.application.ports.output;

import com.ticketmaster.user.domain.model.Genre;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface GenrePersistencePort {
    Flux<Genre> findAllGenres();
    Flux<Genre> findFavoritesByUserId(UUID userId);
    Mono<Void> addFavoriteGenre(UUID userId, UUID genreId);
    Mono<Void> removeFavoriteGenre(UUID userId, UUID genreId);
}
