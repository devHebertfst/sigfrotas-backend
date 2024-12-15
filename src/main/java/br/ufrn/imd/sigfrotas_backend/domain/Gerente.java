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
@Table(name = "gerente")
public class Gerente  {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GERENTE")
    @SequenceGenerator(name = "SEQ_GERENTE", sequenceName = "seq_gerente", allocationSize = 1)
    private Long id;

    @OneToOne
    private Pessoa pessoa;

    private String cnpj;
}