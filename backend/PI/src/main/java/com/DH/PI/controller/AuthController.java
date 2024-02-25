package com.DH.PI.controller;

import com.DH.PI.config.security.TokenService;
import com.DH.PI.dtoIN.TokenDTO;
import com.DH.PI.dtoIN.UsuarioDTO;
import com.DH.PI.dtoOut.TokenDTOOUT;
import com.DH.PI.dtoOut.UsuarioDTOOUT;
import com.DH.PI.model.UsuarioModel;
import com.DH.PI.service.CategoriaService;
import com.DH.PI.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autorização")
@CrossOrigin(origins ="*" )
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    TokenService tokenService;
    private static final Logger logger = LogManager.getLogger(CategoriaService.class);

    @PostMapping
    @Operation( summary = "gera um JWT")
    public ResponseEntity autenticar(@RequestBody @Valid UsuarioDTO usuarioDTO){

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(usuarioDTO.getEmailUsuario(),
                            usuarioDTO.getSenhaUsuario());
            Authentication authentication = this.authManager
                    .authenticate(usernamePasswordAuthenticationToken);
            var usuario = (UsuarioModel) authentication.getPrincipal();
            String token = tokenService.gerarToken(usuario);

            return new ResponseEntity(new TokenDTOOUT(token,"Bearer",usuario.getId(),usuario.getNomeUsuario(),usuario.getSobrenomeUsuario()), HttpStatus.OK);
    }
}
