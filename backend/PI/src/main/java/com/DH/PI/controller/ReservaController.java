package com.DH.PI.controller;

import com.DH.PI.dtoIN.ReservaDTO;
import com.DH.PI.dtoOut.ReservaDTOOUT;
import com.DH.PI.exception.ResourceBadRequest;
import com.DH.PI.exception.ResourceConflict;
import com.DH.PI.exception.ResourceNotFoundException;
import com.DH.PI.model.ProdutoModel;
import com.DH.PI.model.ReservaModel;
import com.DH.PI.service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/reserva")
@Tag(name = "Reserva")
@CrossOrigin(origins ="*" )
@SecurityRequirement(name = "bearerAuth")
public class ReservaController {
    @Autowired
    ReservaService service;
    @PostMapping
    @Operation( summary = " Salva uma reserva USER",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "CREATED. retorna uma URL location no header"
                    )
            })
    public ResponseEntity save(@RequestBody @Valid ReservaDTO reserva) throws ResourceConflict, ResourceNotFoundException, ResourceBadRequest {
        ReservaModel reservaModel= service.save(reserva);
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(reservaModel.getIdReserva())
//                .toUri();
//        return ResponseEntity.created(location).build();
        return new  ResponseEntity(reservaModel,HttpStatus.CREATED);
    }
    @GetMapping
    @Operation( summary = " Retorna uma lista de reservas USER",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK. Retorna uma lista de  categorias"
                    )
            })
    public  ResponseEntity findAll() throws ResourceNotFoundException {
        List<ReservaDTOOUT> reservaModelList = service.findAll();
        for (ReservaDTOOUT reservaModel : reservaModelList){
            long id = reservaModel.getIdReserva();
            reservaModel.add(linkTo(methodOn(ReservaController.class).findById(id)).withSelfRel());
        }
        return new ResponseEntity(reservaModelList, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    @Operation( summary = " Retorna uma unica Reserva USER",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK. Retorna uma unica Reserva"
                    )
            })
    public ResponseEntity findById(@PathVariable Long id) throws ResourceNotFoundException {
        ReservaDTOOUT reservaDTOOUT = service.findById(id);
        reservaDTOOUT.add(linkTo(methodOn(ReservaController.class).findAll()).withRel("lista de Reservas"));
        return new ResponseEntity(reservaDTOOUT,HttpStatus.OK);
    }
    @GetMapping(path = "/findusuario")
    @Operation( summary = " Retorna uma lista de Reserva USER",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK. Retorna uma lista Reserva"
                    )
            })
    public ResponseEntity findByIdUsuario(@RequestParam (value = "id") Long id) throws ResourceNotFoundException {
        List<ReservaDTOOUT> reservaModel = service.findByIdUsuario(id);
        return new ResponseEntity(reservaModel,HttpStatus.OK);
    }
    @PatchMapping
    @Operation( summary = " Edita uma unica reserva USER",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK. Retorna a reserva editada"
                    )
            })
    public ResponseEntity change(@RequestBody @Valid ReservaModel reservaModel) throws ResourceConflict, ResourceNotFoundException {
        ReservaDTOOUT reservaReturn = service.change(reservaModel);
        reservaReturn.add(linkTo(methodOn(ReservaController.class).findById(reservaReturn.getIdReserva())).withSelfRel());
        return  new ResponseEntity(reservaReturn,HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    @Operation( summary = " Exclui uma reserva USER",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK. Retorna uma mensagem de exclusão"
                    )
            })
    public ResponseEntity delete(@PathVariable Long id) throws ResourceNotFoundException {
        service.delete(id);
        return new ResponseEntity("Excuido produto de id "+id, HttpStatus.OK);
    }

}
