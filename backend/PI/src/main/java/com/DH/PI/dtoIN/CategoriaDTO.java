package com.DH.PI.dtoIN;

import com.DH.PI.model.CategoriaModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO {

    private String categoria;
    private String descricao;
    private String urlImagem;

    public CategoriaDTO(CategoriaModel categoria){
        this.categoria = categoria.getCategoria();
        this.descricao = categoria.getDescricao();
        this.urlImagem = categoria.getUrlImagem();
    }

}
