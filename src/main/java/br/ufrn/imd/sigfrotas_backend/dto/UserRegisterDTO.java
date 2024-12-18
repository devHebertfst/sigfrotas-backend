package br.ufrn.imd.sigfrotas_backend.dto;

import br.ufrn.imd.sigfrotas_backend.domain.Pessoa;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRegisterDTO {
    private String user;
    private String password;
    private Pessoa pessoa;
}
