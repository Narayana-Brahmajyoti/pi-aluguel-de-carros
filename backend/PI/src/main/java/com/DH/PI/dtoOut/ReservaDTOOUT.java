package com.DH.PI.dtoOut;

import com.DH.PI.model.ProdutoModel;
import com.DH.PI.model.UsuarioModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;

import java.sql.Date;
import java.sql.Time;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDTOOUT extends RepresentationModel<ReservaDTOOUT> {
    private Long idReserva;

    @DateTimeFormat(pattern="hh:mm:ss")
    private Time horarioInicioReserva;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dataInicioReserva;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dataFinalReserva;

    private ProdutoModel produto;

    private UsuarioDTOOUT usuario;
}
