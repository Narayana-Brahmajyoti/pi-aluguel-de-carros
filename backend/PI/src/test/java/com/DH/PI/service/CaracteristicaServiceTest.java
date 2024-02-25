//package com.DH.PI.service;
//
//import com.DH.PI.dtoIN.CaracteristicaDTO;
//import com.DH.PI.exception.ResourceConflict;
//import com.DH.PI.exception.ResourceNotFoundException;
//import com.DH.PI.model.CaracteristicaModel;
//import com.DH.PI.repository.CaracteristicaRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class CaracteristicaServiceTest {
//
//    @InjectMocks
//    private CaracteristicaService caracteristicaService;
//
//    @Mock
//    private CaracteristicaRepository repository;
//
//    private static final Long ID = 1L;
//    private static final String CARACTERISTICA_NOME = "Caracteristica teste";
//    private static final String CARACTERISTICA_ICONE = ":)";
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    @DisplayName("Salvar caracteristica válida")
//    void saveNewCaracteristica() throws ResourceConflict {
//        CaracteristicaDTO newCaracteristica = new CaracteristicaDTO();
//        newCaracteristica.setNomeCaracteristica(CARACTERISTICA_ICONE);
//
//        CaracteristicaModel savedCaracteristica = new CaracteristicaModel();
//        savedCaracteristica.setNomeCaracteristica(CARACTERISTICA_ICONE);
//
//        when(repository.findByNomeCaracteristicaIgnoreCase(CARACTERISTICA_ICONE)).thenReturn(Collections.emptyList());
//        when(repository.save(any(CaracteristicaModel.class))).thenReturn(savedCaracteristica);
//
//        CaracteristicaModel returnedCaracteristica = caracteristicaService.save(newCaracteristica);
//
//        assertEquals(CARACTERISTICA_ICONE, returnedCaracteristica.getNomeCaracteristica());
//        verify(repository, times(1)).findByNomeCaracteristicaIgnoreCase(CARACTERISTICA_ICONE);
//        verify(repository, times(1)).save(any(CaracteristicaModel.class));
//    }
//
//    @Test
//    @DisplayName("Salvar caracteristica já cadastrada")
//    void saveExistingCaracteristica() {
//        CaracteristicaDTO existingCaracteristica = new CaracteristicaDTO();
//        existingCaracteristica.setNomeCaracteristica(CARACTERISTICA_NOME);
//
//        CaracteristicaModel existingCaracteristicaModel = new CaracteristicaModel();
//
//        existingCaracteristicaModel.setNomeCaracteristica(CARACTERISTICA_NOME);
//
//        when(repository.findByNomeCaracteristicaIgnoreCase(CARACTERISTICA_NOME)).thenReturn(List.of(existingCaracteristicaModel));
//
//        assertThrows(ResourceConflict.class, () -> caracteristicaService.save(existingCaracteristica));
//
//        verify(repository, times(1)).findByNomeCaracteristicaIgnoreCase(CARACTERISTICA_NOME);
//        verify(repository, never()).save(any(CaracteristicaModel.class));
//    }
//
//    @Test
//    @DisplayName("Buscar todas as características cadastradas")
//    void findAllCaracteristicas() throws ResourceNotFoundException {
//
//        CaracteristicaModel c1 = new CaracteristicaModel();
//        c1.setIdCaracteristica(1L);
//        c1.setNomeCaracteristica("Característica 1");
//
//        CaracteristicaModel c2 = new CaracteristicaModel();
//        c2.setIdCaracteristica(2L);
//        c2.setNomeCaracteristica("Característica 2");
//
//        List<CaracteristicaModel> caracteristicas = List.of(c1, c2);
//
//        when(repository.findAll()).thenReturn(caracteristicas);
//
//        List<CaracteristicaModel> result = caracteristicaService.findAll();
//
//        assertEquals(caracteristicas.size(), result.size());
//        assertEquals(caracteristicas.get(0).getIdCaracteristica(), result.get(0).getIdCaracteristica());
//        assertEquals(caracteristicas.get(0).getNomeCaracteristica(), result.get(0).getNomeCaracteristica());
//        assertEquals(caracteristicas.get(1).getIdCaracteristica(), result.get(1).getIdCaracteristica());
//        assertEquals(caracteristicas.get(1).getNomeCaracteristica(), result.get(1).getNomeCaracteristica());
//        verify(repository, times(1)).findAll();
//    }
//
//    @Test
//    @DisplayName("Buscar todas as características quando não há nenhuma cadastrada")
//    void findAllCaracteristicasWhenNoneExist() throws ResourceNotFoundException {
//        when(repository.findAll()).thenReturn(Collections.emptyList());
//
//        Exception exception = assertThrows(ResourceNotFoundException.class, () -> caracteristicaService.findAll());
//
//        assertEquals("Nenhuma caracteristica cadastrada", exception.getMessage());
//        verify(repository, times(1)).findAll();
//    }
//
//    @Test
//    @DisplayName("Obter caracteristica por ID quando não existe")
//    void findCaracteristicaByIdNotFound() {
//        Long id = 1L;
//
//        when(repository.findById(id)).thenReturn(Optional.empty());
//
//        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
//            caracteristicaService.findById(id);
//        });
//
//        assertEquals("Nenhum produto encontrado", exception.getMessage());
//        verify(repository, times(1)).findById(id);
//    }
//
//    @Test
//    @DisplayName("Obter caracteristica por ID quando existe")
//    void findCaracteristicaByIdSuccess() throws ResourceNotFoundException {
//        Long id = 1L;
//
//        CaracteristicaModel caracteristicaModel = new CaracteristicaModel();
//        caracteristicaModel.setIdCaracteristica(id);
//        caracteristicaModel.setNomeCaracteristica(CARACTERISTICA_NOME);
//
//        when(repository.findById(id)).thenReturn(Optional.of(caracteristicaModel));
//
//        CaracteristicaModel returnedCaracteristica = caracteristicaService.findById(id);
//
//        assertEquals(caracteristicaModel.getIdCaracteristica(), returnedCaracteristica.getIdCaracteristica());
//        assertEquals(caracteristicaModel.getNomeCaracteristica(), returnedCaracteristica.getNomeCaracteristica());
//        verify(repository, times(1)).findById(id);
//    }
//
//}