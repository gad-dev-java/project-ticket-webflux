package com.ticketmaster.user.infrastructure.adapters.input.rest;

import com.ticketmaster.user.application.ports.input.UserUseCase;
import com.ticketmaster.user.application.ports.input.dto.UpsertUserCommand;
import com.ticketmaster.user.application.ports.input.dto.UserDto;
import com.ticketmaster.user.infrastructure.adapters.input.rest.mapper.JwtUserMapper;
import com.ticketmaster.user.infrastructure.adapters.input.rest.model.response.DataResponse;
import com.ticketmaster.user.infrastructure.utils.jwt.JwtExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserRestAdapter {
    private final UserUseCase userUseCase;
    private final JwtUserMapper jwtUserMapper;
    private final JwtExtractor jwtExtractor;

    @PostMapping("/me")
    public Mono<ResponseEntity<DataResponse<UserDto>>> getMyProfile(@AuthenticationPrincipal Jwt jwt,
                                                                    ServerWebExchange serverRequest) {
        UpsertUserCommand command = jwtUserMapper.toCommand(jwt);
        return userUseCase.registerUserOrUpdate(command)
                .map(userDto -> DataResponse.<UserDto>builder()
                        .status(HttpStatus.OK.value())
                        .message("User saved successfully")
                        .data(userDto)
                        .timestamp(LocalDateTime.now())
                        .build())
                .map(body -> {
                    URI location = UriComponentsBuilder
                            .fromUri(serverRequest.getRequest().getURI())
                            .path("/{id}")
                            .buildAndExpand(body.data().userId())
                            .toUri();
                    return ResponseEntity.created(location).body(body);
                });
    }

    @GetMapping
    public Mono<ResponseEntity<DataResponse<UserDto>>> getUserById(@AuthenticationPrincipal Jwt jwt) {
        return userUseCase.getUserProfile(jwtExtractor.extractUserId(jwt))
                .map(userDto -> DataResponse.<UserDto>builder()
                        .status(HttpStatus.OK.value())
                        .message("User found successfully")
                        .data(userDto)
                        .timestamp(LocalDateTime.now())
                        .build())
                .map(ResponseEntity::ok);
    }
}
