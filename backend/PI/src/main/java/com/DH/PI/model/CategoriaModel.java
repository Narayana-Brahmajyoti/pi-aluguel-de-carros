package com.DH.PI.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "categoria")
@Entity

public class CategoriaModel extends RepresentationModel<CategoriaModel> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @NotNull
    @NotBlank(message = "Obrigatorio uma descrição valida.")
    @Column(unique = true)
    private String categoria;

    @NotEmpty
    @NotNull
    @NotBlank(message = "Obrigatorio uma descrição valida.")
    @Size(message = "Excedido numero maximo de caracteres", max = 254)
    private String descricao;

    @NotEmpty
    @NotNull
    @NotBlank(message = "Obrigatorio uma descrição valida.")
    private String urlImagem;


}
