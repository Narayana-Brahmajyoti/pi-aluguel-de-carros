package com.DH.PI.dtoIN;

import com.DH.PI.model.CidadeModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CidadeDTO {

    private String nomeCidade;
    private String paisCidade;

    public CidadeDTO(CidadeModel cidade){
        this.nomeCidade = cidade.getNomeCidade();
        this.paisCidade = cidade.getPaisCidade();
    }
}
