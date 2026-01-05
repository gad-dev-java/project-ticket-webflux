package com.ticketmaster.user.infrastructure.adapters.input.rest.mapper;

import com.ticketmaster.user.domain.model.UserActivity;
import com.ticketmaster.user.infrastructure.adapters.input.rest.model.response.UserActivityResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN)
public interface UserActivityRestMapper {

    UserActivityResponse toResponse(UserActivity userActivity);
}
