package com.DH.PI.dtoIN;

import com.DH.PI.model.ProdutoModel;
import com.DH.PI.model.UsuarioModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
public class ReservaDTO {

    @DateTimeFormat(pattern="hh:mm:ss")
    private Time horarioInicioReserva;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dataInicioReserva;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dataFinalReserva;

    private ProdutoDTOID produto;

    private UsuarioDTOID usuario;
}
