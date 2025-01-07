package br.ufrn.imd.sigfrotas_backend.controller;

import br.ufrn.imd.sigfrotas_backend.domain.Endereco;
import br.ufrn.imd.sigfrotas_backend.domain.Rota;
import br.ufrn.imd.sigfrotas_backend.services.RotaService;
import br.ufrn.imd.sigfrotas_backend.services.SmartRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rotas")
public class RotaController {

    @Autowired
    private RotaService rotaService;

    @Autowired
    private SmartRouteService smartRouteService;

    @GetMapping
    public ResponseEntity<List<Rota>> getAllRotas() {
        List<Rota> rotas = rotaService.findAll();
        return ResponseEntity.ok(rotas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rota> getRotaById(@PathVariable Long id) {
        Optional<Rota> rota = rotaService.findById(id);
        return rota.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Rota> createRota(@RequestBody Rota rota) {
        Rota createdRota = rotaService.save(rota);
        return ResponseEntity.ok(createdRota);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rota> updateRota(@PathVariable Long id, @RequestBody Rota rota) {
        if (!rotaService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Rota updatedRota = rotaService.save(rota);
        return ResponseEntity.ok(updatedRota);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRota(@PathVariable Long id) {
        if (!rotaService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        rotaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/smart-routes/{id}")
    public ResponseEntity<Rota> calculateSmartRouteOptimization(@PathVariable Long id) {
        Optional<Rota> optionalRota = rotaService.findById(id);
        if (!optionalRota.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Rota rota = optionalRota.get();

        // Validar coordenadas
        if (rota.getOrigem().getLatitude() == null || rota.getOrigem().getLongitude() == null ||
                rota.getDestino().getLatitude() == null || rota.getDestino().getLongitude() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        for (Endereco ponto : rota.getPontosIntermediarios()) {
            if (ponto.getLatitude() == null || ponto.getLongitude() == null) {
                return ResponseEntity.badRequest().body(null);
            }
        }

        // Calcular a rota otimizada
        Rota rotaOtimizada = smartRouteService.calcularRotaOtimizadaComOptimization(
                rota.getOrigem(),
                rota.getDestino(),
                rota.getPontosIntermediarios()
        );

        return ResponseEntity.ok(rotaOtimizada);
    }



}
