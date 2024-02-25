package com.DH.PI.dtoOut;

import com.DH.PI.model.FuncaoUsuarioModel;
import com.DH.PI.model.UsuarioModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsuarioDTOOUT extends RepresentationModel<UsuarioDTOOUT> {
    private Long id;
    private String nomeUsuario;
    private String sobrenomeUsuario;
    private String emailUsuario;
    private List<FuncaoUsuarioModel> permisao;
}
