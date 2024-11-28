package br.ufrn.imd.sigfrotas_backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Table(name = "motorista")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Motorista {
    private UUID id;
    private String nome;
}
