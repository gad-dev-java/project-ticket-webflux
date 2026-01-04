package com.ticketmaster.user.application.ports.output;

import com.ticketmaster.user.domain.model.User;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserPersistencePort {
    Mono<User> saveUser(User user);
    Mono<User> updateUser(User user);
    Mono<User> findUserById(UUID userId);
    Mono<User> findUserByEmail(String email);
}
