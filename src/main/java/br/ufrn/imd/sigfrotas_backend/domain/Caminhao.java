package br.ufrn.imd.sigfrotas_backend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "caminhao")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Caminhao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CAMINHAO")
    @SequenceGenerator(name = "SEQ_CAMINHAO", sequenceName = "seq_caminhao", allocationSize = 1)
    private Long id;
    private String marca;
    private String modelo;
    private double altura;
    private double cargaMaxima;



}
