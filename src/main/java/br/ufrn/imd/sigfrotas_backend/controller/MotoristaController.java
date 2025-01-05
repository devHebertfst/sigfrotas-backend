package br.ufrn.imd.sigfrotas_backend.controller;

import br.ufrn.imd.sigfrotas_backend.domain.Motorista;
import br.ufrn.imd.sigfrotas_backend.services.MotoristaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/motoristas")
public class MotoristaController {

    @Autowired
    private MotoristaService motoristaService;

    @GetMapping
    public ResponseEntity<List<Motorista>> getAllMotoristas() {
        List<Motorista> motoristas = motoristaService.findAll();
        return ResponseEntity.ok(motoristas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Motorista> getMotoristaById(@PathVariable Long id) {
        Optional<Motorista> motorista = motoristaService.findById(id);
        return motorista.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Motorista> createMotorista(@RequestBody Motorista motorista) {
        Motorista createdMotorista = motoristaService.save(motorista);
        return ResponseEntity.ok(createdMotorista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Motorista> updateMotorista(@PathVariable Long id, @RequestBody Motorista motorista) {
        if (!motoristaService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        motorista.setId(id);
        Motorista updatedMotorista = motoristaService.save(motorista);
        return ResponseEntity.ok(updatedMotorista);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMotorista(@PathVariable Long id) {
        if (!motoristaService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        motoristaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
