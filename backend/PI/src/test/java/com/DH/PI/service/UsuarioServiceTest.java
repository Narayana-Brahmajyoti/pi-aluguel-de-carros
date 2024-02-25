//package com.DH.PI.service;
//
//import com.DH.PI.dtoIN.UsuarioDTOIN;
//import com.DH.PI.dtoIN.UsuarioDTOINPATCH;
//import com.DH.PI.dtoOut.UsuarioDTOOUT;
//import com.DH.PI.exception.ResourceConflict;
//import com.DH.PI.exception.ResourceNotFoundException;
//import com.DH.PI.model.FuncaoUsuarioModel;
//import com.DH.PI.model.UsuarioModel;
//import com.DH.PI.repository.UsuarioRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class UsuarioServiceTest {
//
//    @Mock
//    private UsuarioRepository repository;
//
//    @InjectMocks
//    private UsuarioService service;
//
//    @Test
//    @DisplayName("Salvar usuário")
//    void testSaveShouldReturnSavedUser() throws ResourceConflict {
//
//        UsuarioDTOIN usuarioDTO = new UsuarioDTOIN();
//        usuarioDTO.setNomeUsuario("Fulano da Silva");
//        usuarioDTO.setEmailUsuario("fulano.silva@test.com");
//        usuarioDTO.setSenhaUsuario("123456");
//
//        List<UsuarioModel> usuarioModelList = new ArrayList<>();
//        when(repository.findByEmailUsuarioIgnoreCase(any())).thenReturn(usuarioModelList);
//
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        String senhaCriptografada = encoder.encode(usuarioDTO.getSenhaUsuario());
//
//        UsuarioModel expectedUser = new UsuarioModel();
//        expectedUser.setNomeUsuario(usuarioDTO.getNomeUsuario());
//        expectedUser.setEmailUsuario(usuarioDTO.getEmailUsuario());
//        expectedUser.setSenhaUsuario(senhaCriptografada);
//
//        when(repository.save(any())).thenReturn(expectedUser);
//
//        UsuarioModel actualUser = service.save(usuarioDTO);
//
//        assertNotNull(actualUser);
//        assertEquals(expectedUser.getNomeUsuario(), actualUser.getNomeUsuario());
//        assertEquals(expectedUser.getEmailUsuario(), actualUser.getEmailUsuario());
//        assertEquals(expectedUser.getSenhaUsuario(), actualUser.getSenhaUsuario());
//        verify(repository, times(1)).findByEmailUsuarioIgnoreCase(any());
//        verify(repository, times(1)).save(any());
//    }
//
//    @Test
//    @DisplayName("Salvar usuário já cadastrado")
//    void testSaveShouldThrowResourceConflict() {
//
//        UsuarioDTOIN usuarioDTO = new UsuarioDTOIN();
//        usuarioDTO.setNomeUsuario("Fulano da Silva");
//        usuarioDTO.setEmailUsuario("fulano.silva@test.com");
//        usuarioDTO.setSenhaUsuario("123456");
//
//        List<UsuarioModel> usuarioModelList = new ArrayList<>();
//        usuarioModelList.add(new UsuarioModel());
//
//        when(repository.findByEmailUsuarioIgnoreCase(any())).thenReturn(usuarioModelList);
//
//        assertThrows(ResourceConflict.class, () -> service.save(usuarioDTO));
//        verify(repository, times(1)).findByEmailUsuarioIgnoreCase(any());
//        verify(repository, times(0)).save(any());
//    }
//
//    @Test
//    @DisplayName("Obter todos os usuários")
//    public void testFindAll() throws ResourceNotFoundException {
//        UsuarioModel usuario1 = new UsuarioModel();
//        usuario1.setId(1L);
//        usuario1.setNomeUsuario("Fulano");
//        usuario1.setEmailUsuario("fulano@gmail.com");
//        usuario1.setSenhaUsuario("123456");
//
//        UsuarioModel usuario2 = new UsuarioModel();
//        usuario2.setId(2L);
//        usuario2.setNomeUsuario("Ciclano");
//        usuario2.setEmailUsuario("ciclano@gmail.com");
//        usuario2.setSenhaUsuario("123456");
//
//        List<UsuarioModel> listaUsuarios = new ArrayList<>();
//        listaUsuarios.add(usuario1);
//        listaUsuarios.add(usuario2);
//
//        Mockito.when(repository.findAll()).thenReturn(listaUsuarios);
//
//        List<UsuarioDTOOUT> listaUsuariosDTO = service.findAll();
//
//        Assertions.assertEquals(2, listaUsuariosDTO.size());
//        Assertions.assertEquals(usuario1.getNomeUsuario(), listaUsuariosDTO.get(0).getNomeUsuario());
//        Assertions.assertEquals(usuario1.getEmailUsuario(), listaUsuariosDTO.get(0).getEmailUsuario());
//        Assertions.assertEquals(usuario1.getId(), listaUsuariosDTO.get(0).getId());
//        Assertions.assertEquals(usuario2.getNomeUsuario(), listaUsuariosDTO.get(1).getNomeUsuario());
//        Assertions.assertEquals(usuario2.getEmailUsuario(), listaUsuariosDTO.get(1).getEmailUsuario());
//        Assertions.assertEquals(usuario2.getId(), listaUsuariosDTO.get(1).getId());
//    }
//
//    @Test
//    @DisplayName("Obter todos os usuários quando a lista está vazia")
//    public void testFindAllThrowsResourceNotFoundException() {
//        Mockito.when(repository.findAll()).thenReturn(new ArrayList<>());
//
//        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
//            service.findAll();
//        });
//        assertEquals("Nenhum usuario cadastrado", exception.getMessage());
//        verify(repository, times(1)).findAll();
//
//    }
//
//    @Test
//    @DisplayName("Obter usuário por Id válido")
//    public void testFindByIdUsuarioExistente() throws Exception {
//        Long usuarioId = 1L;
//        UsuarioModel usuarioModel = new UsuarioModel();
//        usuarioModel.setId(usuarioId);
//        usuarioModel.setNomeUsuario("Fulano");
//        usuarioModel.setEmailUsuario("fulano@teste.com");
//
//        when(repository.findById(usuarioId)).thenReturn(Optional.of(usuarioModel));
//
//        UsuarioDTOOUT usuarioDTOOUT = service.findById(usuarioId);
//
//        assertEquals(usuarioDTOOUT.getId(), usuarioModel.getId());
//        assertEquals(usuarioDTOOUT.getNomeUsuario(), usuarioModel.getNomeUsuario());
//        assertEquals(usuarioDTOOUT.getEmailUsuario(), usuarioModel.getEmailUsuario());
//    }
//
//    @Test
//    @DisplayName("Obter usuário por Id inexistente")
//    public void testFindByIdUsuarioInexistente() throws Exception {
//        Long usuarioId = 1L;
//
//        when(repository.findById(usuarioId)).thenReturn(Optional.empty());
//
//        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
//            service.findById(1L);
//        });
//        assertEquals("Usuario não encontrado", exception.getMessage());
//        verify(repository, times(1)).findById(1L);
//
//    }
//
//    @Test
//    @DisplayName("Editar usuário válido")
//    void changeWhenUserExistsAndEmailIsUniqueShouldUpdateUser() throws Exception {
//
//        UsuarioModel usuarioModel = new UsuarioModel();
//        usuarioModel.setId(1L);
//        usuarioModel.setNomeUsuario("Usuario 1");
//        usuarioModel.setEmailUsuario("usuario1@example.com");
//        usuarioModel.setSenhaUsuario("senha123");
//        when(repository.findById(1L)).thenReturn(Optional.of(usuarioModel));
//        when(repository.findByEmailUsuarioIgnoreCase("usuario2@example.com")).thenReturn(Collections.emptyList());
//
//        UsuarioDTOINPATCH usuarioDTO = new UsuarioDTOINPATCH();
//        usuarioDTO.setId(1L);
//        usuarioDTO.setNomeUsuario("Usuario 2");
//        usuarioDTO.setEmailUsuario("usuario2@example.com");
//        usuarioDTO.setSenhaUsuario("novasenha123");
//
//        UsuarioDTOOUT result = service.change(usuarioDTO);
//
//        verify(repository).findById(1L);
//        verify(repository).findByEmailUsuarioIgnoreCase("usuario2@example.com");
//
//        assertEquals(1L, result.getId());
//        assertEquals("Usuario 2", result.getNomeUsuario());
//        assertEquals("usuario2@example.com", result.getEmailUsuario());
//    }
//
//    @Test
//    @DisplayName("Editar usuário com id inexistente")
//    public void testChangeUsuarioNotFound() throws ResourceConflict {
//
//        UsuarioDTOINPATCH usuarioDTO = new UsuarioDTOINPATCH();
//        usuarioDTO.setId(1L);
//        usuarioDTO.setEmailUsuario("email@teste.com");
//        usuarioDTO.setNomeUsuario("Nome Alterado");
//
//        when(repository.findById(usuarioDTO.getId())).thenReturn(Optional.empty());
//
//        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
//            service.change(usuarioDTO);
//        });
//
//        assertEquals("Usuario não encontrado", exception.getMessage());
//    }
//
//    @Test
//    void testChangeUsuarioEmailJaCadastrado() {
//
//        UsuarioModel usuarioCadastrado = new UsuarioModel();
//        usuarioCadastrado.setId(1L);
//        usuarioCadastrado.setNomeUsuario("João");
//        usuarioCadastrado.setSobrenomeUsuario("Da Silva");
//        usuarioCadastrado.setEmailUsuario("joao@teste.com");
//        usuarioCadastrado.setSenhaUsuario("123456");
//
//        when(repository.findById(1L)).thenReturn(Optional.of(usuarioCadastrado));
//
//        UsuarioDTOINPATCH usuarioNovo = new UsuarioDTOINPATCH();
//        usuarioNovo.setId(1L);
//        usuarioNovo.setNomeUsuario("Maria");
//        usuarioNovo.setSobrenomeUsuario("Da Silva");
//        usuarioNovo.setEmailUsuario("joao@teste.com");
//        usuarioNovo.setSenhaUsuario("654321");
//
//        Exception exception = assertThrows(ResourceConflict.class, () -> service.change(usuarioNovo));
//        String expectedMessage = "Email já cadastrado";
//        String actualMessage = exception.getMessage();
//
//        assertTrue(actualMessage.contains(expectedMessage));
//    }
//
//    @Test
//    @DisplayName("Deletar usuário com sucesso")
//    void testDeleteUsuarioExistente() throws ResourceNotFoundException {
//        UsuarioModel usuario = new UsuarioModel();
//        usuario.setId(1L);
//        usuario.setNomeUsuario("João");
//        usuario.setSobrenomeUsuario("Da Silva");
//        usuario.setEmailUsuario("joao@teste.com");
//        usuario.setSenhaUsuario("123456");
//
//        when(repository.findById(1L)).thenReturn(Optional.of(usuario));
//
//        service.delete(1L);
//
//        verify(repository, times(1)).findById(1L);
//        verify(repository, times(1)).deleteById(1L);
//    }
//
//    @Test
//    @DisplayName("Deletar usuário com id inexistente")
//    void testDeleteUsuarioInexistente() {
//        assertThrows(ResourceNotFoundException.class, () -> service.delete(1L));
//
//        verify(repository, times(1)).findById(1L);
//        verify(repository, never()).deleteById(1L);
//    }
//
//    @Test
//    @DisplayName("Validação da permissão padrão")
//    void testPermissaoPadrao() {
//
//        UsuarioModel usuario = new UsuarioModel();
//        usuario.setId(1L);
//        usuario.setNomeUsuario("João");
//        usuario.setEmailUsuario("joao@teste.com");
//        usuario.setSenhaUsuario("123456");
//
//        UsuarioModel usuarioComPermissaoPadrao = service.permissaoPadrao(usuario);
//
//        List<FuncaoUsuarioModel> permissoes = usuarioComPermissaoPadrao.getPermissao();
//        assertEquals(1, permissoes.size());
//        assertEquals(2L, permissoes.get(0).getIdFuncao());
//    }
//
//    @Test
//    @DisplayName("Validação da criptografia da senha")
//    void testEncryptaSenha() {
//
//        UsuarioModel usuario = new UsuarioModel();
//        usuario.setId(1L);
//        usuario.setSenhaUsuario("123456");
//
//        service.encryptaSenha(usuario);
//
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        assertTrue(encoder.matches("123456", usuario.getSenhaUsuario()));
//    }
//
//}
