package com.example.produtovendas.builders;

import com.example.produtovendas.infra.entities.ClienteEntity;
import com.example.produtovendas.infra.entities.ProdutoEntity;
import com.example.produtovendas.infra.entities.VendaEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class Builders {

    public static List<ProdutoEntity> builderProduto(){
        List<ProdutoEntity> produtoEntityList = new ArrayList<>();
        produtoEntityList.add(new ProdutoEntity(1L, "Tenis", false, "Nike", 320));
        produtoEntityList.add(new ProdutoEntity(3L ,"Camiseta", false, "Puma", 400));
        return produtoEntityList;
    }

    public static List<Optional<ProdutoEntity>> builderProdutoOptional(){
        List<Optional<ProdutoEntity>> produtoEntityList = new ArrayList<>();
        produtoEntityList.add(Optional.of(builderProduto().get(0)));
        produtoEntityList.add(Optional.of(builderProduto().get(1)));
        return produtoEntityList;
    }
    public static List<ClienteEntity> builderCliente(){
        List<ClienteEntity> clienteEntities = new ArrayList<>();
        clienteEntities.add(new ClienteEntity(1L, "Mariana", false, "456357159-17", "email@gmail.com", "(44)99874-8356"));
        clienteEntities.add(new ClienteEntity(3L, "João", false, "789456123-55", "", "(44)98747-5623"));
        return clienteEntities;
    }
    public static List<Optional<ClienteEntity>> builderClienteOptional(){
        List<Optional<ClienteEntity>> clienteEntityList = new ArrayList<>();
        clienteEntityList.add(Optional.of(builderCliente().get(0)));
        clienteEntityList.add(Optional.of(builderCliente().get(1)));
        return clienteEntityList;
    }

    public static List<Optional<VendaEntity>> builderVendaOptional(){
        List<Optional<VendaEntity>> vendaEntities = new ArrayList<>();
        vendaEntities.add(Optional.of(builderVenda().get(0)));
        vendaEntities.add(Optional.of(builderVenda().get(1)));
        return vendaEntities;
    }

    public static List<VendaEntity> builderVenda(){
        List<VendaEntity> vendaEntities = new ArrayList<>();
        vendaEntities.add(new VendaEntity(2L, Builders.builderCliente().get(0), false, 120, 10, Collections.emptyList(), LocalDate.now()));
        vendaEntities.add(new VendaEntity(7L, Builders.builderCliente().get(1), false, 520, 0, Collections.emptyList(), LocalDate.now()));
        return vendaEntities;
    }

}