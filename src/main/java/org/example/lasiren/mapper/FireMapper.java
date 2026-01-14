package org.example.lasiren.mapper;

import org.example.lasiren.dto.CreateFireDTO;
import org.example.lasiren.dto.FireDTO;
import org.example.lasiren.model.Fire;

import java.time.LocalDateTime;

public class FireMapper {

    public static FireDTO toDto(Fire fire) {
        return new FireDTO(
                fire.getId(),
                fire.getLatitude(),
                fire.getLongitude(),
                fire.getStartTime(),
                fire.isClosed(),
                fire.getSirens().stream()
                        .map(s -> s.getId())
                        .toList()
        );
    }

    public static Fire toEntity(CreateFireDTO dto) {
        Fire fire = new Fire();
        fire.setLatitude(dto.latitude());
        fire.setLongitude(dto.longitude());
        fire.setStartTime(LocalDateTime.now());
        fire.setClosed(false);
        return fire;
    }
}
