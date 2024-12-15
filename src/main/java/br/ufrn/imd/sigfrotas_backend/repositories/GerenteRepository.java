package br.ufrn.imd.sigfrotas_backend.repositories;

import br.ufrn.imd.sigfrotas_backend.domain.Gerente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GerenteRepository extends JpaRepository<Gerente, Long> {
    Optional<Gerente> findById(Long id);
}
