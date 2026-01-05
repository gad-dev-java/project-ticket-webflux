package com.ticketmaster.user.domain.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Genre {
    private UUID genreId;
    private String name;
}
