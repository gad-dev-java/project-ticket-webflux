package com.ticketmaster.user.application.service;

import com.ticketmaster.user.application.ports.input.UserUseCase;
import com.ticketmaster.user.application.ports.input.dto.UpsertUserCommand;
import com.ticketmaster.user.application.ports.input.dto.UserDto;
import com.ticketmaster.user.application.ports.output.UserPersistencePort;
import com.ticketmaster.user.domain.enums.UserStatus;
import com.ticketmaster.user.domain.exception.UserNotFoundException;
import com.ticketmaster.user.domain.model.User;
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

    @Override
    public Mono<UserDto> registerUserOrUpdate(UpsertUserCommand command) {
        return userPersistencePort.findUserById(command.userId())
                .flatMap(existingUser -> {
                    boolean dataChanged = isDataChanged(command, existingUser);

                    if (dataChanged) {
                        User updatedUser = updateUserFields(existingUser, command);
                        return userPersistencePort.updateUser(updatedUser);
                    }
                    return Mono.just(existingUser);
                })
                .switchIfEmpty(
                        Mono.defer(() -> {
                            User newUser = createNewUser(command);
                            return userPersistencePort.saveUser(newUser);
                        })
                )
                .map(this::mapToDto)
                .as(transactionalOperator::transactional)
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
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
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
