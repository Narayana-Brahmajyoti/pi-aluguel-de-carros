package com.DH.PI.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;


import java.sql.Time;
import java.sql.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Reserva")
@Entity
//@JsonIgnoreProperties({"produto"})
public class ReservaModel extends RepresentationModel<ReservaModel> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReserva;

    @DateTimeFormat(pattern="hh:mm:ss")
    private Time horarioInicioReserva;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dataInicioReserva;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dataFinalReserva;

    @JsonIgnore
    @ManyToOne( fetch = FetchType.EAGER)
    @JoinColumn(name = "id_produto")
    private ProdutoModel produto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario")
    private UsuarioModel usuario;



}
