package com.DH.PI.repository;

import com.DH.PI.model.CaracteristicaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaracteristicaRepository extends JpaRepository<CaracteristicaModel, Long> {
    List<CaracteristicaModel> findByNomeCaracteristicaIgnoreCase(String nomeCaracteristica);
}
