package com.ticketmaster.user.application.service;

import com.ticketmaster.user.application.ports.input.UserActivityUseCase;
import com.ticketmaster.user.application.ports.input.dto.RegisterLogActivityCommand;
import com.ticketmaster.user.application.ports.output.UserActivityPersistencePort;
import com.ticketmaster.user.domain.model.UserActivity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserActivityService implements UserActivityUseCase {
    private final UserActivityPersistencePort userActivityPersistencePort;

    @Override
    public Mono<Void> logActivity(RegisterLogActivityCommand command) {
        UserActivity userActivity = UserActivity.builder()
                .userId(command.userId())
                .activityType(command.activityType())
                .details(command.details())
                .createdAt(LocalDateTime.now())
                .build();
        return userActivityPersistencePort.saveActivity(userActivity)
                .doOnSuccess(userSaved -> log.info("Activity loggedL User {} -> {}", userSaved.getUserId(), userSaved.getActivityType()))
                .then();
    }

    @Override
    public Flux<UserActivity> getUserActivity(UUID userId) {
        return userActivityPersistencePort.findActivitiesByUserId(userId)
                .doOnNext(userActivity -> log.info("activities: {}", userActivity.getActivityType()));
    }
}
