package org.example.lasiren.service;

import org.example.lasiren.exception.NotFoundException;
import org.example.lasiren.model.Siren;
import org.example.lasiren.repository.SirenRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SirenService {

    private final SirenRepository sirenRepository;

    public SirenService(SirenRepository sirenRepository) {
        this.sirenRepository = sirenRepository;
    }

    public List<Siren> findAll() {
        return sirenRepository.findAll();
    }

    public Siren findById(Long id) {
        return sirenRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Siren not found with id " + id));
    }

    public Siren create(Siren siren) {
        return sirenRepository.save(siren);
    }

    public Siren update(Long id, Siren siren) {
        Siren existing = findById(id); // genbrug find + exception

        siren.setId(existing.getId()); // bevar id
        return sirenRepository.save(siren);
    }

    public void delete(Long id) {
        if (!sirenRepository.existsById(id)) {
            throw new NotFoundException("Siren not found with id " + id);
        }
        sirenRepository.deleteById(id);
    }
}
