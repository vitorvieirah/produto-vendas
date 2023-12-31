package com.example.produtovendas.domain;

import com.example.produtovendas.builders.Builders;
import com.example.produtovendas.dtos.ClienteDto;
import com.example.produtovendas.validators.Validators;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ClienteTest {

    @Test
    void testeMetodoInativar() {
        Cliente cliente = Builders.builderClienteDomain().get(0);
        cliente.inativar();
        Assertions.assertTrue(cliente.isInativo());
    }

    @Test
    void testeMetodoAtivar(){
        Cliente cliente = Builders.builderClienteDomain().get(0);
        cliente.ativar();
        Assertions.assertFalse(cliente.isInativo());
    }

    @Test
    void testeMetodoAtualizarDados() {
        Cliente cliente = Builders.builderClienteDomain().get(0);
        Cliente clienteDto = Builders.builderClienteDomain().get(1);
        clienteDto.setId(null);

        cliente.atualizarDados(clienteDto);

        Validators.validaClienteDomain(cliente, 1);
    }
}