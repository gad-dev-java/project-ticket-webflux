package com.ticketmaster.user.infrastructure.adapters.input.rest;

import com.ticketmaster.user.application.ports.input.UserActivityUseCase;
import com.ticketmaster.user.infrastructure.adapters.input.rest.mapper.UserActivityRestMapper;
import com.ticketmaster.user.infrastructure.adapters.input.rest.model.response.DataResponse;
import com.ticketmaster.user.infrastructure.adapters.input.rest.model.response.UserActivityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/activities")
@RequiredArgsConstructor
public class UserActivityRestAdapter {
    private final UserActivityUseCase userActivityUseCase;
    private final UserActivityRestMapper userActivityRestMapper;

    @GetMapping("/user/{userId}/history")
    public Mono<ResponseEntity<DataResponse<List<UserActivityResponse>>>> getHistory(@PathVariable String userId) {
        return userActivityUseCase.getUserActivity(UUID.fromString(userId))
                .map(userActivityRestMapper::toResponse)
                .collectList()
                .map(activities -> DataResponse.<List<UserActivityResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("User history retrieved")
                        .data(activities)
                        .timestamp(LocalDateTime.now())
                        .build())
                .map(ResponseEntity::ok);

    }
}
