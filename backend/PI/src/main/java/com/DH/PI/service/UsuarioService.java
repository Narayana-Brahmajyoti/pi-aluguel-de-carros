package com.DH.PI.service;

import com.DH.PI.controller.ProdutoController;
import com.DH.PI.controller.ReservaController;
import com.DH.PI.controller.UsuarioController;
import com.DH.PI.dtoIN.UsuarioDTO;
import com.DH.PI.dtoIN.UsuarioDTOADMININ;
import com.DH.PI.dtoIN.UsuarioDTOIN;
import com.DH.PI.dtoIN.UsuarioDTOINPATCH;
import com.DH.PI.dtoOut.UsuarioDTOADMINOUT;
import com.DH.PI.dtoOut.UsuarioDTOOUT;
import com.DH.PI.exception.ResourceConflict;
import com.DH.PI.exception.ResourceNotFoundException;
import com.DH.PI.model.FuncaoUsuarioModel;
import com.DH.PI.model.ProdutoModel;
import com.DH.PI.model.ReservaModel;
import com.DH.PI.model.UsuarioModel;
import com.DH.PI.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository repository;
    private static final Logger logger = LogManager.getLogger(UsuarioModel.class);
    public UsuarioModel save(UsuarioDTOIN usuarioDTO) throws  ResourceConflict {
        ObjectMapper mapper = new ObjectMapper();
        UsuarioModel usuario = mapper.convertValue(usuarioDTO,UsuarioModel.class);
        List<UsuarioModel> usuarioModelList = repository.findByEmailUsuarioIgnoreCase(usuario.getEmailUsuario());
        if ((!usuarioModelList.isEmpty())) {
            logger.error("Email já cadastrado");
            throw  new ResourceConflict("E-mail já cadastrado");
        }
            logger.info("Salvando Usuario");
            encryptaSenha(usuario);
            permissaoPadrao(usuario);
            return repository.save(usuario);
    }
    public List<UsuarioDTOOUT> findAll() throws ResourceNotFoundException {
        List<UsuarioModel> usuarioModelList = repository.findAll();
        if (usuarioModelList.isEmpty()){
            logger.info("Retornando todos os usuario");
            throw new ResourceNotFoundException("Nenhum usuario cadastrado");
        }
        List<UsuarioDTOOUT> usuarioDTOOUTList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        for (UsuarioModel u : usuarioModelList){
            u.setPermissao(u.getPermissao());
            usuarioDTOOUTList.add(mapper.convertValue(u, UsuarioDTOOUT.class));

        }
        return usuarioDTOOUTList;
    }
    public UsuarioDTOOUT findById(Long id) throws ResourceNotFoundException {
        ObjectMapper mapper = new ObjectMapper();
        UsuarioModel usuarioModel = repository.findById(id).orElseThrow(()->new ResourceNotFoundException("Usuario não encontrado"));
        UsuarioDTOOUT usuarioDTOOUT = mapper.convertValue(usuarioModel,UsuarioDTOOUT.class);
        usuarioDTOOUT.setPermisao(usuarioModel.getPermissao());
        return usuarioDTOOUT;
    }
    public UsuarioDTOOUT change(UsuarioDTOINPATCH usuarioDTO) throws ResourceConflict, ResourceNotFoundException {
        ObjectMapper mapper = new ObjectMapper();
        UsuarioModel usuario = mapper.convertValue(usuarioDTO,UsuarioModel.class);
        UsuarioModel usuarioModel = repository.findById(usuario.getId()).orElseThrow(()-> new ResourceNotFoundException("Usuario não encontrado"));
        List<UsuarioModel> usuarioModelList = repository.findByEmailUsuarioIgnoreCase(usuario.getEmailUsuario());
            if (!usuarioModel.getEmailUsuario().equalsIgnoreCase(usuario.getEmailUsuario())){
                logger.error("Não é possivel alterar o e-mail cadastrado");
                throw  new ResourceConflict("Não é possivel alterar o e-mail cadastrado");
            }
        logger.info("Salvando alteração do usuario");
        encryptaSenha(usuario);
        usuario.setPermissao(usuarioModel.getPermissao()) ;
        repository.save(usuario);
        List<UsuarioDTOOUT> usuarioDTOOUTList = new ArrayList<>();
        UsuarioDTOOUT usuarioDTOOUT = mapper.convertValue(usuario, UsuarioDTOOUT.class);
        return usuarioDTOOUT;
    }
    public UsuarioDTOADMINOUT changeADMIN(UsuarioDTOADMININ usuarioDTOADMININ) throws ResourceNotFoundException {
        UsuarioModel usuarioModel = repository.findById(usuarioDTOADMININ.getId())
                .orElseThrow(()-> new ResourceNotFoundException("Usuario não encontrado"));
        ObjectMapper mapper = new ObjectMapper();
        UsuarioModel usuarioMapper = mapper.convertValue(usuarioDTOADMININ,UsuarioModel.class);
        FuncaoUsuarioModel perfil = new FuncaoUsuarioModel();
        perfil.setIdFuncao(usuarioDTOADMININ.getPermissao().get(0).getIdFuncao());
        List<FuncaoUsuarioModel> perfilList = new ArrayList<>();
        perfilList.add(perfil);
        usuarioMapper.setNomeUsuario(usuarioModel.getNomeUsuario());
        usuarioMapper.setSobrenomeUsuario(usuarioModel.getSobrenomeUsuario());
        usuarioMapper.setEmailUsuario(usuarioDTOADMININ.getEmailUsuario());
        usuarioMapper.setSenhaUsuario(usuarioModel.getSenhaUsuario());
        usuarioMapper.setPermissao(perfilList);
        repository.save(usuarioMapper);
        return mapper.convertValue(usuarioMapper, UsuarioDTOADMINOUT.class);

    }
    public void delete(Long id) throws ResourceNotFoundException {
        repository.findById(id).orElseThrow(()-> new  ResourceNotFoundException("Usuario não encontrado"));
        logger.info("Exclusão de usuario");
        repository.deleteById(id);
    }
    public UsuarioModel permissaoPadrao(UsuarioModel usuarioPadrao){
        FuncaoUsuarioModel perfilUser = new FuncaoUsuarioModel();
        perfilUser.setIdFuncao(2L);
        List<FuncaoUsuarioModel> perfilListUser = new ArrayList<>();
        perfilListUser.add(perfilUser);
        usuarioPadrao.setPermissao(perfilListUser);
        return usuarioPadrao;
    }
    public  UsuarioModel encryptaSenha(UsuarioModel usuario){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String senhaCriptografada = encoder.encode(usuario.getSenhaUsuario());
        usuario.setSenhaUsuario(senhaCriptografada);
        return usuario;
    }


}