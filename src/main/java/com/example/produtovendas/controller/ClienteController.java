package com.example.produtovendas.controller;

import com.example.produtovendas.domain.Cliente;
import com.example.produtovendas.dtos.ClienteDto;
import com.example.produtovendas.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteDto> cadastroCliente(@RequestBody @Valid ClienteDto dto, UriComponentsBuilder uriBuilder) {
        ClienteDto clienteBody = clienteService.cadastroCliente(dto);
        var uri = uriBuilder.path("/clientes/{id}").buildAndExpand(clienteBody.id()).toUri();
        return ResponseEntity.created(uri).body(clienteBody);
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> consultarTodosClientes() {
        return ResponseEntity.ok(clienteService.consultaTodosClientes());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Cliente> consultaClientePorId(@PathVariable("id") Long id) {
        Optional<Cliente> cliente = clienteService.consultaClientePorId(id);
        return cliente.map(clienteMap -> ResponseEntity.ok(clienteMap))
                .orElse(ResponseEntity.noContent().build());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable("id") Long id) {
        clienteService.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ClienteDto> altararCliente(@PathVariable("id") Long id, @RequestBody ClienteDto clienteDto) {
        return ResponseEntity.ok(clienteService.alterarCliente(id, clienteDto));
    }
}
