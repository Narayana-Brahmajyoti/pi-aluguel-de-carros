package com.DH.PI.dtoOut;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTOOUT {
    private String token;
    private String tipo;
    private Long idUsuario;
    private String nomeUsuario;
    private String sobreNomeUsuario;
}
