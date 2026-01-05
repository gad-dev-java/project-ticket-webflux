package com.ticketmaster.user.infrastructure.adapters.input.rest;

import com.ticketmaster.user.application.ports.input.GenreUseCase;
import com.ticketmaster.user.infrastructure.adapters.input.rest.mapper.GenreRestMapper;
import com.ticketmaster.user.infrastructure.adapters.input.rest.model.response.DataResponse;
import com.ticketmaster.user.infrastructure.adapters.input.rest.model.response.GenreResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreRestAdapter {
    private final GenreUseCase genreUseCase;
    private final GenreRestMapper genreRestMapper;

    @GetMapping
    public Mono<ResponseEntity<DataResponse<List<GenreResponse>>>> getAllGenres() {
        return genreUseCase.getAllGenres()
                .map(genreRestMapper::toResponse)
                .collectList()
                .map(genres -> DataResponse.<List<GenreResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Genres catalog retrieved")
                        .data(genres)
                        .timestamp(LocalDateTime.now())
                        .build())
                .map(ResponseEntity::ok);
    }

    @GetMapping("/users/{userId}/genres")
    public Mono<ResponseEntity<DataResponse<List<GenreResponse>>>> geUserFavorites(@PathVariable String userId) {
        return genreUseCase.getUserFavoriteGenres(UUID.fromString(userId))
                .map(genreRestMapper::toResponse)
                .collectList()
                .map(genres -> DataResponse.<List<GenreResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("User favorite genres retrieved")
                        .data(genres)
                        .timestamp(LocalDateTime.now())
                        .build())
                .map(ResponseEntity::ok);

    }

    @PostMapping("/users/{userId}/genres/{genreId}")
    public Mono<ResponseEntity<DataResponse<Void>>> addFavorite(@PathVariable String userId,
                                                                @PathVariable String genreId) {
        return genreUseCase.addGenreToFavorites(UUID.fromString(userId), UUID.fromString(genreId))
                .then(Mono.just(DataResponse.<Void>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Genre added to favorites")
                        .timestamp(LocalDateTime.now())
                        .build()
                ))
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/users/{userId}/genres/{genreId}")
    public Mono<ResponseEntity<DataResponse<Void>>> removeFavorite(@PathVariable String userId,
                                                                   @PathVariable String genreId) {
        return genreUseCase.removeGenreFromFavorites(UUID.fromString(userId), UUID.fromString(genreId))
                .then(Mono.just(DataResponse.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .message("Genre removed from favorites")
                        .timestamp(LocalDateTime.now())
                        .build()
                ))
                .map(wa -> ResponseEntity.noContent().build());
    }
}
