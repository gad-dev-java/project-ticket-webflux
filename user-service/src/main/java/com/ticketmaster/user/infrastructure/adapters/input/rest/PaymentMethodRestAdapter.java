package com.ticketmaster.user.infrastructure.adapters.input.rest;

import com.ticketmaster.user.application.ports.input.PaymentMethodUseCase;
import com.ticketmaster.user.infrastructure.adapters.input.rest.mapper.PaymentMethodRestMapper;
import com.ticketmaster.user.infrastructure.adapters.input.rest.model.request.CreatePaymentMethodRequest;
import com.ticketmaster.user.infrastructure.adapters.input.rest.model.response.DataResponse;
import com.ticketmaster.user.infrastructure.adapters.input.rest.model.response.PaymentMethodResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/payment-methods")
@RequiredArgsConstructor
public class PaymentMethodRestAdapter {
    private final PaymentMethodUseCase paymentMethodUseCase;
    private final PaymentMethodRestMapper paymentMethodRestMapper;

    @PostMapping("/user/{userId}")
    public Mono<ResponseEntity<DataResponse<PaymentMethodResponse>>> addPaymentMethod(@PathVariable String userId,
                                                                                      @RequestBody CreatePaymentMethodRequest request) {
        var command = paymentMethodRestMapper.toCommand(request);
        return paymentMethodUseCase.addPaymentMethod(UUID.fromString(userId), command)
                .map(paymentMethodRestMapper::toResponse)
                .map(payment -> {
                    var bodyResponse = DataResponse.<PaymentMethodResponse>builder()
                            .status(HttpStatus.OK.value())
                            .message("Payment method added successfully")
                            .data(payment)
                            .timestamp(LocalDateTime.now())
                            .build();
                    return ResponseEntity.ok(bodyResponse);
                });
    }

    @GetMapping("/user/{userId}/payment-method")
    public Mono<ResponseEntity<DataResponse<List<PaymentMethodResponse>>>> getPaymentMethods(@PathVariable String userId) {
        return paymentMethodUseCase.getUserPaymentMethods(UUID.fromString(userId))
                .map(paymentMethodRestMapper::toResponse)
                .collectList()
                .map(payments -> {
                    var bodyResponse = DataResponse.<List<PaymentMethodResponse>>builder()
                            .status(HttpStatus.OK.value())
                            .message("Payment methods retrieved successfully")
                            .data(payments)
                            .timestamp(LocalDateTime.now())
                            .build();
                    return ResponseEntity.ok(bodyResponse);
                });
    }

    @DeleteMapping("/user/{userId}/payment-method/{paymentId}")
    public Mono<ResponseEntity<DataResponse<Void>>> deletePaymentMethod(@PathVariable String userId,
                                                                        @PathVariable String paymentId) {
        return paymentMethodUseCase.removePaymentMethod(UUID.fromString(userId), UUID.fromString(paymentId))
                .then(Mono.just(DataResponse.<Void>builder()
                        .status(HttpStatus.NO_CONTENT.value())
                        .message("Payment Method deleted")
                        .timestamp(LocalDateTime.now())
                        .build())
                )
                .map(ResponseEntity::ok);
    }
}
