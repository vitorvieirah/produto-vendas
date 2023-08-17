package com.example.produtovendas.service;

import com.example.produtovendas.domain.Produto;
import com.example.produtovendas.infra.dataproviders.ProdutoDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoDataProvider produtoDataProvider;

    public Produto cadastroProduto(Produto produto){
        return produtoDataProvider.salvarProduto(produto);
    }

    public Produto consultarProdutoPorId(Long id){
        return produtoDataProvider.consultarProdutoPorId(id);
    }

    public List<Produto> consultaTodosProdutos() {
        return produtoDataProvider.consultaTodosProdutos();
    }

    public void deletarProduto(Long id){
        Produto produto = consultarProdutoPorId(id);
        produto.inativar();
        produtoDataProvider.salvarProduto(produto);
    }

    public Produto alterarProduto(Long id, Produto produtoDto){
       Produto produto = produtoDataProvider.consultarProdutoPorId(id);
       produto.atualizaDados(produtoDto);
       return produtoDataProvider.salvarProduto(produto);
    }


}
