package br.ufrn.imd.sigfrotas_backend.repositories;

import br.ufrn.imd.sigfrotas_backend.domain.Motorista;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MotoristaRepository extends JpaRepository<Motorista, Long> {
    Optional<Motorista> findByCpf(String cpf);
}
