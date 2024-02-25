package com.DH.PI.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "permissao")
@Entity
public class FuncaoUsuarioModel implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFuncao;

    @NotEmpty
    @NotNull
    @NotBlank(message = "Obrigatorio uma descrição valida.")
    private String nomeFuncao;

    public FuncaoUsuarioModel(String nomeFuncao) {
        this.nomeFuncao = nomeFuncao;
    }


    @Override
    public String getAuthority() {
        return this.nomeFuncao;
    }
}
