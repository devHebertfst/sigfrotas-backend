package br.ufrn.imd.sigfrotas_backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PEDIDO")
    @SequenceGenerator(name = "SEQ_PEDIDO", sequenceName = "seq_pedido", allocationSize = 1)
    private Long id;

    @ManyToOne
    private Cliente cliente;
}
