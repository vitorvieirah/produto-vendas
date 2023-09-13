package com.example.produtovendas.controller;

import com.example.produtovendas.builders.Builders;
import com.example.produtovendas.infra.entities.ProdutoEntity;
import com.example.produtovendas.infra.entities.VendaEntity;
import com.example.produtovendas.infra.repositories.ClienteRepository;
import com.example.produtovendas.infra.repositories.ProdutoRepository;
import com.example.produtovendas.infra.repositories.VendaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class VendaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VendaRepository repository;

    @MockBean
    private ClienteRepository clienteRepository;

    @MockBean
    private ProdutoRepository produtoRepository;

    @Test
    void testeMetodoCadastroVenda() throws Exception {
        when(produtoRepository.findById(any())).thenReturn(Optional.of(new ProdutoEntity()));
        when(clienteRepository.findById(any())).thenReturn(Builders.builderClienteOptional().get(0));
        when(repository.save(any())).thenReturn(Builders.builderVenda().get(0));
        String json = builderJson();
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/vendas").contentType(MediaType.APPLICATION_JSON).content(json)).
                andExpect(MockMvcResultMatchers.status().isCreated());
        validaVenda(resultActions);
    }

    @Test
    void testeMetodoBuscarPorId() throws Exception {
        when(repository.findById(any())).thenReturn(Builders.builderVendaOptional().get(0));

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/vendas/{id}", Builders.builderVenda().get(0).getId())).
                andExpect(MockMvcResultMatchers.status().isOk());
        validaVenda(resultActions);
    }

    @Test
    void testeMetodoBuscarTodos() throws Exception{
        when(repository.findAll()).thenReturn(Builders.builderVenda());

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/vendas")).
                andExpect(MockMvcResultMatchers.status().isOk());
        validaVendas(resultActions, "0", Builders.builderVenda().get(0));
        validaVendas(resultActions, "1", Builders.builderVenda().get(0));
    }

    @Test
    void testeMetodoDeletarVenda() throws Exception {
        when(repository.findById(any())).thenReturn(Builders.builderVendaOptional().get(0));
        when(repository.save(any())).thenReturn(Builders.builderVenda().get(0));

        mockMvc.perform(MockMvcRequestBuilders.delete("/vendas/{id}", Builders.builderVenda().get(0).getId())).
                andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testeMetodoAlterarVenda() throws Exception {
        when(repository.findById(any())).thenReturn(Builders.builderVendaOptional().get(0));
        when(repository.save(any())).thenReturn(Builders.builderVenda().get(0));

        String json = builderJson();

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/vendas/{id}", Builders.builderVenda().get(0).getId()).contentType(MediaType.APPLICATION_JSON).content(json)).
                andExpect(MockMvcResultMatchers.status().isOk());

        validaVenda(resultActions);
    }

    private void validaVenda(ResultActions resultActions) throws Exception{
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(Builders.builderVenda().get(0).getId())).
                andExpect(MockMvcResultMatchers.jsonPath("$.clienteEntity").value(Builders.builderVenda().get(0).getClienteEntity())).
                andExpect(MockMvcResultMatchers.jsonPath("$.inativo").value(Builders.builderVenda().get(0).isInativo())).
                andExpect(MockMvcResultMatchers.jsonPath("$.valor").value(Builders.builderVenda().get(0).getValor())).
                andExpect(MockMvcResultMatchers.jsonPath("$.desconto").value(Builders.builderVenda().get(0).getDesconto())).
                andExpect(MockMvcResultMatchers.jsonPath("$.listaProduto").value(Builders.builderVenda().get(0).getListaProdutos())).
                andExpect(MockMvcResultMatchers.jsonPath("$.dataVenda").value(Builders.builderVenda().get(0).getDataVenda()));
    }

    private void validaVendas(ResultActions resultActions, String indexListVenda, VendaEntity vendaEntity) throws Exception{
        String index = "$[".concat(indexListVenda).concat("].");
        resultActions.andExpect(jsonPath(index.concat("id")).value(vendaEntity.getId())).
                andExpect(jsonPath(index.concat("clienteEntity")).value(vendaEntity.getClienteEntity())).
                andExpect(jsonPath(index.concat("inativo")).value(vendaEntity.isInativo())).
                andExpect(jsonPath(index.concat("valor")).value(vendaEntity.getValor())).
                andExpect(jsonPath(index.concat("desconto")).value(vendaEntity.getDesconto())).
                andExpect(jsonPath(index.concat("listaProdutos")).value(vendaEntity.getListaProdutos())).
                andExpect(jsonPath(index.concat("dataVenda")).value(vendaEntity.getDataVenda()));
    }

    private String builderJson(){
        return "{\"idCliente\":12,\"desconto\":10,\"listaProdutos\":[{\"id\":3},{\"id\":7}]}";
    }
}