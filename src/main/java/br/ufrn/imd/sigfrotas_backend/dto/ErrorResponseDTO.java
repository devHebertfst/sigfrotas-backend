package br.ufrn.imd.sigfrotas_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDTO implements Serializable {
    private int status;
    private String message;
    private String timestamp;
}

