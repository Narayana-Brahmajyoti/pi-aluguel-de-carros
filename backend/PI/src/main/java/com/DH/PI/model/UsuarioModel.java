package com.DH.PI.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Usuario")
@Entity
public class UsuarioModel extends RepresentationModel<UsuarioModel> implements UserDetails  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @NotNull
    @NotBlank(message = "Obrigatório uma descrição valida.")
    private String nomeUsuario;

    @NotEmpty
    @NotNull
    @NotBlank(message = "Obrigatório uma descrição valida.")
    private String sobrenomeUsuario;

    @NotEmpty
    @NotNull
    @NotBlank(message = "Obrigatório uma descrição valida.")
    @Email
    @Column(unique = true)
    private String emailUsuario;

    @NotEmpty
    @NotNull
    @NotBlank(message = "Obrigatório uma descrição valida.")
    @Size(min = 8, message = "É necessario uma quantidade minima de 8 caracteres para senha")
    private String senhaUsuario;

    @ManyToMany( fetch = FetchType.EAGER)
    private List<FuncaoUsuarioModel> permissao;




    //Responsavel por crir usuario local CreateUser
    public UsuarioModel (String nomeUsuario, String senhaUsuario, List<FuncaoUsuarioModel> permissao) {
        this.nomeUsuario = nomeUsuario;
        this.senhaUsuario = senhaUsuario;
        this.permissao = permissao;
    }

    public UsuarioModel(String nomeUsuario, String sobrenomeUsuario, String emailUsuario, String senhaUsuario, List<FuncaoUsuarioModel> permissao) {
        this.nomeUsuario = nomeUsuario;
        this.sobrenomeUsuario = sobrenomeUsuario;
        this.emailUsuario = emailUsuario;
        this.senhaUsuario = senhaUsuario;
        this.permissao = permissao;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.permissao;
    }


    @Override
    public String getPassword() {
        return this.senhaUsuario;
    }


    @Override
    public String getUsername() {
        return this.emailUsuario;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @Override
    public boolean isEnabled() {
        return true;
    }
}
