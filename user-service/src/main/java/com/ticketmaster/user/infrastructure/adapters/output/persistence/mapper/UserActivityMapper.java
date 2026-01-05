package com.ticketmaster.user.infrastructure.adapters.output.persistence.mapper;

import com.netflix.appinfo.ApplicationInfoManager;
import com.ticketmaster.user.domain.model.UserActivity;
import com.ticketmaster.user.infrastructure.adapters.output.persistence.entities.UserActivityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN)
public interface UserActivityMapper {
    UserActivityEntity toEntity(UserActivity activity);

    UserActivity toDomain(UserActivityEntity entity);
}
