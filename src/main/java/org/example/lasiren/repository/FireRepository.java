package org.example.lasiren.repository;

import org.example.lasiren.model.Fire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FireRepository extends JpaRepository<Fire, Long> {
    List<Fire> findByClosedFalse();
}
