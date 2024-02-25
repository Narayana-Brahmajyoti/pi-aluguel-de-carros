package com.DH.PI.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "imagem")
@Entity
public class ImageModel extends RepresentationModel<ImageModel> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idImagem;

    @NotEmpty
    @NotNull
    @NotBlank(message = "Obrigatorio uma descrição valida.")
    @Size(message = "Excedido numero maximo de caracteres", max = 254)
    private String tituloImagem;

    @NotEmpty
    @NotNull
    @NotBlank(message = "Obrigatorio uma descrição valida.")
    @Size(message = "Excedido numero maximo de caracteres", max = 254)
    private String urlImagem;
}
