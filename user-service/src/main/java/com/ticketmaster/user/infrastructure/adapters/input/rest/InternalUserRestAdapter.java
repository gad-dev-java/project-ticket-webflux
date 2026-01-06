package com.ticketmaster.user.infrastructure.adapters.input.rest;

import com.ticketmaster.user.application.ports.input.UserUseCase;
import com.ticketmaster.user.infrastructure.adapters.input.rest.mapper.UserRestMapper;
import com.ticketmaster.user.infrastructure.adapters.input.rest.model.response.UserSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/internal/users")
@RequiredArgsConstructor
public class InternalUserRestAdapter {
    private final UserUseCase userUseCase;
    private final UserRestMapper userRestMapper;

    @GetMapping("/user/{userId}/summary")
    public Mono<UserSummaryResponse> getUserSummary(@PathVariable String userId) {
        return userUseCase.getUserProfile(UUID.fromString(userId))
                .map(userRestMapper::toSummaryResponse);
    }
}
