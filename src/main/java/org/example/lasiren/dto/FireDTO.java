package org.example.lasiren.dto;

import java.time.LocalDateTime;
import java.util.List;

public record FireDTO(
        Long id,
        double latitude,
        double longitude,
        LocalDateTime startTime,
        boolean closed,
        List<Long> sirenIds
) {}
