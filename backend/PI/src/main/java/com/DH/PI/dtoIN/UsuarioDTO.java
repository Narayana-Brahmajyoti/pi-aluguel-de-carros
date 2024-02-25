package com.DH.PI.dtoIN;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    @NotEmpty
    @Size(min = 6)
    private String emailUsuario;
    @NotEmpty
    @Size(min = 6)
    private String senhaUsuario;

    public UsernamePasswordAuthenticationToken converter() {
        return  new UsernamePasswordAuthenticationToken(this.emailUsuario, this.senhaUsuario);
    }
}
