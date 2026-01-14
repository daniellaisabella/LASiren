package org.example.lasiren.dto;

import org.example.lasiren.model.Status;

public record SirenDTO(
        Long id,
        double latitude,
        double longitude,
        Status status,
        boolean disabled,
        Long fireId
) {}
