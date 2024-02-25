package com.DH.PI.controller;

import com.DH.PI.exception.ResourceConflict;
import com.DH.PI.exception.ResourceNotFoundException;
import com.DH.PI.model.CategoriaModel;
import com.DH.PI.model.ProdutoModel;
import com.DH.PI.service.CategoriaService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/categoria")
@Tag(name = "Categoria")
@CrossOrigin(origins ="*" )

public class CategoriaController {
    @Autowired
    CategoriaService service;
// Metodo POST responsavel por adicionar uma categoria
    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation( summary = " Salva uma categoria ADMIN",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "CREATED."
                    )
            })
    public ResponseEntity save(@RequestBody @Valid CategoriaModel categoriaModel) throws  ResourceConflict {
            CategoriaModel categoria =  service.save(categoriaModel);
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(categoria.getCategoria())
//                .toUri();
//
//        return ResponseEntity.created(location).build();
        return new  ResponseEntity(categoria,HttpStatus.CREATED);

    }
    // Metodo GET responsavel por buscar todas as categorias
    @GetMapping
    @Operation( summary = " Retorna uma lista de categorias",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK. Retorna uma lista de  categorias"
                    )
            })
    public  ResponseEntity findAll() throws ResourceNotFoundException {
        List<CategoriaModel> categoriaModelList =  service.findAll();
        for (CategoriaModel categoriaModel : categoriaModelList){
            long id = categoriaModel.getId();
            categoriaModel.add(linkTo(methodOn(CategoriaController.class).findbyId(id)).withSelfRel());
        }
        return new ResponseEntity(categoriaModelList,HttpStatus.OK);
    }
    // Metodo GET responsavel por buscar uma categoria por id

    @GetMapping(path = "/{id}")
    @Operation( summary = " Retorna uma unica categoria",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK. Retorna uma unica categoria"
                    )
            })
        public  ResponseEntity findbyId(@PathVariable Long id) throws ResourceNotFoundException {
            CategoriaModel categoriaModel = service.findbyId(id);
        categoriaModel.add(linkTo(methodOn(CategoriaController.class).findAll()).withRel("lista de categorias"));
        return new ResponseEntity(categoriaModel,HttpStatus.OK);

        }
    // Metodo PATCH responsavel por alterar uma categorias
    @PatchMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation( summary = " Edita uma unica categoria ADMIN",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK. Retorna a categoria editada"
                    )
            })
    public  ResponseEntity change(@RequestBody CategoriaModel categoriaModel) throws ResourceConflict, ResourceNotFoundException {
        CategoriaModel categoriaReturn = service.change(categoriaModel);

        categoriaReturn.add(linkTo(methodOn(CategoriaController.class).findbyId(categoriaReturn.getId())).withSelfRel());
        return  new ResponseEntity(categoriaReturn,HttpStatus.OK);
    }
    // Metodo DELETE responsavel excluir uma categoria
    @DeleteMapping(path = "/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation( summary = " Exclui uma categoria ADMIN",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK. Retorna uma mensagem de exclusão"
                    )
            })
    public ResponseEntity delete(@PathVariable Long id)throws ResourceNotFoundException {
       service.delete(id);
        return new ResponseEntity("Excluido categoria de id "+id, HttpStatus.OK);
    }

}
