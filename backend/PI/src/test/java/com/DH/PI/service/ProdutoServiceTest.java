//package com.DH.PI.service;
//
//import com.DH.PI.dtoIN.*;
//import com.DH.PI.exception.ResourceConflict;
//import com.DH.PI.exception.ResourceNotFoundException;
//import com.DH.PI.model.*;
//import com.DH.PI.repository.ProdutoRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//
//import java.util.*;
//
//import static java.util.Optional.empty;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class ProdutoServiceTest {
//
//    @Mock
//    private ProdutoRepository repository;
//
//    @InjectMocks
//    private ProdutoService service;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    @DisplayName("Salvar produto válido")
//    void testSaveProductValid() throws ResourceConflict {
//
//        ProdutoDTO produtoDTO = new ProdutoDTO();
//        produtoDTO.setNomeProduto("Produto Teste");
//        produtoDTO.setDescricaoProduto("Descrição do produto teste");
//        produtoDTO.setCaracteristicas(List.of(new CaracteristicaDTOID(1L)));
//        produtoDTO.setImagem(List.of(new ImageDTOID(1L)));
//        produtoDTO.setCategoria(new CategoriaDTOID(1L));
//        produtoDTO.setCidade(new CidadeDTOID(1L));
//
//        ProdutoModel produtoModel = new ProdutoModel();
//        produtoModel.setIdProduto(1L);
//        produtoModel.setNomeProduto("Produto Teste");
//        produtoModel.setDescricaoProduto("Descrição do produto teste");
//        produtoModel.setCaracteristicas(List.of(new CaracteristicaModel(1L,"Preto", ":)")));
//        produtoModel.setImagem(List.of(new ImageModel(1L,"Imagem 1", "http://urlImage")));
//        produtoModel.setCategoria(new CategoriaModel(1L,"Hatchs", "Descrição categoria", "http://urlImageCategoria"));
//        produtoModel.setCidade(new CidadeModel(1L,"Belo Horizonte", "Brasil"));
//
//        when(repository.findByNomeProdutoIgnoreCase(produtoDTO.getNomeProduto()))
//                .thenReturn(new ArrayList<>());
//
//        when(repository.save(Mockito.any(ProdutoModel.class)))
//                .thenReturn(produtoModel);
//
//        ProdutoModel result = service.save(produtoDTO);
//
//        Assertions.assertNotNull(result);
//        assertEquals(produtoModel.getIdProduto(), result.getIdProduto());
//        assertEquals(produtoModel.getNomeProduto(), result.getNomeProduto());
//        assertEquals(produtoModel.getDescricaoProduto(), result.getDescricaoProduto());
//        assertEquals(produtoModel.getCaracteristicas(), result.getCaracteristicas());
//        assertEquals(produtoModel.getCategoria(), result.getCategoria());
//        assertEquals(produtoModel.getCidade(), result.getCidade());
//        assertEquals(produtoModel.getImagem(), result.getImagem());
//
//    }
//
//    @Test
//    @DisplayName("Salvar produto já existente")
//    public void testSaveProductWithProductExistent() {
//        ProdutoDTO produtoDTO = new ProdutoDTO();
//        produtoDTO.setNomeProduto("Produto Teste");
//        produtoDTO.setDescricaoProduto("Descrição do produto teste");
//        produtoDTO.setCaracteristicas(List.of(new CaracteristicaDTOID(1L)));
//        produtoDTO.setImagem(List.of(new ImageDTOID(1L)));
//        produtoDTO.setCategoria(new CategoriaDTOID(1L));
//        produtoDTO.setCidade(new CidadeDTOID(1L));
//
//        List<ProdutoModel> produtosCadastrados = new ArrayList<>();
//
//        ProdutoModel produtoCadastrado = new ProdutoModel();
//        produtoCadastrado.setIdProduto(1L);
//        produtoCadastrado.setNomeProduto("Produto Teste");
//        produtoCadastrado.setDescricaoProduto("Descrição do produto teste");
//        produtoCadastrado.setCaracteristicas(List.of(new CaracteristicaModel(1L,"Preto", ":)")));
//        produtoCadastrado.setImagem(List.of(new ImageModel(1L,"Imagem 1", "http://urlImage")));
//        produtoCadastrado.setCategoria(new CategoriaModel(1L,"Hatchs", "Descrição categoria", "http://urlImageCategoria"));
//        produtoCadastrado.setCidade(new CidadeModel(1L,"Belo Horizonte", "Brasil"));
//
//        produtosCadastrados.add(produtoCadastrado);
//
//        when(repository.findByNomeProdutoIgnoreCase(produtoDTO.getNomeProduto()))
//                .thenReturn(produtosCadastrados);
//
//        ResourceConflict exception = assertThrows(ResourceConflict.class, () -> service.save(produtoDTO));
//
//        assertEquals("Produto já cadastrado", exception.getMessage());
//    }
//
//    @Test
//    @DisplayName("Deve retornar uma lista de produtos")
//    public void testFindAllWithTwoProducts() throws ResourceNotFoundException {
//        List<ProdutoModel> produtos = new ArrayList<>();
//        produtos.add(new ProdutoModel(1L, "produto 1", "descrição 1", null, null, null, null));
//        produtos.add(new ProdutoModel(2L, "produto 2", "descrição 2", null, null, null, null));
//        when(repository.findAll()).thenReturn(produtos);
//
//        List<ProdutoModel> produtosRetornados = service.findAll();
//        Assertions.assertEquals(2, produtosRetornados.size());
//        Assertions.assertEquals(produtos.get(0).getNomeProduto(), produtosRetornados.get(0).getNomeProduto());
//        Assertions.assertEquals(produtos.get(0).getDescricaoProduto(), produtosRetornados.get(0).getDescricaoProduto());
//        Assertions.assertEquals(produtos.get(1).getNomeProduto(), produtosRetornados.get(1).getNomeProduto());
//        Assertions.assertEquals(produtos.get(1).getDescricaoProduto(), produtosRetornados.get(1).getDescricaoProduto());
//    }
//
//    @Test
//    @DisplayName("Deve retornar uma lista vazia de produtos")
//    public void testFindAllNoProducts() {
//
//        when(repository.findAll()).thenReturn(Collections.emptyList());
//
//        Throwable exception = assertThrows(ResourceNotFoundException.class, () -> service.findAll());
//
//        assertEquals("Nenhum produto cadastrado", exception.getMessage());
//        verify(repository).findAll();
//    }
//
//    @Test
//    @DisplayName("Obter produto por ID inexistente")
//    void testFindByIdWithInvalidId() {
//
//        Long nonExistingId = 999L;
//        when(repository.findById(nonExistingId)).thenReturn(empty());
//
//        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
//                () -> service.findById(nonExistingId));
//        assertEquals("Nenhum produto encontrado", exception.getMessage());
//        verify(repository).findById(nonExistingId);
//    }
//
//    @Test
//    @DisplayName("Obter produto por ID")
//    public void testFindById() throws ResourceNotFoundException {
//        Long id = 1L;
//        ProdutoModel produto = new ProdutoModel();
//        produto.setIdProduto(id);
//        produto.setNomeProduto("Produto Teste");
//        produto.setDescricaoProduto("Descrição do produto teste");
//        produto.setCaracteristicas(List.of(new CaracteristicaModel(1L,"Preto", ":)")));
//        produto.setImagem(List.of(new ImageModel(1L,"Imagem 1", "http://urlImage")));
//        produto.setCategoria(new CategoriaModel(1L,"Hatchs", "Descrição categoria", "http://urlImageCategoria"));
//        produto.setCidade(new CidadeModel(1L,"Belo Horizonte", "Brasil"));
//
//        Mockito.when(repository.findById(id)).thenReturn(Optional.of(produto));
//
//        ProdutoModel produtoEncontrado = service.findById(id);
//
//        assertNotNull(produtoEncontrado);
//        assertEquals(id, produtoEncontrado.getIdProduto());
//        assertEquals(produto.getNomeProduto(), produtoEncontrado.getNomeProduto());
//        assertEquals(produto.getDescricaoProduto(), produtoEncontrado.getDescricaoProduto());
//        assertEquals(produto.getCaracteristicas(), produtoEncontrado.getCaracteristicas());
//        assertEquals(produto.getCategoria(), produtoEncontrado.getCategoria());
//        assertEquals(produto.getCidade(), produtoEncontrado.getCidade());
//        Mockito.verify(repository, Mockito.times(1)).findById(id);
//    }
//
//    @Test
//    @DisplayName("Obter produto por filtro")
//    void testFindByCidadeORCategoria() throws ResourceNotFoundException {
//
//        String cidade = "Belo Horizonte";
//        String categoria = "Hatchs";
//
//
//        ProdutoModel produto1 = new ProdutoModel();
//        produto1.setIdProduto(1L);
//        produto1.setNomeProduto("Produto Teste 1");
//        produto1.setDescricaoProduto("Descrição do produto teste");
//        produto1.setCaracteristicas(List.of(new CaracteristicaModel(1L,"Preto", ":)")));
//        produto1.setImagem(List.of(new ImageModel(1L,"Imagem 1", "http://urlImage")));
//        produto1.setCategoria(new CategoriaModel(1L,"Hatchs", "Descrição categoria", "http://urlImageCategoria"));
//        produto1.setCidade(new CidadeModel(1L,"Belo Horizonte", "Brasil"));
//
//        ProdutoModel produto2 = new ProdutoModel();
//        produto2.setIdProduto(2L);
//        produto2.setNomeProduto("Produto Teste 2");
//        produto2.setDescricaoProduto("Descrição do produto teste 2");
//        produto2.setCaracteristicas(List.of(new CaracteristicaModel(2L,"Preto", ":)")));
//        produto2.setImagem(List.of(new ImageModel(2L,"Imagem 2", "http://urlImage")));
//        produto2.setCategoria(new CategoriaModel(1L,"Hatchs", "Descrição categoria", "http://urlImageCategoria"));
//        produto2.setCidade(new CidadeModel(1L,"Belo Horizonte", "Brasil"));
//
//        List<ProdutoModel> produtos = new ArrayList<>();
//        produtos.add(produto1);
//        produtos.add(produto2);
//
//        when(repository.findByCidadeNomeCidadeIgnoreCaseOrCategoriaCategoriaIgnoreCase(cidade, categoria))
//                .thenReturn(produtos);
//
//        List<ProdutoModel> produtosEncontrados = service.findByCidadeORCategoria(cidade, categoria);
//
//        assertEquals(2, produtosEncontrados.size());
//        verify(repository, times(1)).findByCidadeNomeCidadeIgnoreCaseOrCategoriaCategoriaIgnoreCase(cidade, categoria);
//    }
//
//    @Test
//    @DisplayName("Obter produto por filtro com parâmetros inexistentes")
//    void testFindByCidadeORCategoriaNotFound() throws ResourceNotFoundException {
//
//        String cidade = "São Paulo";
//        String categoria = "Hatchs";
//        when(repository.findByCidadeNomeCidadeIgnoreCaseOrCategoriaCategoriaIgnoreCase(cidade, categoria))
//                .thenReturn(Collections.emptyList());
//
//        assertThrows(ResourceNotFoundException.class, () -> service.findByCidadeORCategoria(cidade, categoria));
//    }
//
//    @Test
//    @DisplayName("Editar produto ")
//    void testChangeProductValid() throws ResourceConflict, ResourceNotFoundException {
//
//        ProdutoModel produto = new ProdutoModel();
//        produto.setIdProduto(1L);
//        produto.setNomeProduto("Produto 1");
//
//        when(repository.findById(produto.getIdProduto())).thenReturn(Optional.of(produto));
//        when(repository.save(produto)).thenReturn(produto);
//
//        ProdutoModel produtoEditado = service.change(produto);
//
//        // then
//        verify(repository, times(1)).findById(produto.getIdProduto());
//        verify(repository, times(1)).findByNomeProdutoIgnoreCase(produto.getNomeProduto());
//        verify(repository, times(1)).save(produto);
//
//        Assertions.assertEquals(produto.getIdProduto(), produtoEditado.getIdProduto());
//        Assertions.assertEquals(produto.getNomeProduto(), produtoEditado.getNomeProduto());
//    }
//
//    @Test
//    @DisplayName("Editar produto para um ja existente ")
//    void testChangeProductExistent() {
//        ProdutoModel produtoExistente = new ProdutoModel();
//        produtoExistente.setIdProduto(1L);
//        produtoExistente.setNomeProduto("Produto 1");
//
//        ProdutoModel produtoAlterado = new ProdutoModel();
//        produtoAlterado.setIdProduto(1L);
//        produtoAlterado.setNomeProduto("Produto Alterado");
//
//        ProdutoDTO produtoDTO = new ProdutoDTO();
//        produtoDTO.setNomeProduto("Produto Alterado");
//
//        ObjectMapper mapper = new ObjectMapper();
//        when(repository.findById(1L)).thenReturn(Optional.of(produtoExistente));
//        when(repository.findByNomeProdutoIgnoreCase("Produto Alterado")).thenReturn(List.of(produtoAlterado));
//        when(repository.save(any(ProdutoModel.class))).thenReturn(produtoAlterado);
//
//        ResourceConflict thrown = Assertions.assertThrows(ResourceConflict.class, () -> {
//            service.change(mapper.convertValue(produtoDTO, ProdutoModel.class));
//        });
//
//        Assertions.assertEquals("Produto já cadastrado", thrown.getMessage());
//    }
//
//    @Test
//    @DisplayName("Editar produto para uma ja existente ")
//    void testChangeExistingProduct() {
////
////
////        ProdutoModel produto = new ProdutoModel();
////        produto.setIdProduto(1L);
////        produto.setNomeProduto("Produto Teste");
////        produto.setDescricaoProduto("Descrição Teste");
////        produto.setImagem(new ImageModel(1L, "Categoria teste 1", "Descrição teste","http://testeCategoria.com"));
////
////        produto.setCategoria(new CategoriaModel("Categoria Teste"));
////        produto.setCidade(new CidadeModel("Cidade Teste"));
////
////        when(repository.findById(produto.getIdProduto())).thenReturn(Optional.of(produto));
////        when(repository.findByNomeProdutoIgnoreCase(produto.getNomeProduto())).thenReturn(Arrays.asList(produto));
////
////        ProdutoModel produtoAtualizado = new ProdutoModel();
////        produtoAtualizado.setIdProduto(1L);
////        produtoAtualizado.setNomeProduto("Produto Atualizado");
////        produtoAtualizado.setDescricaoProduto("Descrição Atualizada");
////        produtoAtualizado.setImagem();
////        produtoAtualizado.setCategoria(new CategoriaModel(1L, "Categoria Atualizada", "https://imagemTeste"));
////        produtoAtualizado.setCidade(new CidadeModel("Cidade Atualizada"));
////
////        try {
////            service.change(produtoAtualizado);
////            fail("Deveria ter lançado uma exceção ResourceConflict");
////        } catch (ResourceConflict e) {
////            assertEquals("Produto já cadastrado", e.getMessage());
////        } catch (Exception e) {
////            fail("Deveria ter lançado uma exceção ResourceConflict");
////        }
//
//    }
//
//    @Test
//    @DisplayName("Editar produto com id inexistente")
//    void testChangeProductNonExistent() {
//
//        ProdutoModel produto = new ProdutoModel();
//        produto.setIdProduto(1L);
//
//        when(repository.findById(produto.getIdProduto())).thenReturn(empty());
//
//        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.change(produto),
//                "Produto não encontrado");
//        verify(repository, times(1)).findById(produto.getIdProduto());
//        verifyNoMoreInteractions(repository);
//    }
//
//    @Test
//    @DisplayName("Deletar por id")
//     void deleteProdutoDeveExecutarComSucesso() throws ResourceNotFoundException {
//
//        Long idProduto = 1L;
//        ProdutoModel produto = new ProdutoModel();
//        produto.setIdProduto(idProduto);
//        when(repository.findById(idProduto)).thenReturn(Optional.of(produto));
//
//        service.delete(idProduto);
//
//        verify(repository, times(1)).findById(idProduto);
//        verify(repository, times(1)).deleteById(idProduto);
//    }
//
//    @Test
//    public void deleteProdutoDeveLancarResourceNotFoundException() {
//
//        Long idProduto = 1L;
//        when(repository.findById(idProduto)).thenReturn(Optional.empty());
//
//        assertThrows(ResourceNotFoundException.class, () -> service.delete(idProduto));
//
//        verify(repository, times(1)).findById(idProduto);
//        verify(repository, never()).deleteById(idProduto);
//    }
//
//    @Test
//    @DisplayName("Filtro por data e cidade quando existe o parâmetro no repository")
//    public void testFindByCidadeAndDatas_WhenProdutosFound_ShouldReturnListProdutos() throws ResourceNotFoundException {
//
//        String cidade = "Belo Horizonte";
//        Date dataInicio = new Date();
//        Date dataFim = new Date();
//        Long id = 1L;
//        List<ProdutoModel> produtos = new ArrayList<>();
//
//        ProdutoModel produtoCadastrado = new ProdutoModel();
//        produtos.add(new ProdutoModel(1L, "Produto Teste 1", "Descrição do produto teste", List.of(new CaracteristicaModel(id,"Preto", ":)")), List.of(new ImageModel(1L,"Imagem 1", "http://urlImage")), new CategoriaModel(1L,"Categoria 1", "Descrição categoria", "http://urlImageCategoria"), new CidadeModel(1L,"Belo Horizonte", "Brasil")));
//        produtos.add(new ProdutoModel(2L, "Produto Teste 2", "Descrição do produto teste", List.of(new CaracteristicaModel(id,"Preto", ":)")), List.of(new ImageModel(1L,"Imagem 1", "http://urlImage")), new CategoriaModel(1L,"Categoria", "Descrição categoria", "http://urlImageCategoria"), new CidadeModel(1L,"Belo Horizonte", "Brasil")));
//
//
//        when(repository.findByCidadeAndDatas(anyString(), any(Date.class), any(Date.class))).thenReturn(produtos);
//
//        List<ProdutoModel> result = service.FindByCidadeAndDatas(cidade, dataInicio, dataFim);
//
//        verify(repository, times(1)).findByCidadeAndDatas(cidade, dataInicio, dataFim);
//        assertEquals(2, result.size());
//    }
//
//    @Test
//    @DisplayName("Filtro por data e cidade quando não existe o parâmetro")
//    public void testFindByCidadeAndDatasWhenNoProdutosFound() {
//
//        String cidade = "São Paulo";
//        Date dataInicio = new Date();
//        Date dataFim = new Date();
//        when(repository.findByCidadeAndDatas(anyString(), any(Date.class), any(Date.class))).thenReturn(new ArrayList<>());
//
//        assertThrows(ResourceNotFoundException.class, () -> {
//            service.FindByCidadeAndDatas(cidade, dataInicio, dataFim);
//        });
//    }
//
//
//}
//
//
