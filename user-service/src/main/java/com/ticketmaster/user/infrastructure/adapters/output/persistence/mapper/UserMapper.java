package com.ticketmaster.user.infrastructure.adapters.output.persistence.mapper;

import com.ticketmaster.user.domain.model.User;
import com.ticketmaster.user.infrastructure.adapters.output.persistence.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN)
public interface UserMapper {

    @Mapping(source = "userStatus", target = "status")
    UserEntity toEntity(User user);

    @Mapping(source = "status", target = "userStatus")
    User toDomain(UserEntity userEntity);
}
