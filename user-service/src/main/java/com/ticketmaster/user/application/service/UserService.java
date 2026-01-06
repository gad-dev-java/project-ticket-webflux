package com.ticketmaster.user.application.service;

import com.ticketmaster.user.application.ports.input.UserUseCase;
import com.ticketmaster.user.application.ports.input.dto.UpsertUserCommand;
import com.ticketmaster.user.application.ports.input.dto.UserDto;
import com.ticketmaster.user.application.ports.output.UserActivityPersistencePort;
import com.ticketmaster.user.application.ports.output.EventPublisherPort;
import com.ticketmaster.user.application.ports.output.UserPersistencePort;
import com.ticketmaster.user.domain.enums.ActivityType;
import com.ticketmaster.user.domain.enums.UserStatus;
import com.ticketmaster.user.domain.event.UserCreatedEvent;
import com.ticketmaster.user.domain.exception.UserNotFoundException;
import com.ticketmaster.user.domain.model.User;
import com.ticketmaster.user.domain.model.UserActivity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserUseCase {
    private final UserPersistencePort userPersistencePort;
    private final TransactionalOperator transactionalOperator;
    private final UserActivityPersistencePort userActivityPersistencePort;
    private final EventPublisherPort eventPublisherPort;

    @Override
    public Mono<UserDto> registerUserOrUpdate(UpsertUserCommand command) {
        return userPersistencePort.findUserById(command.userId())
                .flatMap(existingUser -> {
                    if (isDataChanged(command, existingUser)) {
                        User updatedUser = updateUserFields(existingUser, command);
                        return userPersistencePort.updateUser(updatedUser)
                                .flatMap(savedUser -> {
                                    var userActivity = UserActivity.builder()
                                            .userId(savedUser.getUserId())
                                            .activityType(ActivityType.PROFILE_UPDATED)
                                            .details("Updated profile data")
                                            .build();
                                    return userActivityPersistencePort.saveActivity(userActivity)
                                            .thenReturn(savedUser);
                                });
                    }
                    return Mono.just(existingUser);
                })
                .switchIfEmpty(
                        Mono.defer(() -> {
                            User newUser = createNewUser(command);
                            return userPersistencePort.saveUser(newUser)
                                    .flatMap(savedUser -> {
                                        log.info("User saved 1 : {}", savedUser);
                                        var userActivity = UserActivity.builder()
                                                .userId(savedUser.getUserId())
                                                .activityType(ActivityType.USER_REGISTERED)
                                                .details("Initial registration via Keycloak")
                                                .build();
                                        return userActivityPersistencePort.saveActivity(userActivity)
                                                .thenReturn(savedUser);
                                    });
                        })
                )
                .as(transactionalOperator::transactional)
                .flatMap(userSaved -> {
                    log.info("User saved 2 : {}", userSaved);
                    var userCreatedEvent = UserCreatedEvent.builder()
                            .userId(userSaved.getUserId())
                            .email(userSaved.getEmail())
                            .firstName(userSaved.getFirstName())
                            .lastName(userSaved.getLastName())
                            .occurredAt(userSaved.getCreatedAt())
                            .build();
                    return eventPublisherPort.publishUserCreated(userCreatedEvent)
                            .thenReturn(userSaved)
                            .doOnNext(user ->  log.info("User saved 3 : {}", user))
                            .onErrorResume(error -> {
                                log.error("Failed to publish event for userId: {}", userSaved.getUserId(), error);
                                return Mono.just(userSaved);
                            });
                })
                .map(this::mapToDto)
                .doOnNext(userDto -> log.info("User Dto: {}", userDto))
                .doOnNext(userDto -> log.info("User registered successfully"))
                .doOnError(error -> log.error("Error registering user from command: {}", command, error));
    }

    @Override
    public Mono<UserDto> getUserProfile(UUID userId) {
        return userPersistencePort.findUserById(userId)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User not found with id: " + userId)))
                .map(this::mapToDto)
                .doOnNext(userDb -> log.info("User found successfully"))
                .doOnError(error -> log.error("Error finding user with id: {}", userId, error));
    }

    private static boolean isDataChanged(UpsertUserCommand command, User existingUser) {
        return !Objects.equals(existingUser.getEmail(), command.email()) ||
                !Objects.equals(existingUser.getFirstName(), command.firstName()) ||
                !Objects.equals(existingUser.getLastName(), command.lastName()) ||
                !Objects.equals(existingUser.getPhone(), command.phone());
    }

    private UserDto mapToDto(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .createdAt(user.getCreatedAt())
                .build();
    }

    private User updateUserFields(User existingUser, UpsertUserCommand command) {
        return existingUser.toBuilder()
                .email(command.email())
                .firstName(command.firstName())
                .lastName(command.lastName())
                .phone(command.phone())
                .build();
    }

    private User createNewUser(UpsertUserCommand command) {
        return User.builder()
                .userId(command.userId())
                .username(command.username())
                .email(command.email())
                .firstName(command.firstName())
                .lastName(command.lastName())
                .phone(command.phone())
                .userStatus(UserStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
