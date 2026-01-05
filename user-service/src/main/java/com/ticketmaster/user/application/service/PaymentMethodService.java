package com.ticketmaster.user.application.service;

import com.ticketmaster.user.application.ports.input.PaymentMethodUseCase;
import com.ticketmaster.user.application.ports.input.dto.CreatePaymentMethodCommand;
import com.ticketmaster.user.application.ports.input.dto.PaymentMethodDto;
import com.ticketmaster.user.application.ports.output.PaymentMethodPersistencePort;
import com.ticketmaster.user.application.ports.output.UserPersistencePort;
import com.ticketmaster.user.domain.exception.UserNotFoundException;
import com.ticketmaster.user.domain.model.PaymentMethod;
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
public class PaymentMethodService implements PaymentMethodUseCase {
    private final PaymentMethodPersistencePort paymentMethodPersistencePort;
    private final UserPersistencePort userPersistencePort;

    @Override
    public Mono<PaymentMethodDto> addPaymentMethod(UUID userId, CreatePaymentMethodCommand command) {
        return userPersistencePort.findUserById(userId)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User not found with id: " + userId)))
                .flatMap(user -> {
                    Mono<Void> prepareDefaults = Boolean.TRUE.equals(command.isDefault())
                            ? paymentMethodPersistencePort.removeDefaultStatusFromAll(userId)
                            : Mono.empty();

                    return prepareDefaults.then(Mono.defer(() -> {
                        var newPayment = PaymentMethod.builder()
                                .userId(userId)
                                .token(command.token())
                                .brand(command.brand())
                                .lastFour(command.lastFour())
                                .holderName(command.holderName())
                                .isDefault(command.isDefault())
                                .createdAt(LocalDateTime.now())
                                .build();
                        return paymentMethodPersistencePort.save(newPayment);
                    }));
                })
                .map(this::toDto)
                .doOnNext(payment -> log.info("Payment add successfull: {}", payment))
                .doOnError(error -> log.info("Payment add failed: ", error));
    }

    @Override
    public Flux<PaymentMethodDto> getUserPaymentMethods(UUID userId) {
        return paymentMethodPersistencePort.findByUserId(userId)
                .map(this::toDto)
                .doOnNext(payment -> log.info("Payment get successfull: {}", payment))
                .doOnError(error -> log.info("Payment get failed: ", error));
    }

    @Override
    public Mono<Void> removePaymentMethod(UUID userId, UUID paymentMethodId) {
        return paymentMethodPersistencePort.deleteById(paymentMethodId)
                .doOnSuccess(result -> log.info("Payment remove successfull: {}", paymentMethodId))
                .then();
    }

    private PaymentMethodDto toDto(PaymentMethod paymentMethod) {
        return PaymentMethodDto.builder()
                .paymentMethodId(paymentMethod.getPaymentMethodId())
                .token(paymentMethod.getToken())
                .brand(paymentMethod.getBrand())
                .holderName(paymentMethod.getHolderName())
                .build();
    }
}
