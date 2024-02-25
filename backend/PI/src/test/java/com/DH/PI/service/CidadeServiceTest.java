//package com.DH.PI.service;
//
//import com.DH.PI.exception.ResourceConflict;
//import com.DH.PI.exception.ResourceNotFoundException;
//import com.DH.PI.model.CidadeModel;
//import com.DH.PI.repository.CidadeRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class CidadeServiceTest {
//
//    @Mock
//    private CidadeRepository repository;
//
//    @InjectMocks
//    private CidadeService service;
//
//    private CidadeModel cidade;
//
//    @BeforeEach
//    public void setUp() {
//        cidade = new CidadeModel();
//        cidade.setIdCidade(1L);
//        cidade.setNomeCidade("Belo Horizonte");
//    }
//
//    @Test
//    @DisplayName("Salvar cidade em duplicidade")
//    public void testSaveShouldThrowResourceConflict() {
//        List<CidadeModel> cidadeList = new ArrayList<>();
//        cidadeList.add(cidade);
//
//        when(repository.findByNomeCidadeIgnoreCase(cidade.getNomeCidade())).thenReturn(cidadeList);
//
//        Exception exception = assertThrows(ResourceConflict.class, () -> {
//            service.save(cidade);
//        });
//
//        String expectedMessage = "Cidade já cadastrada";
//        String actualMessage = exception.getMessage();
//
//        assertEquals(expectedMessage, actualMessage);
//    }
//
//    @Test
//    @DisplayName("Salvar cidade ")
//    public void testSaveShouldSaveCity() throws ResourceConflict {
//        when(repository.findByNomeCidadeIgnoreCase(cidade.getNomeCidade())).thenReturn(new ArrayList<>());
//        when(repository.save(cidade)).thenReturn(cidade);
//
//        CidadeModel savedCidade = service.save(cidade);
//
//        assertEquals(cidade.getNomeCidade(), savedCidade.getNomeCidade());
//    }
//
//    @Test
//    @DisplayName("Obter todas as cidades quando a lista está vazia")
//    public void testFindAllShouldThrowResourceNotFoundException() {
//        when(repository.findAll()).thenReturn(new ArrayList<>());
//
//        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
//            service.findAll();
//        });
//
//        String expectedMessage = "Nenhuma cidade encontrada";
//        String actualMessage = exception.getMessage();
//
//        assertEquals(expectedMessage, actualMessage);
//    }
//
//    @Test
//    @DisplayName("Obter lista de cidade")
//    public void testFindAllShouldReturnListOfCities() throws ResourceNotFoundException {
//        List<CidadeModel> cidadeList = new ArrayList<>();
//        cidadeList.add(cidade);
//
//        when(repository.findAll()).thenReturn(cidadeList);
//
//        List<CidadeModel> foundCidadeList = service.findAll();
//
//        assertEquals(cidadeList, foundCidadeList);
//    }
//
//    @Test
//    @DisplayName("Obter cidade por Id inexistente")
//    public void testFindByIdShouldThrowResourceNotFoundException() {
//        Long cidadeId = 1L;
//        when(repository.findById(cidadeId)).thenReturn(Optional.empty());
//
//        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
//            service.findById(cidadeId);
//        });
//
//        String expectedMessage = "Cidade não encontrada";
//        String actualMessage = exception.getMessage();
//
//        assertEquals(expectedMessage, actualMessage);
//    }
//
//    @Test
//    @DisplayName("Obter cidade por Id")
//    public void testFindByIdShouldReturnCidade() throws ResourceNotFoundException {
//        Long cidadeId = 1L;
//        when(repository.findById(cidadeId)).thenReturn(Optional.of(cidade));
//
//        CidadeModel foundCidade = service.findById(cidadeId);
//
//        assertEquals(cidade, foundCidade);
//    }
//
//    @Test
//    @DisplayName("Editar cidade com id inexistente")
//    public void testChangeShouldThrowResourceNotFoundException() {
//        Long cidadeId = 1L;
//        when(repository.findById(cidadeId)).thenReturn(Optional.empty());
//
//        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
//            service.change(cidade);
//        });
//
//        String expectedMessage = "Cidade não encontrada";
//        String actualMessage = exception.getMessage();
//
//        assertEquals(expectedMessage, actualMessage);
//    }
//
//    @Test
//    @DisplayName("Editar cidade para uma já existente no repository")
//    public void testChangeShouldThrowResourceConflict() {
//        Long cidadeId = 1L;
//        when(repository.findById(cidadeId)).thenReturn(Optional.of(cidade));
//        when(repository.findByNomeCidadeIgnoreCase(cidade.getNomeCidade())).thenReturn(List.of(cidade));
//
//        Exception exception = assertThrows(ResourceConflict.class, () -> {
//            service.change(cidade);
//        });
//
//        String expectedMessage = "Cidade já cadastrada";
//        String actualMessage = exception.getMessage();
//
//        assertEquals(expectedMessage, actualMessage);
//    }
//
//    @Test
//    @DisplayName("Editar cidade")
//    public void testChangeShouldReturnCidade() throws ResourceNotFoundException, ResourceConflict {
//        Long cidadeId = 1L;
//        when(repository.findById(cidadeId)).thenReturn(Optional.of(cidade));
//        when(repository.findByNomeCidadeIgnoreCase(cidade.getNomeCidade())).thenReturn(List.of());
//
//        CidadeModel updatedCidade = service.change(cidade);
//
//        assertEquals(cidade, updatedCidade);
//        verify(repository).save(cidade);
//    }
//
//    @Test
//    @DisplayName("Deletar cidade por id")
//    public void testDeleteCidade() throws ResourceNotFoundException {
//
//        when(repository.findById(1L)).thenReturn(Optional.of(cidade));
//        doNothing().when(repository).deleteById(1L);
//
//        service.delete(1L);
//
//        Mockito.verify(repository, Mockito.times(1)).deleteById(1L);
//    }
//
//    @Test
//    @DisplayName("Deletar cidade por id inexistente")
//    public void testDeleteCidadeInexistente() {
//
//        when(repository.findById(anyLong())).thenReturn(Optional.empty());
//
//        assertThrows(ResourceNotFoundException.class, () -> service.delete(1L));
//
//        Mockito.verify(repository, Mockito.never()).deleteById(anyLong());
//    }
//}
