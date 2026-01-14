package org.example.lasiren.restController;

import jakarta.validation.Valid;
import org.example.lasiren.dto.CreateFireDTO;
import org.example.lasiren.dto.FireDTO;
import org.example.lasiren.mapper.FireMapper;
import org.example.lasiren.model.Fire;
import org.example.lasiren.service.FireService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/fires")
@CrossOrigin("*")
public class FireController {

    private final FireService fireService;

    public FireController(FireService fireService) {
        this.fireService = fireService;
    }

    @PostMapping
    public ResponseEntity<FireDTO> createFire(@Valid @RequestBody CreateFireDTO dto) {
        Fire fire = FireMapper.toEntity(dto);
        Fire created = fireService.createFireAndActivateSirens(fire);

        URI location = URI.create("/api/fires/" + created.getId());
        return ResponseEntity.created(location).body(FireMapper.toDto(created));
    }

    @GetMapping("/active")
    public List<FireDTO> getActiveFires() {
        return fireService.findByClosedFalse()
                .stream()
                .map(FireMapper::toDto)
                .toList();
    }

    @PutMapping("/{id}/closure")
    public ResponseEntity<FireDTO> closeFire(@PathVariable Long id) {
        Fire closed = fireService.closeFire(id);
        return ResponseEntity.ok(FireMapper.toDto(closed));
    }
}



//    @Autowired
//    private FireService fireService;
//
//    @PostMapping
//    public ResponseEntity<Fire> createFire (@RequestBody Fire fire){
//        Fire created = fireService.createFireAndActivateSirens(fire);
//        URI location = URI.create("/api/fires/"+created.getId());
//        return ResponseEntity.created(location).body(created);
//
//    }
//
//    @GetMapping("/active")
//    public List<Fire> getActiveFires (){
//        return fireService.findByClosedFalse();
//
//    }
//
//    @PutMapping("/{id}/closure")
//    public ResponseEntity<Fire> closeFire (@PathVariable Long id){
//        return fireService.closeFire(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }


