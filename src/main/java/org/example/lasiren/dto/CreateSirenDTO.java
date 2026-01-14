package org.example.lasiren.dto;

import jakarta.validation.constraints.NotNull;

public record CreateSirenDTO(
        @NotNull Double latitude,
        @NotNull Double longitude,
        boolean disabled
) {}
