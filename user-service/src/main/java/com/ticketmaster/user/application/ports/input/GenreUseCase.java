package com.ticketmaster.user.application.ports.input;

import com.ticketmaster.user.domain.model.Genre;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface GenreUseCase {
    Flux<Genre> getAllGenres();
    Flux<Genre> getUserFavoriteGenres(UUID userId);
    Mono<Void> addGenreToFavorites(UUID userId, UUID genreId);
    Mono<Void> removeGenreFromFavorites(UUID userId, UUID genreId);
}
