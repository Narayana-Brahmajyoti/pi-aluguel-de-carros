//package com.DH.PI.service;
//
//import com.DH.PI.model.UsuarioModel;
//import com.DH.PI.repository.UsuarioRepository;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class AutenticacaoServiceTest {
//
//    @Mock
//    private UsuarioRepository repository;
//
//    @InjectMocks
//    private AutenticacaoService service;
//
//    @Test
//    void testLoadUserByUsername() {
//        // Cria um usuário
//        UsuarioModel usuario = new UsuarioModel();
//        usuario.setEmailUsuario("joao@teste.com");
//        usuario.setSenhaUsuario("123456");
//
//        // Configura o mock do repository para retornar o usuário criado
//        when(repository.findByEmailUsuario(usuario.getEmailUsuario())).thenReturn(usuario);
//
//        // Executa o método loadUserByUsername com o email do usuário criado
//        UserDetails userDetails = service.loadUserByUsername(usuario.getEmailUsuario());
//
//        // Verifica se o usuário retornado pelo método é igual ao usuário criado
//        assertEquals(usuario.getEmailUsuario(), userDetails.getUsername());
//        assertEquals(usuario.getSenhaUsuario(), userDetails.getPassword());
//    }
//
//    @Test
//    @DisplayName("Usuário não encontrado")
//    void testLoadUserByUsernameUsuarioNaoEncontrado() {
//
//        when(repository.findByEmailUsuario("joao@teste.com")).thenReturn(null);
//
//        Exception exception = assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("joao@teste.com"));
//
//        String expectedMessage = "Usuario não existe";
//        String actualMessage = exception.getMessage();
//        assertTrue(actualMessage.contains(expectedMessage));
//    }
//}
