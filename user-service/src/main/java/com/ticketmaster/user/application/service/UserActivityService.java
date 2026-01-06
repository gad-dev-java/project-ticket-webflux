package com.ticketmaster.user.application.service;

import com.ticketmaster.user.application.ports.input.UserActivityUseCase;
import com.ticketmaster.user.application.ports.output.UserActivityPersistencePort;
import com.ticketmaster.user.domain.model.UserActivity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserActivityService implements UserActivityUseCase {
    private final UserActivityPersistencePort userActivityPersistencePort;

    @Override
    public Flux<UserActivity> getUserActivity(UUID userId) {
        return userActivityPersistencePort.findActivitiesByUserId(userId)
                .doOnNext(userActivity -> log.info("activities: {}", userActivity.getActivityType()));
    }
}
