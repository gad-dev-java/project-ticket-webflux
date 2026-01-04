package com.ticketmaster.user.infrastructure.adapters.output.persistence;

import com.ticketmaster.user.application.ports.output.UserPersistencePort;
import com.ticketmaster.user.domain.exception.UserNotFoundException;
import com.ticketmaster.user.domain.model.User;
import com.ticketmaster.user.infrastructure.adapters.output.persistence.mapper.UserMapper;
import com.ticketmaster.user.infrastructure.adapters.output.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserPersistenceAdapter implements UserPersistencePort {
    private final UserRepository userRepository;
    private final R2dbcEntityTemplate template;
    private final UserMapper userMapper;

    @Override
    public Mono<User> saveUser(User user) {
        return template.insert(userMapper.toEntity(user))
                .map(userMapper::toDomain)
                .doOnNext(userSaved -> log.info("User saved successfully with id: {}", userSaved.getUserId()))
                .doOnError(error -> log.error("Error while saving user", error));
    }

    @Override
    public Mono<User> updateUser(User user) {
        return userRepository.save(userMapper.toEntity(user))
                .map(userMapper::toDomain)
                .doOnSuccess(u -> log.info("User updated: {}", u.getUserId()));
    }

    @Override
    public Mono<User> findUserById(UUID userId) {
        return userRepository.findById(userId)
                .map(userMapper::toDomain)
                .doOnNext(user ->  log.info("User found successfully with id: {}", userId))
                .doOnError(error -> log.error("Error while finding user with id {}", userId, error));
    }

    @Override
    public Mono<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User with email " + email + " not found")))
                .map(userMapper::toDomain)
                .doOnNext(user ->  log.info("User found successfully with email: {}", email))
                .doOnError(error -> log.error("Error while finding user with email {}", email, error));
    }
}
