package com.DH.PI.controller;

import com.DH.PI.dtoIN.CaracteristicaDTO;
import com.DH.PI.exception.ResourceConflict;
import com.DH.PI.exception.ResourceNotFoundException;
import com.DH.PI.model.CaracteristicaModel;
import com.DH.PI.model.ProdutoModel;
import com.DH.PI.service.CaracteristicaService;
//import io.swagger.annotations.Api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/caracteristica")
@Tag(name = "Caracteristica")
@CrossOrigin(origins ="*" )
@SecurityRequirement(name = "bearerAuth")
public class CaracteristicaController {
    private static final Logger logger = LogManager.getLogger(CaracteristicaController.class);
    @Autowired
    CaracteristicaService service;
    // Metodo POST responsavel por adicionar uma categoria
    @PostMapping
    @Operation( summary = " Salva uma caracteristica ADMIN",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "CREATED. Salva uma caracteristica"
                    )
            })
    public ResponseEntity save(@RequestBody CaracteristicaDTO caracteristica) throws SQLException, ResourceConflict {
        CaracteristicaModel caracteristicaModel = service.save(caracteristica);
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(caracteristicaModel.getIdCaracteristica())
//                .toUri();
//        return ResponseEntity.created(location).build();
        return new  ResponseEntity(caracteristicaModel,HttpStatus.CREATED);
    }
    // Metodo GET responsavel por buscar todas as categorias
    @GetMapping
    @Operation( summary = " Retorna uma lista de caracteristicas USER",
    responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK. Retorna uma lista de caracteristicas"
            )
    })
    public  ResponseEntity findAll() throws ResourceNotFoundException {
        logger.info("Gerando hatereos");
        List<CaracteristicaModel> caracteristicaModelList = service.findAll();
        for (CaracteristicaModel caracteristicaModel : caracteristicaModelList){
            long id = caracteristicaModel.getIdCaracteristica();
            caracteristicaModel.add(linkTo(methodOn(CaracteristicaController.class).findById(id)).withSelfRel());
        }
        return new ResponseEntity(caracteristicaModelList, HttpStatus.OK) ;
    }
    @GetMapping(path = "/{id}")
    @Operation( summary = " Retorna uma unica caracteristica USER",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK. Retorna uma unica caracteristica"
                    )
            })
    public ResponseEntity findById(@PathVariable Long id) throws ResourceNotFoundException {
        CaracteristicaModel caracteristicaModel = service.findById(id);
        caracteristicaModel.add(linkTo(methodOn(CaracteristicaController.class).findAll()).withRel("lista de categorias"));
        return new ResponseEntity<>(caracteristicaModel,HttpStatus.OK);
    }



}
