package com.DH.PI.config.security;

import com.DH.PI.model.UsuarioModel;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.jwt.secret}")
    private String secret;
    public String gerarToken(UsuarioModel usuario){
////
    return JWT.create()
            .withIssuer("API reserva Carro PI turma 5 equipe 2")
            .withSubject(usuario.getEmailUsuario())
            .withClaim("id",usuario.getId())
            .withExpiresAt(LocalDateTime.now()
                    .plusMinutes(30)
                    .toInstant(ZoneOffset.of("-03:00"))
            ).sign(Algorithm.HMAC256(secret));
    }

    public String getSubject(String token) {
        return JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("API reserva Carro PI turma 5 equipe 2")
                .build().verify(token).getSubject();
    }
}
