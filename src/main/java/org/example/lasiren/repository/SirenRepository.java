package org.example.lasiren.repository;

import org.example.lasiren.model.Fire;
import org.example.lasiren.model.Siren;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SirenRepository extends JpaRepository<Siren, Long> {
    List<Siren> findByFire(Fire fire);
}
