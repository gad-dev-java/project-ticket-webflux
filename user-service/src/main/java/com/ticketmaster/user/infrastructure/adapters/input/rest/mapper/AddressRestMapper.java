package com.ticketmaster.user.infrastructure.adapters.input.rest.mapper;

import com.ticketmaster.user.application.ports.input.dto.AddressDto;
import com.ticketmaster.user.application.ports.input.dto.CreateAddressCommand;
import com.ticketmaster.user.infrastructure.adapters.input.rest.model.request.CreateAddressRequest;
import com.ticketmaster.user.infrastructure.adapters.input.rest.model.response.AddressResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN)
public interface AddressRestMapper {
    CreateAddressCommand toCommand(CreateAddressRequest request);

    AddressResponse toResponse(AddressDto addressDto);
}
