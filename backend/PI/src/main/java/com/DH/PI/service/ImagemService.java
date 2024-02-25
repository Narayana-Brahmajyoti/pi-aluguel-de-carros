package com.DH.PI.service;

import com.DH.PI.exception.ResourceConflict;
import com.DH.PI.exception.ResourceNotFoundException;
import com.DH.PI.model.ImageModel;
import com.DH.PI.repository.ImagemRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImagemService {
    @Autowired
    ImagemRepository repository;
    private static final Logger logger = LogManager.getLogger(CidadeService.class);

    public ImageModel save(ImageModel imageModel) throws ResourceConflict {
        List<ImageModel> imageModelList = repository.findByTituloImagemIgnoreCase(imageModel.getTituloImagem());
        if ((!imageModelList.isEmpty())) {
            logger.error("Imagem já cadastrada");
            throw new ResourceConflict("Imagem já cadastrada");
        }
            logger.info("Salvando imagem");
            return repository.save(imageModel);
    }
    public  List<ImageModel> findAll() throws ResourceNotFoundException {
        List<ImageModel> imageModelList = repository.findAll();
        if (imageModelList.isEmpty()){
            logger.info("Retornando todas as imagens cadastradas");
            throw new ResourceNotFoundException("Nenhuma imagem cadastrada");
        }
            return imageModelList;
    }
    public ImageModel findById(Long id) throws ResourceNotFoundException {
        return repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Imagem não encontrada"));
    }
    public ImageModel change(ImageModel imageModel) throws ResourceNotFoundException, ResourceConflict {
        repository.findById(imageModel.getIdImagem()).orElseThrow(()-> new ResourceNotFoundException("Imagem não encontrada"));
        List<ImageModel> produtoModelList = repository.findByTituloImagemIgnoreCase(imageModel.getTituloImagem());
        if (!produtoModelList.isEmpty()){
            logger.error("Imagem já cadastrada");
            throw new ResourceConflict("Imagem já cadastrada");
        }
        logger.info("Salvando alteração da Imagem");
        return repository.save(imageModel);
    }
    public void delete(Long id) throws ResourceNotFoundException {
        repository.findById(id).orElseThrow(()->new ResourceNotFoundException("Imagem não encontrada"));
        logger.info("Exclusão de Imagem");
        repository.deleteById(id);

    }
}
