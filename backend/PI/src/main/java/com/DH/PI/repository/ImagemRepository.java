package com.DH.PI.repository;

import com.DH.PI.model.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagemRepository extends JpaRepository<ImageModel,Long> {
    List<ImageModel> findByTituloImagemIgnoreCase(String tituloImagem);
}
