package com.DH.PI.repository;

import com.DH.PI.model.ProdutoModel;
import com.DH.PI.model.ReservaModel;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface ReservaRepository extends JpaRepository<ReservaModel, Long> {

    @Query("SELECT r FROM ReservaModel r JOIN ProdutoModel p  WHERE r.produto.idProduto = :idProduto AND r.dataInicioReserva BETWEEN :dataInicial AND :dataFinal")
    List<ReservaModel> findByConsultaReserva(@Param("idProduto") Long idProduto,
                                          @Param("dataInicial") Date dataInicial,
                                          @Param("dataFinal") Date dataFinal);
    @Query("SELECT r FROM ReservaModel r JOIN ProdutoModel p  WHERE r.produto.idProduto = :idProduto AND r.dataInicioReserva BETWEEN :dataInicial AND :dataFinal")
    List<ReservaModel> findByConsultaReserva1(@Param("idProduto") Long idProduto,
                                             @Param("dataInicial") Date dataInicial,
                                             @Param("dataFinal") Date dataFinal);

    @Query("SELECT p FROM ProdutoModel p JOIN ReservaModel r JOIN p.cidade c WHERE c.nomeCidade = :cidade AND r.dataInicioReserva >= :dataInicial AND r.dataFinalReserva <= :dataFinal")
    List<ProdutoModel> findByCidadeAndDatas(@Param("cidade") String cidade,
                                            @Param("dataInicial") Date dataInicial,
                                            @Param("dataFinal") Date dataFinal);

    List<ReservaModel> findByUsuarioId(Long id);

}
