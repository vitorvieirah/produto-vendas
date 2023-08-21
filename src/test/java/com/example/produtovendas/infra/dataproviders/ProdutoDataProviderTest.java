package com.example.produtovendas.infra.dataproviders;

import com.example.produtovendas.domain.Produto;
import com.example.produtovendas.infra.entities.ProdutoEntity;
import com.example.produtovendas.infra.exceptions.BancoDeDadosException;
import com.example.produtovendas.infra.mappers.ProdutoMapper;
import com.example.produtovendas.infra.repositories.ProdutoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class ProdutoDataProviderTest {

    @Autowired
    private ProdutoDataProvider produtoDataProvider;

    @Mock
    private ProdutoRepository repository;

    @BeforeEach
    public void beforeEach(){
        MockitoAnnotations.initMocks(this);
        this.produtoDataProvider = new ProdutoDataProvider(repository);
    }

    @Test
    void testaSeTaSalvandoProduto() {
        Long id = 1L;
        String nome = "Tenis";
        String marca = "Nike";
        boolean inativo = false;
        double valor = 350;
        Produto produto = new Produto(id, nome, inativo,marca, valor);
        Mockito.when(repository.save(any())).thenReturn(ProdutoMapper.paraEntity(produto));
        Produto produtoSalvo = produtoDataProvider.salvarProduto(produto);
        Assertions.assertEquals(nome, produtoSalvo.getNome());
        Assertions.assertEquals(id, produtoSalvo.getId());
        Assertions.assertEquals(marca, produtoSalvo.getMarca());
        Assertions.assertEquals(valor, produtoSalvo.getValor());
        Assertions.assertEquals(inativo, produtoSalvo.isInativo());
    }

    @Test
    void testaSeEstaconsultandoProdutoPorId() {
        List<ProdutoEntity> produtoEntities = new ArrayList<>();
        Produto produto1 = new Produto(1L, "Tenis", false,"Nike", 350);
        Produto produto2 = new Produto(2L, "Camiseta", false, "Vans", 400);
        produtoEntities.add(ProdutoMapper.paraEntity(produto1));
        produtoEntities.add(ProdutoMapper.paraEntity(produto2));
        Long id = produto1.getId();
        Mockito.when(repository.findById(any())).thenReturn(produtoEntities.stream().filter(ProdutoEntity -> id.equals(ProdutoEntity.getId())).findFirst());
        Produto produtoConsultado = produtoDataProvider.consultarProdutoPorId(produto1.getId());

        Assertions.assertEquals(produto1.getId(), produtoConsultado.getId());
        Assertions.assertEquals(produto1.getNome(), produtoConsultado.getNome());
        Assertions.assertEquals(produto1.isInativo(), produtoConsultado.isInativo());
        Assertions.assertEquals(produto1.getMarca(), produtoConsultado.getMarca());
        Assertions.assertEquals(produto1.getValor(), produtoConsultado.getValor());
    }

    @Test
    void testaSeEstaconsultandoTodosProdutos() {
        List<ProdutoEntity> produtoEntities = new ArrayList<>();
        Produto produto1 = new Produto(1L, "Tenis", false,"Nike", 350);
        Produto produto2 = new Produto(2L, "Camiseta", false, "Vans", 400);
        produtoEntities.add(ProdutoMapper.paraEntity(produto1));
        produtoEntities.add(ProdutoMapper.paraEntity(produto2));

        Mockito.when(repository.findAll()).thenReturn(produtoEntities);

        List<Produto> produtoList = produtoDataProvider.consultaTodosProdutos();

        Assertions.assertEquals(produto1.getId(), produtoList.get(0).getId());
        Assertions.assertEquals(produto1.getNome(), produtoList.get(0).getNome());
        Assertions.assertEquals(produto1.isInativo(), produtoList.get(0).isInativo());
        Assertions.assertEquals(produto1.getMarca(), produtoList.get(0).getMarca());
        Assertions.assertEquals(produto1.getValor(), produtoList.get(0).getValor());
        Assertions.assertEquals(produto2.getId(), produtoList.get(1).getId());
        Assertions.assertEquals(produto2.getNome(), produtoList.get(1).getNome());
        Assertions.assertEquals(produto2.isInativo(), produtoList.get(1).isInativo());
        Assertions.assertEquals(produto2.getMarca(), produtoList.get(1).getMarca());
        Assertions.assertEquals(produto2.getValor(), produtoList.get(1).getValor());
    }

    @Test
    void testaSeEstaAlterandoProduto(){
        Long id = 1L;
        String nome = "Tenis";
        String marca = "Nike";
        double valor = 350;
        Produto produtoDto = new Produto(null, nome, false, marca, valor);
        Optional<ProdutoEntity> produtoEntity = Optional.of(new ProdutoEntity(1L, "Tenis", false,"Nike", 350));

        Mockito.when(repository.findById(any())).thenReturn(produtoEntity);

    }

    @Test
    void testaSeMetodoSalvarProdutoEstaLancandoException(){
        Mockito.when(repository.save(any())).thenThrow(RuntimeException.class);
        BancoDeDadosException exceptionTeste = Assertions.assertThrows(BancoDeDadosException.class, () -> produtoDataProvider.salvarProduto(
                new Produto(5L, "Tenis", false, "Nike", 350)));
        Assertions.assertEquals("Erro no cadastro do produto", exceptionTeste.getMessage());
    }

    @Test
    void testaSeMetodoConsultaPorIdEstaLancandoException(){
        Mockito.when(repository.findById(any())).thenThrow(RuntimeException.class);
        BancoDeDadosException exceptionTeste = Assertions.assertThrows(BancoDeDadosException.class, () -> produtoDataProvider.consultarProdutoPorId(3L));
        Assertions.assertEquals( "Erro na consalta por id",exceptionTeste.getMessage());
    }

    @Test
    void testaSeMetodoConsultaTodosEstaLancandoExceprion(){
        Mockito.when(repository.findAll()).thenThrow(RuntimeException.class);
        BancoDeDadosException exceptionTeste = Assertions.assertThrows(BancoDeDadosException.class, () -> produtoDataProvider.consultaTodosProdutos());
        Assertions.assertEquals("Erro na consulta por todos", exceptionTeste.getMessage());

    }
}