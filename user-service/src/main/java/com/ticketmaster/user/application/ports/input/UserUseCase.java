package com.ticketmaster.user.application.ports.input;

import com.ticketmaster.user.application.ports.input.dto.UpsertUserCommand;
import com.ticketmaster.user.application.ports.input.dto.UserDto;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserUseCase {
    Mono<UserDto> registerUserOrUpdate(UpsertUserCommand command);
    Mono<UserDto> getUserProfile(UUID userId);
}
