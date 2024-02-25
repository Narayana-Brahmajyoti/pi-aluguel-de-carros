package com.DH.PI.controller;

import com.DH.PI.dtoIN.UsuarioDTOADMININ;
import com.DH.PI.dtoIN.UsuarioDTOIN;
import com.DH.PI.dtoIN.UsuarioDTOINPATCH;
import com.DH.PI.dtoOut.UsuarioDTOOUT;
import com.DH.PI.exception.ResourceConflict;
import com.DH.PI.exception.ResourceNotFoundException;
import com.DH.PI.model.UsuarioModel;
import com.DH.PI.service.UsuarioService;
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
import java.sql.SQLException;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/usuario")
@Tag(name = "Usuario")
@CrossOrigin(origins ="*" )
public class UsuarioController {
    @Autowired
    UsuarioService service;
    @PostMapping
    @Operation( summary = " Salva um usuario",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "CREATED. retorna uma URL location no header"
                    )
            })
    public ResponseEntity save(@RequestBody @Valid UsuarioDTOIN usuario) throws SQLException, ResourceConflict {
       UsuarioModel usuarioModel = service.save(usuario);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(usuarioModel.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation( summary = " Retorna uma lista de usuarios ADMIN",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK. Retorna uma lista de  usuario"
                    )
            })
    public  ResponseEntity findAll() throws ResourceNotFoundException {
        List<UsuarioDTOOUT> usuarioDTOOUTList = service.findAll();
        for (UsuarioDTOOUT usuarioDTOOUT : usuarioDTOOUTList){
            long id = usuarioDTOOUT.getId();
            usuarioDTOOUT.add(linkTo(methodOn(UsuarioController.class).findById(id)).withSelfRel());
        }
        return new ResponseEntity(usuarioDTOOUTList, HttpStatus.OK);
    }


    @GetMapping(path = "/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation( summary = " Retorna um unico usuario ADMIN",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK. Retorna um unico usuario"
                    )
            })
    public ResponseEntity findById(@PathVariable Long id) throws ResourceNotFoundException {
        UsuarioDTOOUT usuarioDTOOUT = service.findById(id);
        usuarioDTOOUT.add(linkTo(methodOn(UsuarioController.class).findAll()).withRel("lista de usuario"));
        return new ResponseEntity(usuarioDTOOUT,HttpStatus.OK);
    }
    @PatchMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation( summary = " Edita um unico usuario USER",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK. Retorna o usuario editado"
                    )
            })
    public ResponseEntity change(@RequestBody @Valid UsuarioDTOINPATCH usuarioModel) throws ResourceConflict, ResourceNotFoundException {
        UsuarioDTOOUT usuarioDTOOUT = service.change(usuarioModel);
        usuarioDTOOUT.add(linkTo(methodOn(UsuarioController.class).findById(usuarioDTOOUT.getId())).withSelfRel());
        return new ResponseEntity(usuarioDTOOUT,HttpStatus.OK);
    }
    @PatchMapping(path = "/admin")
    @SecurityRequirement(name = "bearerAuth")
    @Operation( summary = " Altera o nivel de premissão e e-mail do usuario ADMIN",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK. Retorna o usuarioADMIN editado"
                    )
            })
    public ResponseEntity changeADMIN(@RequestBody @Valid UsuarioDTOADMININ usuario) throws ResourceNotFoundException {
        service.changeADMIN(usuario);
        return new ResponseEntity(usuario,HttpStatus.OK);
    }
    @DeleteMapping(path = "/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation( summary = " Exclui um usuario ADMIN",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK. Retorna uma mensagem de exclusão"
                    )
            })
    public ResponseEntity delete(@PathVariable Long id) throws ResourceNotFoundException {
        service.delete(id);
        return new ResponseEntity("Excuido usuario de id "+id, HttpStatus.OK);
    }
}
