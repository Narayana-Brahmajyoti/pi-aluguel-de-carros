package com.DH.PI.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.sql.results.graph.Fetch;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "produto")
@Entity
public class ProdutoModel extends RepresentationModel<ProdutoModel> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "idProduto")
    private Long idProduto;

    @NotEmpty
    @NotNull
    @NotBlank(message = "Obrigatório uma descrição valida.")
    @Size(message = "Excedido número máximo de caracteres", max = 254)
    private String nomeProduto;

    @NotEmpty
    @NotNull
    @NotBlank(message = "Obrigatório uma descrição valida.")
    @Size(message = "Excedido número máximo de caracteres", max = 254)
    private String descricaoProduto;

    @ManyToMany
    @JoinTable(name = "produto_caracteristica",
                joinColumns = @JoinColumn(name = "idProduto"),
                inverseJoinColumns = @JoinColumn(name = "id_Caracteriscica")
              )
    private List<CaracteristicaModel> caracteristicas;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_produto")
    private List<ImageModel> imagem;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_categoria")
    private CategoriaModel categoria;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cidades")
    private CidadeModel cidade;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ReservaModel>  reserva;
}
