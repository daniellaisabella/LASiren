package org.example.lasiren.restController;

import org.example.lasiren.model.Siren;
import org.example.lasiren.repository.SirenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/sirens")
@CrossOrigin("*")
public class SirenController {

    @Autowired
    private SirenRepository sirenRepository;

    // CRUD FOR SIRENER
    // Metoderne har samme URI, men forskellig HTTP-metode - og denne bestemmer hvad der sker

    //Find all
    @GetMapping
    public List<Siren> findAll (){
        return sirenRepository.findAll();
    }

    //Get one
    @GetMapping("/{id}")
    public ResponseEntity<Siren> getSiren (@PathVariable Long id){
        return sirenRepository.findById(id) // returnerer Optional<Siren> fordi den måske ikke findes
                .map(ResponseEntity::ok) //hvis den findes transformeres Siren -> Optional<ResponseEntity<Siren>> så den kan returnere hele HTTP response (pakkes ind i HTTP 200 response + JSON)
                .orElse(ResponseEntity.notFound() //hvis den ikke findes returnerer 404 Not Found
                        .build()); //og build bygger HTTP response med tom body, fordi instansen ikke findes

    }

    // Create
    // URI = Uniform Resource Identifier; det er en adresse der peger på en ressource (instans af objektet) i mit API
    // REST- princip: Når du opretter en ny ressource, skal sereveren forællte hvor den findes (location)
    @PostMapping
    public ResponseEntity<Siren> createSiren(@RequestBody Siren siren){
        Siren saved = sirenRepository.save(siren);

        URI location = URI.create("/sirens/"+saved.getId());

        return ResponseEntity.created(location).body(saved); //returnerer HTTP status, location-header peger på ny URL til ressource-endpointet, JSON body

    }

    @PutMapping("/{id}")
    public ResponseEntity<Siren> updateBoat(@PathVariable Long id, @RequestBody Siren siren) {
        return sirenRepository.findById(id)
                .map(existing -> { //lambda er en funktion uden navn - her: vhis siren eksisterer, så tag parameter navngivet exisiting og gør følgende
                    siren.setId(existing.getId()); //bevar id
                    Siren updatedSiren = sirenRepository.save(siren);
                    return ResponseEntity.ok(updatedSiren);
                })
                .orElse(ResponseEntity.notFound().build());

    }

    // Jeg bruger ikke lambda her, fordi findById returnerer Optional, og kombinationen af Optional, generics og ResponseEntity gør koden unødigt kompleks
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSiren(@PathVariable Long id) {

        if (!sirenRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        sirenRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }



}
