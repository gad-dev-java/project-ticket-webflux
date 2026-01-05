package com.ticketmaster.user.infrastructure.adapters.output.persistence.mapper;

import com.ticketmaster.user.domain.model.PaymentMethod;
import com.ticketmaster.user.infrastructure.adapters.output.persistence.entities.PaymentMethodEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN)
public interface PaymentMethodMapper {
    PaymentMethodEntity toEntity(PaymentMethod paymentMethod);

    PaymentMethod toDomain(PaymentMethodEntity paymentMethodEntity);
}
