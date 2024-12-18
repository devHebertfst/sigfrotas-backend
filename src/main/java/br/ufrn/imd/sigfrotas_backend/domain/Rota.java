package br.ufrn.imd.sigfrotas_backend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Rota {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_rota")
    @SequenceGenerator(name = "seq_rota", sequenceName = "seq_rota", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "origem_id")
    private Endereco origem;

    @ManyToOne
    @JoinColumn(name = "destino_id")
    private Endereco destino;

    @OneToMany
    private List<Endereco> pontosIntermediarios;

    @ManyToOne
    private Caminhao caminhao;

}
