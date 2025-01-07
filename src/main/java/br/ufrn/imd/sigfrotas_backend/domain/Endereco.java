package br.ufrn.imd.sigfrotas_backend.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_endereco")
    @SequenceGenerator(name = "seq_endereco", sequenceName = "seq_endereco", allocationSize = 1)
    private Long id;
    private String rua;
    private String numero;
    private String cep;
    private String bairro;
    private String cidade;
    private String estado;

    public Endereco() {

    }
}
