package br.ufrn.imd.sigfrotas_backend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Rota {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_rota")
    @SequenceGenerator(name = "seq_rota", sequenceName = "seq_rota", allocationSize = 1)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "origem_id")
    private Endereco origem;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "destino_id")
    private Endereco destino;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Endereco> pontosIntermediarios;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Caminhao caminhao;
}
