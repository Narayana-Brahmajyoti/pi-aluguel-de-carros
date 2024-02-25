package com.DH.PI.controller;

import com.DH.PI.exception.ResourceConflict;
import com.DH.PI.exception.ResourceNotFoundException;
import com.DH.PI.model.ImageModel;
import com.DH.PI.service.ImagemService;
//import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jdk.dynalink.linker.LinkerServices;
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
@RequestMapping("/imagem")
@Tag(name = "Imagem")
@CrossOrigin(origins ="*" )
@SecurityRequirement(name = "bearerAuth")
public class ImagemController {
    @Autowired
    ImagemService service;

    @PostMapping
    @Operation( summary = " Salva uma imagem ADMIN",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "CREATED. retorna uma URL location no header"
                    )
            })
    public ResponseEntity save(@RequestBody @Valid ImageModel imageModel) throws ResourceConflict {
        ImageModel imagemReturn = service.save(imageModel);
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(imagemReturn.getIdImagem())
//                .toUri();
//        return ResponseEntity.created(location).build();
        return  new ResponseEntity(imagemReturn,HttpStatus.CREATED);
    }

    @GetMapping
    @Operation( summary = " Retorna uma lista de imagens ADMIN",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK. Retorna uma lista de  imagens"
                    )
            })
    public ResponseEntity findAll() throws ResourceNotFoundException {
        List<ImageModel> imageModelList = service.findAll();
        for (ImageModel imageModel : imageModelList){
            long id = imageModel.getIdImagem();
            imageModel.add(linkTo(methodOn(ImagemController.class).findById(id)).withSelfRel());
        }
        return new ResponseEntity(imageModelList, HttpStatus.OK);
    }
    @GetMapping(path = "/{id}")
    @Operation( summary = " Retorna uma unica imagem ADMIN",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK. Retorna uma unica imagem"
                    )
            })
    public ResponseEntity findById(@PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity( service.findById(id),HttpStatus.OK);
    }
    @PatchMapping
    @Operation( summary = " Edita uma unica imagem ADMIN",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK. Retorna a imagem editada"
                    )
            })
    public  ResponseEntity change(@RequestBody ImageModel imageModel) throws ResourceConflict, ResourceNotFoundException {
        ImageModel imageReturn =  service.change(imageModel);
        imageReturn.add(linkTo(methodOn(ImagemController.class).findById(imageReturn.getIdImagem())).withSelfRel());
        return new ResponseEntity(imageReturn,HttpStatus.OK);
    }
    @DeleteMapping(path = "/{id}")
    @Operation( summary = " Exclui uma cidade ADMIN",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK. Retorna uma mensagem de exclusão"
                    )
            })
    public ResponseEntity delete(@PathVariable Long id) throws ResourceNotFoundException {
        service.delete(id);
        return new ResponseEntity("Excuido imagem de id "+id, HttpStatus.OK);
    }
}
