package br.ufrn.imd.sigfrotas_backend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "motorista")
public class Motorista  {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MOTORISTA")
    @SequenceGenerator(name = "SEQ_MOTORISTA", sequenceName = "seq_motorista", allocationSize = 1)
    private Long id;

    @OneToOne
    private Pessoa pessoa;

    @ManyToOne
    @JoinColumn(name = "caminhao_id")
    private Caminhao caminhao;


}
