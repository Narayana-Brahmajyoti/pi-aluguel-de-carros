package com.DH.PI.dtoIN;

import jakarta.persistence.Column;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTOIN {

    @NotEmpty
    @NotNull
    @NotBlank(message = "Obrigatório uma descrição valida.")
    private String nomeUsuario;

    @NotEmpty
    @NotNull
    @NotBlank(message = "Obrigatório uma descrição valida.")
    private String sobrenomeUsuario;

    @NotEmpty
    @NotNull
    @NotBlank(message = "Obrigatório uma descrição valida.")
    @Email
    @Column(unique = true)
    private String emailUsuario;

    @NotEmpty
    @NotNull
    @NotBlank(message = "Obrigatório uma descrição valida.")
    @Size(min = 8, message = "É necessario uma quantidade minima de 8 caracteres para senha")
    private String senhaUsuario;
}
