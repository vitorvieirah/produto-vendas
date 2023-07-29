package com.example.produtovendas.entity;

import com.example.produtovendas.domain.Venda;

import java.util.ArrayList;
import java.util.List;

public class VendaMapper {

    public static VendaEntity paraEntity(Venda venda){
        return new VendaEntity(ClienteMapper.paraEntity(venda.getCliente()), venda.getValor(), venda.getDesconto(), ProdutoMapper.paraEntitys(venda.getListaProdutos()), venda.getDataVenda());
    }

    public static Venda paraDomain(VendaEntity vendaEntity){
        return new Venda(vendaEntity.getId(), ClienteMapper.paraCliente(vendaEntity.getClienteEntity()), vendaEntity.getClienteEntity().getId(), vendaEntity.getValor(), vendaEntity.getDesconto(), ProdutoMapper.paraProdutos(vendaEntity.getListaProdutos()), vendaEntity.getDataVenda());
    }

    public static List<Venda> paraDomains(List<VendaEntity> vendaEntities){
        List<Venda> vendaList = new ArrayList<>();
        for(VendaEntity vendaEntity : vendaEntities){
            vendaList.add(paraDomain(vendaEntity));
        }
        return vendaList;
    }
}