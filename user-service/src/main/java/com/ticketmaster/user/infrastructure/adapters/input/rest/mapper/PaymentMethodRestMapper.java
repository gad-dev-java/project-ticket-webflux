package com.ticketmaster.user.infrastructure.adapters.input.rest.mapper;

import com.ticketmaster.user.application.ports.input.dto.CreatePaymentMethodCommand;
import com.ticketmaster.user.application.ports.input.dto.PaymentMethodDto;
import com.ticketmaster.user.infrastructure.adapters.input.rest.model.request.CreatePaymentMethodRequest;
import com.ticketmaster.user.infrastructure.adapters.input.rest.model.response.PaymentMethodResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN)
public interface PaymentMethodRestMapper {
    CreatePaymentMethodCommand toCommand(CreatePaymentMethodRequest request);

    PaymentMethodResponse toResponse(PaymentMethodDto paymentMethodDto);
}
