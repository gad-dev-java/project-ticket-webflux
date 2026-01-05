package com.ticketmaster.user.infrastructure.adapters.input.rest.mapper;

import com.ticketmaster.user.domain.model.Genre;
import com.ticketmaster.user.infrastructure.adapters.input.rest.model.response.GenreResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN)
public interface GenreRestMapper {
    GenreResponse toResponse(Genre genre);
}
