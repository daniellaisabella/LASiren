package org.example.lasiren.restController;

import org.example.lasiren.model.Fire;
import org.example.lasiren.model.Status;
import org.example.lasiren.repository.FireRepository;
import org.example.lasiren.service.FireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/fires")
@CrossOrigin("*")
public class FireController {

    @Autowired
    private FireService fireService;

    @PostMapping
    public ResponseEntity<Fire> createFire (@RequestBody Fire fire){
        Fire created = fireService.createFireAndActivateSirens(fire);
        URI location = URI.create("/api/fires/"+created.getId());
        return ResponseEntity.created(location).body(created);

    }

    @GetMapping("/active")
    public List<Fire> getActiveFires (){
        return fireService.findByClosedFalse();

    }

    @PutMapping("/{id}/closure")
    public ResponseEntity<Fire> closeFire (@PathVariable Long id){
        return fireService.closeFire(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
