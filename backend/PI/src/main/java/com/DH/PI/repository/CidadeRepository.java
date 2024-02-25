package com.DH.PI.repository;

import com.DH.PI.model.CidadeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CidadeRepository extends JpaRepository<CidadeModel, Long> {
    List<CidadeModel> findByNomeCidadeIgnoreCase(String nomeCidade);
}
