package com.DH.PI.repository;

import com.DH.PI.model.ProdutoModel;
import com.DH.PI.model.ReservaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoModel, Long> {
    List<ProdutoModel> findByNomeProdutoIgnoreCase(String nomeProduto);


    List<ProdutoModel> findByCidadeNomeCidadeIgnoreCaseOrCategoriaCategoriaIgnoreCase(String cidade, String categoria);
    @Query("SELECT p FROM ProdutoModel p JOIN ReservaModel r JOIN p.cidade c " +
            "WHERE c.nomeCidade = :cidade AND r.dataInicioReserva >= :dataInicial AND r.dataFinalReserva <= :dataFinal")
    List<ProdutoModel> findByCidadeAndDatas(@Param("cidade") String cidade,
                                            @Param("dataInicial") Date dataInicial,
                                            @Param("dataFinal") Date dataFinal);


}
