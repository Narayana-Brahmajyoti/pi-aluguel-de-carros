package com.DH.PI.service;

import com.DH.PI.controller.CategoriaController;
import com.DH.PI.controller.CidadeController;
import com.DH.PI.controller.ProdutoController;
import com.DH.PI.exception.ResourceConflict;
import com.DH.PI.exception.ResourceNotFoundException;
import com.DH.PI.model.CategoriaModel;
import com.DH.PI.model.CidadeModel;
import com.DH.PI.model.ProdutoModel;
import com.DH.PI.repository.CidadeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class CidadeService {
    @Autowired
    CidadeRepository repository;
    private static final Logger logger = LogManager.getLogger(CidadeService.class);

    //Metodo responsavel por salvar uma cidade
    public CidadeModel save(CidadeModel cidade) throws ResourceConflict {
        List<CidadeModel> listCidade = repository.findByNomeCidadeIgnoreCase(cidade.getNomeCidade());
        if (!listCidade.isEmpty()){
            logger.error("Cidade já cadastrada");
            throw new ResourceConflict("Cidade já cadastrada");
        }
        logger.info("Salvando Cidade");

        return repository.save(cidade);
    }


    //Metodo responsavel por buscar todas as cidade salvas
    public List<CidadeModel> findAll() throws ResourceNotFoundException {
        List<CidadeModel> cidadeList = repository.findAll();
        if(cidadeList.isEmpty()){
            throw new ResourceNotFoundException("Nenhuma cidade encontrada");
        }
            logger.info("Cadastrando cidade");
            return cidadeList;
    }

    public CidadeModel findById(Long id) throws ResourceNotFoundException {
        return repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Cidade não encontrada"));
    }
    public CidadeModel change(CidadeModel cidade) throws ResourceNotFoundException, ResourceConflict {
        repository.findById(cidade.getIdCidade()).orElseThrow(()-> new ResourceNotFoundException("Cidade não encontrada"));
            List<CidadeModel> cidadeModelList = repository.findByNomeCidadeIgnoreCase(cidade.getNomeCidade());
            if (!cidadeModelList.isEmpty()){
                logger.error("cidade já cadastrada");
                throw new ResourceConflict("Cidade já cadastrada");
            }
            return repository.save(cidade);
    }
    public void delete(Long id) throws ResourceNotFoundException {
        repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Cidade não encontrada"));
        logger.info("Exclusão de cidade");
        repository.deleteById(id);
    }
}
