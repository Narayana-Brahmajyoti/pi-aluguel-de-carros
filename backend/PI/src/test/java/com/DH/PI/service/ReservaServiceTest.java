//package com.DH.PI.service;
//
//import com.DH.PI.dtoIN.ProdutoDTOID;
//import com.DH.PI.dtoIN.ReservaDTO;
//import com.DH.PI.dtoIN.UsuarioDTOID;
//import com.DH.PI.exception.ResourceConflict;
//import com.DH.PI.exception.ResourceNotFoundException;
//import com.DH.PI.model.ProdutoModel;
//import com.DH.PI.model.ReservaModel;
//import com.DH.PI.model.UsuarioModel;
//import com.DH.PI.repository.ProdutoRepository;
//import com.DH.PI.repository.ReservaRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.sql.Date;
//import java.sql.Time;
//import java.util.*;
//
//import static org.junit.Assert.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class ReservaServiceTest {
//
//    @Mock
//    private ReservaRepository reservaRepository;
//
//    @Mock
//    private ProdutoRepository produtoRepository;
//
//    @InjectMocks
//    private ReservaService reservaService;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    @DisplayName("Salvar reserva válida")
//    public void testSave() throws ResourceConflict {
//        ProdutoDTOID produtoDTOID = new ProdutoDTOID();
//        produtoDTOID.setIdProduto(1L);
//
//        UsuarioDTOID usuarioDTOID = new UsuarioDTOID();
//        usuarioDTOID.setId(1L);
//
//        ReservaDTO reservaDTO = new ReservaDTO();
//        reservaDTO.setHorarioInicioReserva(Time.valueOf("08:00:00"));
//        reservaDTO.setDataInicioReserva(Date.valueOf("2023-04-10"));
//        reservaDTO.setDataFinalReserva(Date.valueOf("2023-04-12"));
//        reservaDTO.setProduto(produtoDTOID);
//        reservaDTO.setUsuario(usuarioDTOID);
//
//
//        ProdutoModel produtoModel = new ProdutoModel();
//        produtoModel.setIdProduto(1L);
//        produtoModel.setNomeProduto("Produto Teste");
//        produtoModel.setDescricaoProduto("Descrição Teste");
//
//        UsuarioModel usuarioModel = new UsuarioModel();
//        usuarioModel.setId(1L);
//        usuarioModel.setEmailUsuario("Narayana");
//        usuarioModel.setSobrenomeUsuario("Brahmajyoti");
//        usuarioModel.setEmailUsuario("nara@email.com");
//        usuarioModel.setSenhaUsuario("123");
//
//        ReservaModel reservaModel = new ReservaModel();
//        reservaModel.setIdReserva(1L);
//        reservaModel.setHorarioInicioReserva(reservaDTO.getHorarioInicioReserva());
//        reservaModel.setDataInicioReserva(reservaDTO.getDataInicioReserva());
//        reservaModel.setDataFinalReserva(reservaDTO.getDataFinalReserva());
//        reservaModel.setProduto(produtoModel);
//        reservaModel.setUsuario(usuarioModel);
//        List<ReservaModel> reservaModelList = new ArrayList<>();
//
//        when(reservaRepository.findByConsultaReserva(
//                any(Long.class), any(Date.class), any(Date.class))).thenReturn(reservaModelList);
//        when(reservaRepository.save(reservaModel)).thenReturn(reservaModel);
//
//        ReservaModel reservaResult = reservaService.save(reservaDTO);
//
//        verify(reservaRepository, times(1)).findByConsultaReserva(
//                any(Long.class), any(Date.class), any(Date.class));
//        verify(reservaRepository, times(1)).save(reservaModel);
//        assertEquals(reservaModel.getIdReserva(), reservaResult.getIdReserva());
//    }
//
//    @Test
//    @DisplayName("Salvar reserva indisponível")
//    public void testSaveUnavailable() {
//        ProdutoDTOID produtoDTOID = new ProdutoDTOID();
//        produtoDTOID.setIdProduto(1L);
//
//        UsuarioDTOID usuarioDTOID = new UsuarioDTOID();
//        usuarioDTOID.setId(1L);
//
//        ReservaDTO reservaDTO = new ReservaDTO();
//        reservaDTO.setHorarioInicioReserva(Time.valueOf("08:00:00"));
//        reservaDTO.setDataInicioReserva(Date.valueOf("2023-04-10"));
//        reservaDTO.setDataFinalReserva(Date.valueOf("2023-04-12"));
//        reservaDTO.setProduto(produtoDTOID);
//        reservaDTO.setUsuario(usuarioDTOID);
//
//        ProdutoModel produtoModel = new ProdutoModel();
//        produtoModel.setIdProduto(1L);
//        produtoModel.setNomeProduto("Produto Teste");
//        produtoModel.setDescricaoProduto("Descrição Teste");
//
//        UsuarioModel usuarioModel = new UsuarioModel();
//        usuarioModel.setId(1L);
//        usuarioModel.setEmailUsuario("Narayana");
//        usuarioModel.setSobrenomeUsuario("Brahmajyoti");
//        usuarioModel.setEmailUsuario("nara@email.com");
//        usuarioModel.setSenhaUsuario("123");
//
//        ReservaModel reservaModel = new ReservaModel();
//        reservaModel.setIdReserva(1L);
//        reservaModel.setHorarioInicioReserva(reservaDTO.getHorarioInicioReserva());
//        reservaModel.setDataInicioReserva(reservaDTO.getDataInicioReserva());
//        reservaModel.setDataFinalReserva(reservaDTO.getDataFinalReserva());
//        reservaModel.setProduto(produtoModel);
//        reservaModel.setUsuario(usuarioModel);
//        List<ReservaModel> reservaModelList = new ArrayList<>();
//        reservaModelList.add(reservaModel);
//
//        when(reservaRepository.findByConsultaReserva(
//                any(Long.class), any(Date.class), any(Date.class))).thenReturn(reservaModelList);
//
//        Assertions.assertThrows(ResourceConflict.class, () -> {
//            reservaService.save(reservaDTO);
//        }, "Este produto está indisponível para essa data.");
//    }
//
//    @Test
//    @DisplayName("Encontrar todas as reservas")
//    public void testFindAllReservas() throws ResourceNotFoundException {
//
//        ReservaModel reserva1 = new ReservaModel();
//        ReservaModel reserva2 = new ReservaModel();
//        List<ReservaModel> reservas = Arrays.asList(reserva1, reserva2);
//
//        when(reservaRepository.findAll()).thenReturn(reservas);
//
//        List<ReservaModel> result = reservaService.findAll();
//
//        assertEquals(reservas.size(), result.size());
//        assertEquals(reserva1.getIdReserva(), result.get(0).getIdReserva());
//        assertEquals(reserva2.getIdReserva(), result.get(1).getIdReserva());
//
//        verify(reservaRepository, times(1)).findAll();
//    }
//
//    @Test
//    @DisplayName("Obter todas as reservas quando a lista está vazia")
//    public void testFindAllReservasListaVazia() {
//
//        List<ReservaModel> reservas = new ArrayList<>();
//
//        when(reservaRepository.findAll()).thenReturn(reservas);
//
//        Exception exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
//            reservaService.findAll();
//        });
//
//        String expectedMessage = "Nenhuma reserva encontrada";
//        String actualMessage = exception.getMessage();
//        Assertions.assertTrue(actualMessage.contains(expectedMessage));
//        verify(reservaRepository, times(1)).findAll();
//    }
//
//    @Test
//    @DisplayName("Encontrar reserva por ID")
//    public void testFindById() throws ResourceNotFoundException {
//        ReservaModel reservaModel = new ReservaModel();
//        reservaModel.setIdReserva(1L);
//        reservaModel.setHorarioInicioReserva(Time.valueOf("08:00:00"));
//        reservaModel.setDataInicioReserva(Date.valueOf("2023-04-10"));
//        reservaModel.setDataFinalReserva(Date.valueOf("2023-04-12"));
//
//        ProdutoModel produtoModel = new ProdutoModel();
//        produtoModel.setIdProduto(1L);
//        produtoModel.setNomeProduto("Produto Teste");
//        produtoModel.setDescricaoProduto("Descrição Teste");
//        reservaModel.setProduto(produtoModel);
//
//        UsuarioModel usuarioModel = new UsuarioModel();
//        usuarioModel.setId(1L);
//        usuarioModel.setEmailUsuario("Narayana");
//        usuarioModel.setSobrenomeUsuario("Brahmajyoti");
//        usuarioModel.setEmailUsuario("nara@email.com");
//        usuarioModel.setSenhaUsuario("123");
//        reservaModel.setUsuario(usuarioModel);
//
//        when(reservaRepository.findById(any(Long.class))).thenReturn(Optional.of(reservaModel));
//
//        ReservaModel reservaModel1 = reservaService.findById(1L);
//
//        verify(reservaRepository, times(1)).findById(1L);
//        assertEquals(reservaModel.getIdReserva(), reservaModel1.getIdReserva());
//    }
//
//    @Test
//    @DisplayName("Obter reserva por id inválido")
//    public void testFindByIdInvalidId() {
//        Long idReserva = 99L;
//        when(reservaRepository.findById(idReserva)).thenReturn(Optional.empty());
//
//        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
//            reservaService.findById(idReserva);
//        });
//
//        String expectedMessage = "Nenhuma reserva encontrada";
//        String actualMessage = exception.getMessage();
//
//        verify(reservaRepository, times(1)).findById(idReserva);
//        assertEquals(expectedMessage, actualMessage);
//    }
//
////    @Test
////    @DisplayName("Editar reserva não encontrada")
////    public void testChangeReservaNotFound() {
////
////        ReservaModel reservaModel = new ReservaModel();
////        reservaModel.setDataInicioReserva(Date.valueOf("2023-04-10"));
////        reservaModel.setDataFinalReserva(Date.valueOf("2023-04-12"));
////
////        when(reservaRepository.findById(anyLong())).thenReturn(Optional.empty());
////
////        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
////            reservaService.change(reservaModel);
////        });
////
////        String expectedMessage = "Reserva não encontrada";
////        String actualMessage = exception.getMessage();
////
////    }
//
//    @Test
//    @DisplayName("Editar reserva não encontrada")
//    public void testChangeReservaNotFound() {
//
//        ReservaModel reservaModel = new ReservaModel();
//        reservaModel.setDataInicioReserva(Date.valueOf("2023-04-10"));
//        reservaModel.setDataFinalReserva(Date.valueOf("2023-04-12"));
//
//        // Primeiro simulamos o método findById retornando um Optional vazio
//        when(reservaRepository.findById(anyLong())).thenReturn(Optional.empty());
//
//        // Depois, fazemos a chamada ao método change e validamos a exceção lançada
//        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
//            reservaService.change(reservaModel);
//        });
//
//        String expectedMessage = "Reserva não encontrada";
//        String actualMessage = exception.getMessage();
//        assertEquals(expectedMessage, actualMessage);
//    }
//
//    @Test
//    @DisplayName("Editar reserva quando produto indisponível")
//    public void testChangeUnavailableProduct() throws ResourceNotFoundException, ResourceConflict {
//        ProdutoDTOID produtoDTOID = new ProdutoDTOID();
//        produtoDTOID.setIdProduto(1L);
//
//        UsuarioDTOID usuarioDTOID = new UsuarioDTOID();
//        usuarioDTOID.setId(1L);
//
//        ReservaDTO reservaDTO = new ReservaDTO();
//        reservaDTO.setHorarioInicioReserva(Time.valueOf("08:00:00"));
//        reservaDTO.setDataInicioReserva(Date.valueOf("2023-04-10"));
//        reservaDTO.setDataFinalReserva(Date.valueOf("2023-04-12"));
//        reservaDTO.setProduto(produtoDTOID);
//        reservaDTO.setUsuario(usuarioDTOID);
//
//        ProdutoModel produtoModel = new ProdutoModel();
//        produtoModel.setIdProduto(1L);
//        produtoModel.setNomeProduto("Produto Teste");
//        produtoModel.setDescricaoProduto("Descrição Teste");
//
//        UsuarioModel usuarioModel = new UsuarioModel();
//        usuarioModel.setId(1L);
//        usuarioModel.setEmailUsuario("Narayana");
//        usuarioModel.setSobrenomeUsuario("Brahmajyoti");
//        usuarioModel.setSenhaUsuario("123");
//
//        ReservaModel reservaModel = new ReservaModel();
//        reservaModel.setIdReserva(1L);
//        reservaModel.setHorarioInicioReserva(reservaDTO.getHorarioInicioReserva());
//        reservaModel.setDataInicioReserva(reservaDTO.getDataInicioReserva());
//        reservaModel.setDataFinalReserva(reservaDTO.getDataFinalReserva());
//        reservaModel.setProduto(produtoModel);
//        reservaModel.setUsuario(usuarioModel);
//
//        when(reservaRepository.findById(reservaModel.getIdReserva())).thenReturn(Optional.of(reservaModel));
//        when(reservaRepository.findByConsultaReserva(
//                any(Long.class), any(Date.class), any(Date.class))).thenReturn(Collections.singletonList(reservaModel));
//
//        Assertions.assertThrows(ResourceConflict.class, () -> reservaService.change(reservaModel));
//
//        verify(reservaRepository, times(1)).findById(reservaModel.getIdReserva());
//        verify(reservaRepository, times(1)).findByConsultaReserva(
//                any(Long.class), any(Date.class), any(Date.class));
//    }
//
//    @Test
//    @DisplayName("Editar reserva válida")
//    public void testChangeValidReserva() throws ResourceConflict, ResourceNotFoundException {
//        ProdutoDTOID produtoDTOID = new ProdutoDTOID();
//        produtoDTOID.setIdProduto(1L);
//
//        UsuarioDTOID usuarioDTOID = new UsuarioDTOID();
//        usuarioDTOID.setId(1L);
//
//        ReservaModel reservaModel = new ReservaModel();
//        reservaModel.setIdReserva(1L);
//        reservaModel.setHorarioInicioReserva(Time.valueOf("08:00:00"));
//        reservaModel.setDataInicioReserva(Date.valueOf("2023-04-10"));
//        reservaModel.setDataFinalReserva(Date.valueOf("2023-04-12"));
//        reservaModel.setProduto(new ProdutoModel());
//        reservaModel.setUsuario(new UsuarioModel());
//
//        when(reservaRepository.findById(anyLong())).thenReturn(Optional.of(reservaModel));
//        when(reservaRepository.save(any(ReservaModel.class))).thenReturn(reservaModel);
//
//        ReservaModel updatedReserva = reservaService.change(reservaModel);
//
//        assertEquals(reservaModel.getIdReserva(), updatedReserva.getIdReserva());
//        assertEquals(reservaModel.getDataInicioReserva(), updatedReserva.getDataInicioReserva());
//        assertEquals(reservaModel.getDataFinalReserva(), updatedReserva.getDataFinalReserva());
//
//    }
//
//    @Test
//    @DisplayName("Excluir reserva existente")
//    public void testDeleteReservaExistent() throws ResourceNotFoundException {
//        ProdutoDTOID produtoDTOID = new ProdutoDTOID();
//        produtoDTOID.setIdProduto(1L);
//
//        UsuarioDTOID usuarioDTOID = new UsuarioDTOID();
//        usuarioDTOID.setId(1L);
//
//        ReservaModel reservaModel = new ReservaModel();
//        reservaModel.setIdReserva(1L);
//        reservaModel.setHorarioInicioReserva(Time.valueOf("08:00:00"));
//        reservaModel.setDataInicioReserva(Date.valueOf("2023-04-10"));
//        reservaModel.setDataFinalReserva(Date.valueOf("2023-04-12"));
//        reservaModel.setProduto(new ProdutoModel());
//        reservaModel.setUsuario(new UsuarioModel());
//
//        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reservaModel));
//
//        reservaService.delete(1L);
//
//        verify(reservaRepository, times(1)).deleteById(1L);
//    }
//
//
//    @Test
//    @DisplayName("Excluir reserva não encontrada")
//    public void testDeleteReservaNotFound() {
//        when(reservaRepository.findById(anyLong())).thenReturn(Optional.empty());
//
//        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
//            reservaService.delete(1L);
//        });
//
//        String expectedMessage = "Reserva não encontrada";
//        String actualMessage = exception.getMessage();
//
//        assertEquals(expectedMessage, actualMessage);
//    }
//
//
//}
