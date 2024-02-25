package com.DH.PI.dtoOut;

import com.DH.PI.dtoIN.PermisaoDTOID;
import com.DH.PI.model.FuncaoUsuarioModel;
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
public class UsuarioDTOADMINOUT extends RepresentationModel<UsuarioDTOADMINOUT> {
    private Long id;
    private String emailUsuario;
    private List<FuncaoUsuarioModel> permissao;
}
