package com.ticketmaster.user.infrastructure.adapters.output.persistence;

import com.ticketmaster.user.application.ports.output.UserActivityPersistencePort;
import com.ticketmaster.user.domain.model.UserActivity;
import com.ticketmaster.user.infrastructure.adapters.output.persistence.mapper.UserActivityMapper;
import com.ticketmaster.user.infrastructure.adapters.output.persistence.repository.UserActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserActivityPersistenceAdapter implements UserActivityPersistencePort {
    private final UserActivityRepository userActivityRepository;
    private final UserActivityMapper userActivityMapper;

    @Override
    public Mono<UserActivity> saveActivity(UserActivity activity) {
        return userActivityRepository.save(userActivityMapper.toEntity(activity))
                .map(userActivityMapper::toDomain)
                .doOnNext(userActivityEntity -> log.info("Saving user activity {}", userActivityEntity))
                .doOnError(error -> log.error("Error saving activity", error));
    }

    @Override
    public Flux<UserActivity> findActivitiesByUserId(UUID userId) {
        return userActivityRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .map(userActivityMapper::toDomain)
                .doOnNext(userActivityEntity -> log.info("Finding activities by user activity {}", userActivityEntity))
                .doOnError(error -> log.error("Error finding activities by user activity", error));
    }
}
