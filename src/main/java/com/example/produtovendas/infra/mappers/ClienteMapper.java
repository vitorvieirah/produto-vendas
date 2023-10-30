package com.example.produtovendas.infra.mappers;

import com.example.produtovendas.domain.Cliente;
import com.example.produtovendas.dtos.ClienteDto;
import com.example.produtovendas.infra.entities.ClienteEntity;

import java.util.List;

public class ClienteMapper {

    public static Cliente paraCliente(ClienteEntity clienteEntity) {
        return Cliente.builder()
                .id(clienteEntity.getId())
                .nome(clienteEntity.getNome())
                .cpf(clienteEntity.getCpf())
                .email(clienteEntity.getEmail())
                .numeroTelefone(clienteEntity.getNumeroTelefone())
                .inativo(clienteEntity.isInativo())
                .build();
    }

    public static ClienteEntity paraEntity(Cliente cliente) {
        return ClienteEntity.builder()
                .id(cliente.getId())
                .nome(cliente.getNome())
                .cpf(cliente.getCpf())
                .email(cliente.getEmail())
                .numeroTelefone(cliente.getNumeroTelefone())
                .inativo(cliente.isInativo())
                .build();
    }

    public static List<Cliente> paraClientes(List<ClienteEntity> clienteEntities) {
        return clienteEntities.stream().map(ClienteMapper::paraCliente).toList();
    }

    public static Cliente paraDomainDeDto(ClienteDto clienteDto){
        return Cliente.builder()
                .nome(clienteDto.nome())
                .cpf(clienteDto.cpf())
                .email(clienteDto.email())
                .numeroTelefone(clienteDto.numeroTelefone())
                .build();
    }

    public static ClienteDto paraDtoDeDomain(Cliente cliente){
        return ClienteDto.builder()
                .id(cliente.getId())
                .nome(cliente.getNome())
                .cpf(cliente.getCpf())
                .email(cliente.getEmail())
                .numeroTelefone(cliente.getNumeroTelefone())
                .build();
    }
}
