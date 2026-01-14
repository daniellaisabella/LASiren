package org.example.lasiren.mapper;

import org.example.lasiren.dto.CreateSirenDTO;
import org.example.lasiren.dto.SirenDTO;
import org.example.lasiren.model.Siren;
import org.example.lasiren.model.Status;

public class SirenMapper {

    public static SirenDTO toDto(Siren s) {
        return new SirenDTO(
                s.getId(),
                s.getLatitude(),
                s.getLongitude(),
                s.getStatus(),
                s.isDisabled(),
                s.getFire() != null ? s.getFire().getId() : null
        );
    }

    public static Siren toEntity(CreateSirenDTO dto) {
        Siren s = new Siren();
        s.setLatitude(dto.latitude());
        s.setLongitude(dto.longitude());
        s.setDisabled(dto.disabled());
        s.setStatus(Status.FRED); // default
        return s;
    }
}
