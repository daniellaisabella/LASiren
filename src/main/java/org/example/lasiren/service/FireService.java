package org.example.lasiren.service;

import org.example.lasiren.exception.NotFoundException;
import org.example.lasiren.model.Fire;
import org.example.lasiren.model.Siren;
import org.example.lasiren.model.Status;
import org.example.lasiren.repository.FireRepository;
import org.example.lasiren.repository.SirenRepository;
import org.example.lasiren.util.GeoUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FireService {

    private final FireRepository fireRepository;
    private final SirenRepository sirenRepository;

    public FireService(FireRepository fireRepository, SirenRepository sirenRepository) {
        this.fireRepository = fireRepository;
        this.sirenRepository = sirenRepository;
    }

    public Fire createFireAndActivateSirens(Fire fire) {
        fire.setClosed(false);
        fire.setStartTime(LocalDateTime.now());

        Fire savedFire = fireRepository.save(fire);
        List<Siren> sirens = sirenRepository.findAll();

        for (Siren siren : sirens) {

            double distance = GeoUtils.distanceKm(
                    savedFire.getLatitude(),
                    savedFire.getLongitude(),
                    siren.getLatitude(),
                    siren.getLongitude()
            );

            // aktiverer sirener indenfor 10 km
            if (distance <= 10.00 && !siren.isDisabled()) {
                siren.setStatus(Status.FARE);
                siren.setFire(savedFire);
                sirenRepository.save(siren);
            }
        }
        return savedFire;
    }

    // opdaterer flere entities derfor @Transactional
    @Transactional
    public Fire closeFire(Long id) {
        Fire fire = fireRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Fire not found with id " + id));

        // 1. Luk brand
        fire.setClosed(true);

        // 2. Find alle sirener tilknyttet denne brand
        List<Siren> sirens = sirenRepository.findByFire(fire);

        // 3. Deaktiver dem
        for (Siren siren : sirens) {
            siren.setStatus(Status.FRED);
            siren.setFire(null);
        }

        // 4. Gem ændringer
        fireRepository.save(fire);
        sirenRepository.saveAll(sirens);

        return fire;
    }

    public List<Fire> findByClosedFalse() {
        return fireRepository.findByClosedFalse();
    }
}
