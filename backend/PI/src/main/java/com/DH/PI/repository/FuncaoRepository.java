package com.DH.PI.repository;

import com.DH.PI.model.FuncaoUsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FuncaoRepository extends JpaRepository<FuncaoUsuarioModel, Long> {

    List<FuncaoUsuarioModel> findByNomeFuncao(String nomeFuncao);
}
