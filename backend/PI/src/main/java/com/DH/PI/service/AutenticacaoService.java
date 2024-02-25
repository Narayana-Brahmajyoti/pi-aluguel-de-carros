package com.DH.PI.service;

import com.DH.PI.model.UsuarioModel;
import com.DH.PI.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
//Metodo responsavel por autenticar o usuario - modelo
public class AutenticacaoService implements UserDetailsService {
    @Autowired
    private UsuarioRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UsuarioModel usuarioModel = repository.findByEmailUsuario(username);
            return usuarioModel;

        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("Usuario não existe");
        }

    }
}
