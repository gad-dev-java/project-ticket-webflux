package com.ticketmaster.user.infrastructure.adapters.input.rest;

import com.ticketmaster.user.application.ports.input.UserUseCase;
import com.ticketmaster.user.application.ports.input.dto.UpsertUserCommand;
import com.ticketmaster.user.infrastructure.adapters.input.rest.mapper.JwtUserMapper;
import com.ticketmaster.user.infrastructure.adapters.input.rest.mapper.UserRestMapper;
import com.ticketmaster.user.infrastructure.adapters.input.rest.model.response.DataResponse;
import com.ticketmaster.user.infrastructure.adapters.input.rest.model.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserRestAdapter {
    private final UserUseCase userUseCase;
    private final JwtUserMapper jwtUserMapper;
    private final UserRestMapper userRestMapper;

    @PostMapping("/me")
    public Mono<ResponseEntity<DataResponse<UserResponse>>> getMyProfile(@AuthenticationPrincipal Jwt jwt,
                                                                         ServerWebExchange serverRequest) {
        UpsertUserCommand command = jwtUserMapper.toCommand(jwt);
        return userUseCase.registerUserOrUpdate(command)
                .doOnNext(userDto -> log.info("Usr Dto: {}", userDto))
                .map(userRestMapper::toResponse)
                .doOnNext(userResponse -> log.info("User Response: {}", userResponse))
                .map(userResponse -> {
                    var bodyResponse = DataResponse.<UserResponse>builder()
                            .status(HttpStatus.CREATED.value())
                            .message("User saved successfully")
                            .data(userResponse)
                            .timestamp(LocalDateTime.now())
                            .build();
                    URI location = UriComponentsBuilder
                            .fromUri(serverRequest.getRequest().getURI())
                            .path("/{id}")
                            .buildAndExpand(userResponse.userId())
                            .toUri();
                    return ResponseEntity.created(location).body(bodyResponse);
                });
    }

    @GetMapping("/user/{userId}")
    public Mono<ResponseEntity<DataResponse<UserResponse>>> getUserById(@PathVariable String userId) {
        return userUseCase.getUserProfile(UUID.fromString(userId))
                .map(userRestMapper::toResponse)
                .map(userResponse -> {
                    var bodyResponse = DataResponse.<UserResponse>builder()
                            .status(HttpStatus.OK.value())
                            .message("User found successfully")
                            .data(userResponse)
                            .timestamp(LocalDateTime.now())
                            .build();
                    return ResponseEntity.ok(bodyResponse);
                });
    }
}
