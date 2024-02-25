package com.DH.PI.dtoIN;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {
    @NotEmpty
    @NotNull
    @NotBlank(message = "Obrigatório uma descrição valida.")
    @Size(message = "Excedido número máximo de caracteres", max = 254)
    private String nomeProduto;

    @NotEmpty
    @NotNull
    @NotBlank(message = "Obrigatório uma descrição valida.")
    @Size(message = "Excedido número máximo de caracteres", max = 254)
    private String descricaoProduto;


    private List<CaracteristicaDTOID> caracteristicas;


    private List<ImageDTOID> imagem;


    private CategoriaDTOID categoria;


    private CidadeDTOID cidade;




}
