package com.ticketmaster.user.application.service;

import com.ticketmaster.user.application.ports.input.GenreUseCase;
import com.ticketmaster.user.application.ports.output.GenrePersistencePort;
import com.ticketmaster.user.application.ports.output.UserPersistencePort;
import com.ticketmaster.user.domain.exception.UserNotFoundException;
import com.ticketmaster.user.domain.model.Genre;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenreService implements GenreUseCase {
    private final GenrePersistencePort genrePersistencePort;
    private final UserPersistencePort userPersistencePort;

    @Override
    public Flux<Genre> getAllGenres() {
        return genrePersistencePort.findAllGenres()
                .doOnNext(genre -> log.info("Genre found: {}", genre))
                .doOnError(error -> log.error("Error finding Genre", error));
    }

    @Override
    public Flux<Genre> getUserFavoriteGenres(UUID userId) {
        return genrePersistencePort.findFavoritesByUserId(userId)
                .doOnNext(genre -> log.info("Genre found {} for id user: {}", genre, userId))
                .doOnError(error -> log.error("Error finding Genre", error));
    }

    @Override
    public Mono<Void> addGenreToFavorites(UUID userId, UUID genreId) {
        return userPersistencePort.findUserById(userId)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User not found with id: " + userId)))
                .flatMap(user -> {
                    return genrePersistencePort.addFavoriteGenre(userId, genreId);
                })
                .doOnSuccess(genre -> log.info("Genre added {} for id user: {}", genre, userId));
    }

    @Override
    public Mono<Void> removeGenreFromFavorites(UUID userId, UUID genreId) {
        return genrePersistencePort.removeFavoriteGenre(userId, genreId)
                .doOnSuccess(genre -> log.info("Genre removed {} for id user: {}", genre, userId));
    }
}
