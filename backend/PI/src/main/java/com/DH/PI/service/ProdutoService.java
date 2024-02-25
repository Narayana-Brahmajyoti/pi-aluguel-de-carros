package com.DH.PI.service;

import com.DH.PI.dtoIN.ProdutoDTO;
import com.DH.PI.exception.ResourceConflict;
import com.DH.PI.exception.ResourceNotFoundException;
import com.DH.PI.model.ProdutoModel;
import com.DH.PI.model.ReservaModel;
import com.DH.PI.repository.ProdutoRepository;
import com.DH.PI.repository.ReservaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service
public class ProdutoService {
    @Autowired
    ProdutoRepository repository;

    @Autowired
    ReservaRepository reservaRepository;
    private static final Logger logger = LogManager.getLogger(ProdutoService.class);

    public ProdutoModel save(ProdutoDTO produtoDTO) throws ResourceConflict {
        ObjectMapper mapper = new ObjectMapper();
        ProdutoModel produtoModel = mapper.convertValue(produtoDTO,ProdutoModel.class);
        List<ProdutoModel> produtoModelList = repository.findByNomeProdutoIgnoreCase(produtoDTO.getNomeProduto());
        if ((! produtoModelList.isEmpty())) {
            logger.error("produto já cadastrado");
            throw new ResourceConflict("Produto já cadastrado");
        }
            logger.info("Salvando produto");
            return repository.save(produtoModel);
    }
    public  List<ProdutoModel> findAll() throws ResourceNotFoundException {
        List<ProdutoModel> produtoModelList = repository.findAll();
        if (produtoModelList.isEmpty()){
            logger.info("Nenhum produto cadastrado");
            throw new ResourceNotFoundException("Nenhum produto cadastrado");
        }
            logger.info("Retornando todos os produtos cadastrados");
            return produtoModelList;
    }

    public ProdutoModel findById(Long id) throws ResourceNotFoundException {
        return repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Nenhum produto encontrado"));
    }
     public List<ProdutoModel> findByCidadeORCategoria(String cidade, String categoria) throws ResourceNotFoundException {
        List<ProdutoModel> produtoModel = repository.findByCidadeNomeCidadeIgnoreCaseOrCategoriaCategoriaIgnoreCase(cidade, categoria);
//        List<ProdutoModel> produtoModelOptional = repository.findByCidadeCategoriaIgnoreCase(cidade,categoria);
        if (produtoModel.isEmpty()){
            logger.info("Nenhum produto encontrado");
            throw new ResourceNotFoundException("Nenhum produto encontrado");
        }
        return produtoModel;
     }

     public ProdutoModel change(ProdutoModel produto) throws ResourceConflict, ResourceNotFoundException {
         repository.findById(produto.getIdProduto()).orElseThrow(()-> new ResourceNotFoundException("Produto não encontrado"));
             List<ProdutoModel> produtoModelList = repository.findByNomeProdutoIgnoreCase(produto.getNomeProduto());
             if (!produtoModelList.isEmpty()){
                 logger.error("Produto já cadastrado");
                 throw new ResourceConflict("Produto já cadastrado");

             }
                 logger.info("Salvando alteração no produto");
                 return repository.save(produto);
     }
    public void delete(Long id) throws ResourceNotFoundException {
        repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Produto não encontrado"));
        repository.deleteById(id);
    }
    public List<ProdutoModel> FindByCidadeAndDatas(String cidade, Date dataInicio, Date dataFim) throws ResourceNotFoundException {
        List<ProdutoModel> listProduto = repository.findByCidadeAndDatas(cidade, dataInicio,dataFim);
        if (listProduto.isEmpty()){
            throw new ResourceNotFoundException("Nenhum produto encontrado para essa pesquisa");
        }
        return listProduto;
    }



}
