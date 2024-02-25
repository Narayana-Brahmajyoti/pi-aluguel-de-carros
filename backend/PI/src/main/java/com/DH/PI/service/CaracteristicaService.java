package com.DH.PI.service;

import com.DH.PI.dtoIN.CaracteristicaDTO;
import com.DH.PI.exception.ResourceConflict;
import com.DH.PI.exception.ResourceNotFoundException;
import com.DH.PI.model.CaracteristicaModel;
import com.DH.PI.repository.CaracteristicaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaracteristicaService {
    private static final Logger logger = LogManager.getLogger(CaracteristicaModel.class);
    @Autowired
    CaracteristicaRepository repository;
    public CaracteristicaModel save(CaracteristicaDTO caracteristica) throws ResourceConflict {
        ObjectMapper mapper = new ObjectMapper();
        CaracteristicaModel caracteristicaModel = mapper.convertValue(caracteristica, CaracteristicaModel.class);
        List<CaracteristicaModel> caracteristicaModelList = repository.findByNomeCaracteristicaIgnoreCase(caracteristicaModel.getNomeCaracteristica());
        if (!caracteristicaModelList.isEmpty()){
            throw new ResourceConflict("Caracteristica já cadastrada");
        }
        return repository.save(caracteristicaModel) ;
    }
    public List<CaracteristicaModel> findAll() throws ResourceNotFoundException {
        List<CaracteristicaModel> caracteristicaModelList = repository.findAll();
        if (caracteristicaModelList.isEmpty()){
            logger.info("Nenhuma caracteristica cadastrada");
            throw new ResourceNotFoundException("Nenhuma caracteristica cadastrada");
        }
        logger.info("Retornando todos as caracteristica");
        return caracteristicaModelList;
    }
    public CaracteristicaModel findById(Long id) throws ResourceNotFoundException {
        return repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Nenhum produto encontrado"));


    }
}
