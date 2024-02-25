package com.DH.PI.controller;

import com.DH.PI.dtoIN.ProdutoDTO;
import com.DH.PI.exception.ResourceConflict;
import com.DH.PI.exception.ResourceNotFoundException;
import com.DH.PI.model.ProdutoModel;
import com.DH.PI.service.CategoriaService;
import com.DH.PI.service.ProdutoService;
//import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/produto")
@Tag(name = "Produto")
@CrossOrigin(origins ="*" )
public class ProdutoController {
    private static final Logger logger = LogManager.getLogger(ProdutoController.class);
    @Autowired
    ProdutoService service;
    // Metodo POST responsavel por adicionar uma categoria

    @PostMapping
    @Operation( summary = " Salva um produto ADMIN",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "CREATED. retorna uma URL location no header"
                    )
            })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity save(@RequestBody @Valid ProdutoDTO produto) throws SQLException, ResourceConflict {

        ProdutoModel produtoModel = service.save(produto);
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(produtoModel.getIdProduto())
//                .toUri();
//        return ResponseEntity.created(location).build();
        return new  ResponseEntity(produtoModel,HttpStatus.CREATED);
    }

    @GetMapping
    @Operation( summary = " Retorna uma lista de produtos",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK. Retorna uma lista de produtos"
                    )
            })
    public  ResponseEntity findAll() throws ResourceNotFoundException {
        logger.info("Gerando hatereos");
        List<ProdutoModel> produtoModelList = service.findAll();
        for (ProdutoModel produtoModel : produtoModelList){
            long id = produtoModel.getIdProduto();
            produtoModel.add(linkTo(methodOn(ProdutoController.class).findById(id)).withSelfRel());
        }
        return new ResponseEntity<>(produtoModelList, HttpStatus.OK) ;
    }
    @GetMapping(path = "/{id}")
    @Operation( summary = " Retorna uma unico produto",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK. Retorna um unico produto"
                    )
            })
    public ResponseEntity findById(@PathVariable Long id) throws ResourceNotFoundException {
        ProdutoModel produtoModel = service.findById(id);
        produtoModel.add(linkTo(methodOn(ProdutoController.class).findAll()).withRel("lista de produtos"));
            return new ResponseEntity<>(produtoModel,HttpStatus.OK);
    }

    @GetMapping(path = "/filter")
    @Operation( summary = " Retorna uma lista de produtos",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK. Retorna uma lista de produtos"
                    )
            })
    public ResponseEntity find(@RequestParam (value = "cidade", required = false) String cidade, @RequestParam(value = "categoria", required = false)  String categoria) throws ResourceNotFoundException {
        List<ProdutoModel> produtoModelList = service.findByCidadeORCategoria(cidade, categoria);
        return new ResponseEntity(produtoModelList,HttpStatus.OK);
    }
    @GetMapping(path = "/findByCidadeAndDatas")
    @Operation( summary = " Retorna uma lista de produtos",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK. Retorna uma lista de produtos"
                    )
            })
    public ResponseEntity findByCidadeAndDatas(@RequestParam( value = "cidade") String cidade,
                                               @RequestParam(value = "dataInicio") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataInicio,
                                               @RequestParam(value = "dataFim") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataFim) throws ResourceNotFoundException {
       List<ProdutoModel> produtoModelList =  service.FindByCidadeAndDatas(cidade,dataInicio,dataFim);
         return new ResponseEntity(produtoModelList, HttpStatus.OK);
    }

    @PatchMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation( summary = " Edita uma unico produto ADMIN",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK. Retorna o produto editado"
                    )
            })

    public ResponseEntity change(@RequestBody @Valid ProdutoModel produtoModel) throws ResourceConflict, ResourceNotFoundException {
        ProdutoModel produtoReturn = service.change(produtoModel);
        produtoReturn.add(linkTo(methodOn(ProdutoController.class).findById(produtoReturn.getIdProduto())).withSelfRel());
        return  new ResponseEntity(produtoReturn,HttpStatus.OK);
    }
    @DeleteMapping(path = "/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation( summary = " Exclui uma categoria ADMIN",
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
