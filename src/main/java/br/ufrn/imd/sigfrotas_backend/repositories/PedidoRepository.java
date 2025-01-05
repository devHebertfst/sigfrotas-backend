package br.ufrn.imd.sigfrotas_backend.repositories;

import br.ufrn.imd.sigfrotas_backend.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
