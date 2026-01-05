package com.ticketmaster.user.infrastructure.adapters.output.persistence.mapper;

import com.ticketmaster.user.domain.model.Genre;
import com.ticketmaster.user.infrastructure.adapters.output.persistence.entities.GenreEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN)
public interface GenreMapper {
    Genre toDomain(GenreEntity genreEntity);
}
