package com.DH.PI;

import com.DH.PI.model.FuncaoUsuarioModel;
import com.DH.PI.model.UsuarioModel;
import com.DH.PI.repository.FuncaoRepository;
import com.DH.PI.repository.UsuarioRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CreateUser implements ApplicationRunner {
    @Autowired
    UsuarioRepository repositoryUsuario;
    @Autowired
    FuncaoRepository repositoryFuncao;
    private static final Logger logger = LogManager.getLogger(CreateUser.class);
    @Override
    public void run(ApplicationArguments args) throws Exception {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        FuncaoUsuarioModel perfilUser = new FuncaoUsuarioModel();
        FuncaoUsuarioModel perfilAdmin = new FuncaoUsuarioModel();
        perfilUser.setNomeFuncao("USER");
        perfilAdmin.setNomeFuncao("ADMIN");

        List<FuncaoUsuarioModel> perfilListUser = new ArrayList<>();
        List<FuncaoUsuarioModel> perfilListAdmin = new ArrayList<>();

  List<FuncaoUsuarioModel> funcaoUsuarioModelList = repositoryFuncao.findByNomeFuncao(perfilAdmin.getNomeFuncao());
    if (funcaoUsuarioModelList.isEmpty()){
        repositoryFuncao.save(perfilAdmin);
        repositoryFuncao.save(perfilUser);
        perfilAdmin.setIdFuncao(1L);
        perfilUser.setIdFuncao(2L);
        perfilListAdmin.add(perfilAdmin);
        perfilListUser.add(perfilUser);
        UsuarioModel usuario1 = new UsuarioModel();
        UsuarioModel usuario2 = new UsuarioModel();

        usuario1.setNomeUsuario("PadraoADMIN");
        usuario1.setSobrenomeUsuario("ADMIN");
        usuario1.setEmailUsuario("padraoadmin@CTD.com.br");
        usuario1.setSenhaUsuario(encoder.encode("12345678"));
        usuario1.setPermissao(perfilListAdmin);

        usuario2.setNomeUsuario("PadraoUSER");
        usuario2.setSobrenomeUsuario("USER");
        usuario2.setEmailUsuario("padraouser@CTD.com.br");
        usuario2.setSenhaUsuario(encoder.encode("12345678"));
        usuario2.setPermissao(perfilListUser);

        repositoryUsuario.save(usuario1);
        repositoryUsuario.save(usuario2);

    }

    }
}
