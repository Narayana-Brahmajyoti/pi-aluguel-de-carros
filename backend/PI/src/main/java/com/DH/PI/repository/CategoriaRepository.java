package com.DH.PI.repository;

import com.DH.PI.model.CategoriaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaModel, Long> {


    List<CategoriaModel> findByCategoriaIgnoreCase(String categoria);
}
