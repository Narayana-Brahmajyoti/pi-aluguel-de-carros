package com.DH.PI.repository;

import com.DH.PI.model.ProdutoModel;
import com.DH.PI.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {


    UsuarioModel findByEmailUsuario(String username);

    List<UsuarioModel> findByEmailUsuarioIgnoreCase(String emailUsuario);
}
