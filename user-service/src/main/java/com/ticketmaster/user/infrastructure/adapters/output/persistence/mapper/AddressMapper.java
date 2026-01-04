package com.ticketmaster.user.infrastructure.adapters.output.persistence.mapper;

import com.ticketmaster.user.domain.model.Address;
import com.ticketmaster.user.infrastructure.adapters.output.persistence.entities.AddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN)
public interface AddressMapper {
    AddressEntity toEntity(Address address);

    Address toDomain(AddressEntity entity);
}
