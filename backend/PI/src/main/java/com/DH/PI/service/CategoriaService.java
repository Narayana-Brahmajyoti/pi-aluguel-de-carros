package com.DH.PI.service;

import com.DH.PI.controller.CategoriaController;
import com.DH.PI.exception.ResourceConflict;
import com.DH.PI.exception.ResourceNotFoundException;
import com.DH.PI.model.CategoriaModel;
import com.DH.PI.repository.CategoriaRepository;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.image.RasterFormatException;
import java.lang.module.ResolutionException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class CategoriaService {
    @Autowired
    CategoriaRepository repository;
    private static final Logger logger = LogManager.getLogger(CategoriaService.class);

    //Metodo responsavel por salvar um objeto de categoria
    public CategoriaModel save(CategoriaModel categoriaModel) throws ResourceConflict {
        List<CategoriaModel> listCategoriaModel = repository.findByCategoriaIgnoreCase(categoriaModel.getCategoria());
        if (!listCategoriaModel.isEmpty()){
            logger.error("Categoria já cadastrada");
            throw  new ResourceConflict("Categoria já cadastrada");
        }
        logger.info("Salvando Categoria");
        return repository.save(categoriaModel);
    }

    //Metodo responsavel por buscar todas as categorias salvas
    public List<CategoriaModel> findAll() throws ResourceNotFoundException {
        List<CategoriaModel> categoriaModelList = repository.findAll();
        if(categoriaModelList.isEmpty()){
            logger.info("Nenhum produto cadastrado");
            throw new ResourceNotFoundException("Nenhuma categoria cadastrado");
        }
            return  categoriaModelList;
    }
    //Metodo responsavel por buscar uma categoria por Id
    public  CategoriaModel findbyId(Long id){
        return repository.findById(id).orElseThrow(()-> new RasterFormatException("Categoria não encontrada"));
    }
    //Metodo responsavel por alterar os atributos de um categoria
    public CategoriaModel change(CategoriaModel categoriaModel) throws ResourceConflict {
        repository.findById(categoriaModel.getId()).orElseThrow(()-> new ResolutionException("Categoria não encontrada"));
        List<CategoriaModel> listCategoriaModel = repository.findByCategoriaIgnoreCase(categoriaModel.getCategoria());
        if (!listCategoriaModel.isEmpty()){
            logger.error("Categoria já cadastrada");
            throw new ResourceConflict("Categoria já cadastrada");
        }
        logger.info("Salvando alteração na categoria");
        return repository.save(categoriaModel);
    }
    //Metodo responsavel por escluir uma categoria atraves de um Id
    public void delete(Long id){
        repository.findById(id).orElseThrow(()-> new ResolutionException("Categoria não encontrada"));
        repository.deleteById(id);
    }
    //Metodo responsavel por verificar se existe alguma categia já salva com a mesma categoria a salvar
    public List<CategoriaModel> findCategory(String categoria){
        logger.info("Verificando se a categoria para cadastro já existe.");
        return repository.findByCategoriaIgnoreCase(categoria);
    }



}
