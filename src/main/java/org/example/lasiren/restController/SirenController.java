package org.example.lasiren.restController;

import jakarta.validation.Valid;
import org.example.lasiren.dto.CreateSirenDTO;
import org.example.lasiren.dto.SirenDTO;
import org.example.lasiren.mapper.SirenMapper;
import org.example.lasiren.model.Siren;
import org.example.lasiren.service.SirenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/sirens")
@CrossOrigin("*")
public class SirenController {

    private final SirenService sirenService;

    public SirenController(SirenService sirenService) {
        this.sirenService = sirenService;
    }

    // Find all
    @GetMapping
    public List<SirenDTO> findAll() {
        return sirenService.findAll()
                .stream()
                .map(SirenMapper::toDto)
                .toList();
    }

    // Get one
    @GetMapping("/{id}")
    public ResponseEntity<SirenDTO> getSiren(@PathVariable Long id) {
        Siren s = sirenService.findById(id);
        return ResponseEntity.ok(SirenMapper.toDto(s));
    }

    // Create
    @PostMapping
    public ResponseEntity<SirenDTO> createSiren(@Valid @RequestBody CreateSirenDTO dto) {
        Siren siren = SirenMapper.toEntity(dto);
        Siren saved = sirenService.create(siren);

        URI location = URI.create("/api/sirens/" + saved.getId());
        return ResponseEntity.created(location).body(SirenMapper.toDto(saved));
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<SirenDTO> updateSiren(@PathVariable Long id, @Valid @RequestBody CreateSirenDTO dto) {
        Siren siren = SirenMapper.toEntity(dto);
        Siren updated = sirenService.update(id, siren);
        return ResponseEntity.ok(SirenMapper.toDto(updated));
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSiren(@PathVariable Long id) {
        sirenService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

//package org.example.lasiren.restController;
//
//import org.example.lasiren.model.Siren;
//import org.example.lasiren.service.SirenService;
//import org.springframework.web.bind.annotation.*;
//
//import org.springframework.http.ResponseEntity;
//import java.net.URI;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/sirens")
//@CrossOrigin("*")
//public class SirenController {
//
//    private final SirenService sirenService;
//
//    public SirenController(SirenService sirenService) {
//        this.sirenService = sirenService;
//    }
//
//    // Find all
//    @GetMapping
//    public List<Siren> findAll() {
//        return sirenService.findAll();
//    }
//
//    // Get one
//    @GetMapping("/{id}")
//    public ResponseEntity<Siren> getSiren(@PathVariable Long id) {
//        Siren siren = sirenService.findById(id);
//        return ResponseEntity.ok(siren);}
//
//    // Create
//    // URI = Uniform Resource Identifier; det er en adresse der peger på en ressource (instans af objektet) i mit API
//    // REST- princip: Når du opretter en ny ressource, skal sereveren forællte hvor den findes (location)
//    @PostMapping
//    public ResponseEntity<Siren> createSiren(@RequestBody Siren siren) {
//        Siren saved = sirenService.create(siren);
//
//        URI location = URI.create("/api/sirens/" + saved.getId());
//        return ResponseEntity.created(location).body(saved);
//    }
//
//    // Update
//    @PutMapping("/{id}")
//    public ResponseEntity<Siren> updateSiren(@PathVariable Long id, @RequestBody Siren siren) {
//        Siren updated = sirenService.update(id, siren);
//        return ResponseEntity.ok(updated);
//    }
//
//    // Delete
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteSiren(@PathVariable Long id) {
//        sirenService.delete(id);
//        return ResponseEntity.noContent().build();
//    }
//}
