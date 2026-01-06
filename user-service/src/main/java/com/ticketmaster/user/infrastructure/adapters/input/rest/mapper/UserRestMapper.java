package com.ticketmaster.user.infrastructure.adapters.input.rest.mapper;

import com.ticketmaster.user.application.ports.input.dto.UserDto;
import com.ticketmaster.user.infrastructure.adapters.input.rest.model.response.UserResponse;
import com.ticketmaster.user.infrastructure.adapters.input.rest.model.response.UserSummaryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN)
public interface UserRestMapper {
    UserResponse toResponse(UserDto userDto);
    UserSummaryResponse toSummaryResponse(UserDto userDto);
}
