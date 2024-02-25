package com.DH.PI.controller;

import com.DH.PI.exception.ResourceConflict;
import com.DH.PI.exception.ResourceNotFoundException;
import com.DH.PI.model.CidadeModel;
import com.DH.PI.service.CidadeService;
//import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/cidade")
@Tag(name = "Cidade")
@CrossOrigin(origins ="*" )
public class CidadeController {
    @Autowired
    CidadeService Service;

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation( summary = " Salva uma cidade ADMIN",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "CREATED."
                    )
            })
    public ResponseEntity save(@RequestBody @Valid CidadeModel cidade) throws ResourceConflict {
        CidadeModel cidadeModel = Service.save(cidade);
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(cidadeModel.getIdCidade())
//                .toUri();
//
//        return ResponseEntity.created(location).build();
        return new  ResponseEntity(cidadeModel,HttpStatus.CREATED);
    }
    @GetMapping
    @Operation( summary = " Retorna uma lista de cidades",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK. Retorna uma lista de  cidades"
                    )
            })
    public  ResponseEntity findAll() throws ResourceNotFoundException {
        List<CidadeModel> cidadeModelList = Service.findAll();
        for (CidadeModel cidadeModel : cidadeModelList){
            long id = cidadeModel.getIdCidade();
            cidadeModel.add(linkTo(methodOn(CidadeController.class).findbyId(id)).withSelfRel());
        }
        return new ResponseEntity(cidadeModelList, HttpStatus.OK);
    }
    @GetMapping(path = "/{id}")
    @Operation( summary = " Retorna uma unica cidade",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK. Retorna uma unica cidade"
                    )
            })
    public ResponseEntity findbyId(@PathVariable Long id) throws ResourceNotFoundException {
        CidadeModel cidadeModel = Service.findById(id);
        cidadeModel.add(linkTo(methodOn(CidadeController.class).findAll()).withRel("lista de cidades"));
        return new ResponseEntity(cidadeModel,HttpStatus.OK);
    }
    @PatchMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation( summary = " Edita uma unica cidade ADMIN",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK. Retorna a cidade editada"
                    )
            })
    public  ResponseEntity change(@RequestBody CidadeModel cidadeModel) throws ResourceConflict, ResourceNotFoundException {
        CidadeModel cidadeReturn = Service.change(cidadeModel);
        cidadeReturn.add(linkTo(methodOn(CidadeController.class).findbyId(cidadeReturn.getIdCidade())).withSelfRel());
        return new ResponseEntity(cidadeReturn,HttpStatus.OK);
    }
    @DeleteMapping(path = "/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation( summary = " Exclui uma cidade ADMIN",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK. Retorna uma mensagem de exclusão"
                    )
            })
    public  ResponseEntity delete(@PathVariable Long id) throws ResourceNotFoundException {
        Service.delete(id);
        return new ResponseEntity("Excuido cidade de id "+id, HttpStatus.OK);
    }

}
