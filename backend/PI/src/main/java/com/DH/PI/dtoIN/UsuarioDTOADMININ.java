package com.DH.PI.dtoIN;

import com.DH.PI.model.FuncaoUsuarioModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTOADMININ {
    private Long id;
    private String emailUsuario;
    private List<PermisaoDTOID> permissao;
}
