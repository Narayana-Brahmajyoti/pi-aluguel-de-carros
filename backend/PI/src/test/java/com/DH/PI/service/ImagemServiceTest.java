//package com.DH.PI.service;
//
//import com.DH.PI.exception.ResourceConflict;
//import com.DH.PI.exception.ResourceNotFoundException;
//import com.DH.PI.model.ImageModel;
//import com.DH.PI.repository.ImagemRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
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
//public class ImagemServiceTest {
//
//    @Mock
//    private ImagemRepository repository;
//
//    @InjectMocks
//    private ImagemService service;
//
//
//    private ImageModel imagem1;
//    private ImageModel imagem2;
//    private List<ImageModel> listaImagens;
//
//
//    @BeforeEach
//    void setUp() {
//        imagem1 = new ImageModel();
//        imagem1.setIdImagem(1L);
//        imagem1.setTituloImagem("Imagem 1");
//        imagem1.setUrlImagem("http://localhost:8080/imagens/imagem1.jpg");
//
//        imagem2 = new ImageModel();
//        imagem2.setIdImagem(2L);
//        imagem2.setTituloImagem("Imagem 2");
//        imagem2.setUrlImagem("http://localhost:8080/imagens/imagem2.jpg");
//
//        listaImagens = new ArrayList<>();
//        listaImagens.add(imagem1);
//        listaImagens.add(imagem2);
//    }
//
//    @Test
//    @DisplayName("Salvar uma imagem já existente")
//    public void testSaveImageExistent() {
//        ImageModel imagem = new ImageModel();
//        imagem.setTituloImagem("imagem1");
//
//        List<ImageModel> imagemList = new ArrayList<>();
//        imagemList.add(imagem);
//
//        when(repository.findByTituloImagemIgnoreCase("imagem1")).thenReturn(imagemList);
//
//        Exception exception = assertThrows(ResourceConflict.class, () -> {
//            service.save(imagem);
//        });
//
//        String expectedMessage = "Imagem já cadastrada";
//        String actualMessage = exception.getMessage();
//
//        assertEquals(expectedMessage, actualMessage);
//    }
//
//    @Test
//    @DisplayName("Salvar uma imagem com sucesso")
//    public void testSaveImage() throws ResourceConflict {
//        ImageModel imagem = new ImageModel();
//        imagem.setTituloImagem("imagem1");
//
//        when(repository.findByTituloImagemIgnoreCase("imagem1")).thenReturn(new ArrayList<>());
//        when(repository.save(imagem)).thenReturn(imagem);
//
//        ImageModel imagemSalva = service.save(imagem);
//
//        assertEquals(imagem, imagemSalva);
//    }
//
//    @Test
//    @DisplayName("Obter lista vazia")
//    void deveRetornarListaVazia() {
//        when(repository.findAll()).thenReturn(new ArrayList<>());
//        ResourceNotFoundException thrown = assertThrows(
//                ResourceNotFoundException.class,
//                () -> service.findAll(),
//                "Deveria ter lançado ResourceNotFoundException"
//        );
//
//        assertEquals("Nenhuma imagem cadastrada", thrown.getMessage());
//    }
//
//    @Test
//    @DisplayName("Obter lista com 2 imagens")
//    void deveRetornarListaComDuasImagens() throws ResourceNotFoundException {
//        when(repository.findAll()).thenReturn(listaImagens);
//
//        List<ImageModel> imagens = service.findAll();
//
//        assertEquals(2, imagens.size());
//        assertEquals(imagem1.getTituloImagem(), imagens.get(0).getTituloImagem());
//        assertEquals(imagem2.getTituloImagem(), imagens.get(1).getTituloImagem());
//    }
//
//
//    @Test
//    @DisplayName("Obter imagem por id")
//    void deveRetornarImagemQuandoExistir() throws ResourceNotFoundException {
//        Long id = 1L;
//        ImageModel imagem = new ImageModel();
//        imagem.setIdImagem(id);
//
//        when(repository.findById(id)).thenReturn(Optional.of(imagem));
//
//        ImageModel imagemRetornada = service.findById(id);
//
//        assertEquals(id, imagemRetornada.getIdImagem());
//        verify(repository, times(1)).findById(id);
//    }
//
//    @Test
//    @DisplayName("Obter imagem por id inexistente")
//    void deveRetornarExcecaoQuandoNaoExistirImagem() {
//        Long id = 1L;
//
//        when(repository.findById(id)).thenReturn(Optional.empty());
//
//        assertThrows(ResourceNotFoundException.class, () -> service.findById(id));
//        verify(repository, times(1)).findById(id);
//    }
//
//    @Test
//    @DisplayName("Editar imagem com id inexistente")
//    void deveRetornarExcecaoQuandoNaoExistirImagemParaAlterar() {
//        Long id = 1L;
//
//        when(repository.findById(id)).thenReturn(Optional.empty());
//
//        assertThrows(ResourceNotFoundException.class, () -> service.change(imagem1));
//        verify(repository, times(1)).findById(id);
//    }
//
//    @Test
//    @DisplayName("Editar imagem já existente")
//    void deveRetornarExcecaoQuandoImagemJaExistir() {
//        Long id = 1L;
//
//        when(repository.findById(id)).thenReturn(Optional.of(imagem1));
//        when(repository.findByTituloImagemIgnoreCase(imagem1.getTituloImagem())).thenReturn(java.util.Collections.singletonList(imagem1));
//
//        assertThrows(ResourceConflict.class, () -> service.change(imagem1));
//        verify(repository, times(1)).findById(id);
//        verify(repository, times(1)).findByTituloImagemIgnoreCase(imagem1.getTituloImagem());
//    }
//
//    @Test
//    @DisplayName("Editar imagem com sucesso")
//    void deveAlterarImagemQuandoExistir() throws ResourceNotFoundException, ResourceConflict {
//        Long id = 1L;
//        ImageModel imagemAlterada = new ImageModel();
//        imagemAlterada.setIdImagem(id);
//        imagemAlterada.setTituloImagem("Imagem 2");
//        imagemAlterada.setUrlImagem("http://localhost:8080/imagens/imagem2.jpg");
//
//        when(repository.findById(id)).thenReturn(Optional.of(imagem1));
//        when(repository.findByTituloImagemIgnoreCase(imagemAlterada.getTituloImagem())).thenReturn(java.util.Collections.emptyList());
//        when(repository.save(imagemAlterada)).thenReturn(imagemAlterada);
//
//        ImageModel imagemRetornada = service.change(imagemAlterada);
//
//        assertEquals(imagemAlterada, imagemRetornada);
//        verify(repository, times(1)).findById(id);
//        verify(repository, times(1)).findByTituloImagemIgnoreCase(imagemAlterada.getTituloImagem());
//        verify(repository, times(1)).save(imagemAlterada);
//    }
//
//    @Test
//    @DisplayName("Deletar imagem com id inexistente")
//    void deveLancarExcecaoQuandoDeletarImagemInexistente() {
//        Long id = 1L;
//
//        when(repository.findById(id)).thenReturn(Optional.empty());
//
//        assertThrows(ResourceNotFoundException.class, () -> service.delete(id));
//        verify(repository, times(1)).findById(id);
//    }
//
//    @Test
//    @DisplayName("Deletar imagem existente")
//    void deveDeletarImagemExistente() throws ResourceNotFoundException {
//        Long id = 1L;
//
//        ImageModel imagem = new ImageModel();
//        imagem.setIdImagem(id);
//
//        when(repository.findById(id)).thenReturn(Optional.of(imagem));
//
//        service.delete(id);
//
//        verify(repository, times(1)).findById(1L);
//        verify(repository, times(1)).deleteById(1L);
//    }
//
//}
