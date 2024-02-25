//package com.DH.PI.service;
//
//import com.DH.PI.exception.ResourceConflict;
//import com.DH.PI.exception.ResourceNotFoundException;
//import com.DH.PI.model.CategoriaModel;
//import com.DH.PI.repository.CategoriaRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.awt.image.RasterFormatException;
//import java.lang.module.ResolutionException;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class CategoriaServiceTest {
//
//    @Mock
//    private CategoriaRepository repository;
//
//    @InjectMocks
//    private CategoriaService service;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    @DisplayName("Salvar categoria válida")
//    public void testSaveCategoryValid() throws ResourceConflict {
//        CategoriaModel categoria = new CategoriaModel(1L, "Teste", "Descrição do Teste", "http://teste.com");
//
//        when(repository.findByCategoriaIgnoreCase(categoria.getCategoria())).thenReturn(new ArrayList<>());
//        when(repository.save(categoria)).thenReturn(categoria);
//
//        CategoriaModel savedCategoriaModel = service.save(categoria);
//
//        assertNotNull(savedCategoriaModel);
//        assertEquals(categoria.getId(), savedCategoriaModel.getId());
//        assertEquals(categoria.getCategoria(), savedCategoriaModel.getCategoria());
//        verify(repository, times(1)).findByCategoriaIgnoreCase(categoria.getCategoria());
//        verify(repository, times(1)).save(categoria);
//    }
//
//
//    @Test
//    @DisplayName("Salvar categoria já existente")
//    public void testSaveCategoryWithCategoryExistent() throws ResourceConflict {
//
//        CategoriaModel categoria = new CategoriaModel(1L, "Teste", "Descrição do Teste", "http://teste.com");
//
//        List<CategoriaModel> listaCategoriasExistentes = new ArrayList<>();
//        listaCategoriasExistentes.add(categoria);
//
//        when(repository.findByCategoriaIgnoreCase(categoria.getCategoria()))
//                .thenReturn(listaCategoriasExistentes);
//
//        Throwable exception = assertThrows(ResourceConflict.class, () -> service.save(categoria));
//
//        String mensagemEsperada = "Categoria já cadastrada";
//        String mensagemRecebida = exception.getMessage();
//
//        assertEquals(mensagemEsperada, mensagemRecebida);
//        verify(repository, times(1)).findByCategoriaIgnoreCase(categoria.getCategoria());
//        verify(repository, times(0)).save(categoria);
//    }
//
//    @Test
//    @DisplayName("Obter todas as categorias")
//    public void testFindAll() throws ResourceNotFoundException {
//        CategoriaModel categoria = new CategoriaModel(1L, "Teste", "Descrição do Teste", "http://teste.com");
//
//        List<CategoriaModel> categoriaModelList = new ArrayList<>();
//        categoriaModelList.add(categoria);
//        when(repository.findAll()).thenReturn(categoriaModelList);
//
//        List<CategoriaModel> foundCategoriaModelList = service.findAll();
//
//        assertNotNull(foundCategoriaModelList);
//        assertEquals(categoriaModelList.size(), foundCategoriaModelList.size());
//        verify(repository, times(1)).findAll();
//    }
//
//    @Test
//    @DisplayName("Obter todas as categorias quando a lista está vazia")
//    public void testFindAllEmpty() {
//        when(repository.findAll()).thenReturn(Collections.emptyList());
//        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
//            service.findAll();
//        });
//        assertEquals("Nenhuma categoria cadastrada", exception.getMessage());
//        verify(repository, times(1)).findAll();
//    }
//
//    @Test
//    @DisplayName("Obter categoria por ID")
//    void testFindById() {
//        CategoriaModel categoria = new CategoriaModel(1L, "Teste", "Descrição do Teste", "http://teste.com");
//        when(repository.findById(1L)).thenReturn(Optional.of(categoria));
//
//        CategoriaModel result = service.findbyId(1L);
//
//        verify(repository, times(1)).findById(1L);
//        Assertions.assertEquals(categoria, result);
//    }
//
//    @Test
//    @DisplayName("Obter categoria com id inexistente")
//    public void testFindByIdWithInvalidId() {
//        Long id = 1L;
//
//        when(repository.findById(id)).thenReturn(Optional.empty());
//
//        Throwable exception = assertThrows(RasterFormatException.class, () -> {
//            service.findbyId(id);
//        });
//
//        assertEquals("Categoria não encontrada", exception.getMessage());
//        verify(repository, times(1)).findById(id);
//    }
//
//    @Test
//    @DisplayName("Editar categoria existente")
//    public void testChangeCategorySuccess() throws ResourceConflict {
//
//        Long id = 1L;
//        CategoriaModel categoria = new CategoriaModel(id, "Nova Categoria", "Descrição teste","http://teste.com");
//
//        when(repository.findById(id)).thenReturn(Optional.of(categoria));
//        when(repository.findByCategoriaIgnoreCase(categoria.getCategoria())).thenReturn(Collections.emptyList());
//        when(repository.save(categoria)).thenReturn(categoria);
//
//        CategoriaModel result = service.change(categoria);
//
//        assertEquals(categoria, result);
//        verify(repository, times(1)).findById(id);
//        verify(repository, times(1)).findByCategoriaIgnoreCase(categoria.getCategoria());
//        verify(repository, times(1)).save(categoria);
//    }
//
//    @Test
//    @DisplayName("Editar categoria com id inexistente")
//    public void testChangeCategoryNotFound() throws ResourceNotFoundException {
//
//        Long id = 1L;
//        CategoriaModel categoria = new CategoriaModel(id, "Categoria teste", "Descrição teste","http://testeCategoria.com");
//
//        when(repository.findById(id)).thenReturn(Optional.empty());
//
//        Exception exception = assertThrows(ResolutionException.class, () -> service.change(categoria));
//
//        assertEquals("Categoria não encontrada", exception.getMessage());
//        verify(repository, times(1)).findById(id);
//        verify(repository, times(0)).save(any());
//    }
//
//    @Test
//    @DisplayName("Editar categoria com categoria cadastrada")
//    public void testChangeWithExistingCategoria() {
//        CategoriaModel categoria = new CategoriaModel(1L, "Categoria teste 1", "Descrição teste","http://testeCategoria.com");
//        CategoriaModel categoria2 = new CategoriaModel(2L, "Categoria teste 2", "Descrição teste","http://testeCategoria.com");
//
//        when(repository.findById(categoria.getId())).thenReturn(Optional.of(categoria));
//        when(repository.findByCategoriaIgnoreCase(categoria2.getCategoria())).thenReturn(Collections.singletonList(categoria));
//
//        assertThrows(ResolutionException.class, () -> service.change(categoria2));
//        verify(repository, times(1)).findById(categoria2.getId());
//        verify(repository, times(1)).findByCategoriaIgnoreCase(categoria2.getCategoria());
//        verify(repository, times(0)).save(categoria2);
//    }
//
//    @Test
//    @DisplayName("Deletar por id ")
//    public void testDeleteWithValidId() {
//
//        CategoriaModel categoria = new CategoriaModel(1L, "Nova Categoria", "Descrição teste","http://teste.com");
//
//        when(repository.findById(1L)).thenReturn(Optional.of(new CategoriaModel()));
//        service.delete(1L);
//        verify(repository, times(1)).findById(1L);
//        verify(repository, times(1)).deleteById(1L);
//    }
//
//
//
//    @Test
//    @DisplayName("Deletar por id inexistente")
//    public void testDeleteWithNonexistentId() {
//        Long id = 1L;
//        doThrow(new ResolutionException("Categoria não encontrada")).when(repository).findById(id);
//
//        Exception exception = assertThrows(ResolutionException.class, () -> service.delete(id));
//        assertEquals("Categoria não encontrada", exception.getMessage());
//
//        verify(repository, times(1)).findById(id);
//        verify(repository, never()).deleteById(id);
//    }
//
//    @Test
//    @DisplayName("Salvar categoria invalida")
//    void saveInvalidCategory() {
//
//        CategoriaModel categoria = new CategoriaModel(1L, "Teste", "Descrição do Teste", "http://teste.com/".repeat(256));
//
//        when(repository.findByCategoriaIgnoreCase(categoria.getCategoria())).thenReturn(new ArrayList<>());
//        when(repository.save(categoria)).thenReturn(categoria);
//
////        service.save(categoria);
//
//
////        assertThrows(ResourceConflict.class, () -> service.save(categoria));
//        verify(repository, times(0)).save(categoria);
//        verify(repository, never()).save(any());
//    }
//
//
//}