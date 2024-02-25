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
@Table(name = "cidade")
@Entity
public class CidadeModel extends RepresentationModel<CidadeModel> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCidade;

    @NotEmpty
    @NotNull
    @NotBlank(message = "Obrigatorio uma descrição valida.")
    @Column(unique = true)
    @Size(message = "Excedido numero maximo de caracteres", max = 254)
    private String nomeCidade;

    @NotEmpty
    @NotNull
    @NotBlank(message = "Obrigatorio uma descrição valida.")
    @Size(message = "Excedido numero maximo de caracteres", max = 254)
    private String paisCidade;
}
