package com.DH.PI.dtoIN;

import com.DH.PI.model.CaracteristicaModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CaracteristicaDTO {

    private String iconeCaracteristica;
    private String nomeCaracteristica;

    public CaracteristicaDTO(CaracteristicaModel caracteristica){
        this.iconeCaracteristica = caracteristica.getIconeCaracteristica();
        this.nomeCaracteristica = caracteristica.getNomeCaracteristica();
    }

}
