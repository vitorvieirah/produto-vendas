package com.example.produtovendas.infra.dataproviders;

import com.example.produtovendas.builders.Builders;
import com.example.produtovendas.domain.Produto;
import com.example.produtovendas.infra.entities.ProdutoEntity;
import com.example.produtovendas.infra.exceptions.BancoDeDadosException;
import com.example.produtovendas.infra.mappers.ProdutoMapper;
import com.example.produtovendas.infra.repositories.ProdutoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.example.produtovendas.validators.Validators.validaProdutoDomain;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ProdutoDataProviderTest {

    @Mock
    private ProdutoRepository repository;

    @InjectMocks
    private ProdutoDataProvider produtoDataProvider;

    @Test
    void testeMetodoSalvar(){
        Produto produto = Builders.builderProdutoDomain().get(0);

        Mockito.when(repository.save(any())).thenReturn(ProdutoMapper.paraEntity(produto));
        Produto produtoTeste = produtoDataProvider.salvar(produto);

        validaProdutoDomain(produtoTeste, 0);
    }

    @Test
    void testeMetodoConsultarPorId(){
        Optional<ProdutoEntity> produtoEntity = Builders.builderProdutoOptionalEntity().get(0);

        Mockito.when(repository.findById(any())).thenReturn(produtoEntity);

        Optional<Produto> produtoTeste = produtoDataProvider.consultarPorId(1L);

        produtoTeste.ifPresent(produto -> validaProdutoDomain(produtoTeste.get(), 0));
    }

    @Test
    void testeMetodoConsultaTodos() {
        List<ProdutoEntity> produtoEntities = Builders.builderProdutoEntity();

        Mockito.when(repository.findAll()).thenReturn(produtoEntities);
        List<Produto> produtoList = produtoDataProvider.consultaTodos();

        validaProdutoDomain(produtoList.get(0), 0);
        validaProdutoDomain(produtoList.get(1), 1);
    }

    @Test
    void testaSeMetodoSalvarProdutoEstaLancandoException(){
        Mockito.when(repository.save(any())).thenThrow(RuntimeException.class);
        BancoDeDadosException exceptionTeste = Assertions.assertThrows(BancoDeDadosException.class, () -> produtoDataProvider.salvar(Builders.builderProdutoDomain().get(0)));
        Assertions.assertEquals("Erro no cadastro do produto", exceptionTeste.getMessage());
    }

    @Test
    void testaSeMetodoConsultaPorIdEstaLancandoException(){
        Mockito.when(repository.findById(any())).thenThrow(RuntimeException.class);
        BancoDeDadosException exceptionTeste = Assertions.assertThrows(BancoDeDadosException.class, () -> produtoDataProvider.consultarPorId(3L));
        Assertions.assertEquals( "Erro na consulta por id",exceptionTeste.getMessage());
    }

    @Test
    void testaSeMetodoConsultaTodosEstaLancandoExceprion(){
        Mockito.when(repository.findAll()).thenThrow(RuntimeException.class);
        BancoDeDadosException exceptionTeste = Assertions.assertThrows(BancoDeDadosException.class, () -> produtoDataProvider.consultaTodos());
        Assertions.assertEquals("Erro na consulta por todos", exceptionTeste.getMessage());

    }
}