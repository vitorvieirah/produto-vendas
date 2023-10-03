package com.example.produtovendas.domain;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@ToString
@Setter
@Builder
public class Venda {

    private Long id;
    private Cliente cliente;
    private Long idCliente;
    private BigDecimal valor;
    private boolean inativo;
    private Integer desconto;
    private List<Produto> listaProdutos;
    private LocalDate dataVenda;

    public void inativar() {
        this.inativo = true;
    }

    public void atualizaDados(Venda vendaDto) {
        this.inativo = false;
        this.cliente = vendaDto.getCliente();
        this.idCliente = vendaDto.getIdCliente();
        this.valor = vendaDto.getValor();
        this.desconto = vendaDto.getDesconto();
        this.listaProdutos = vendaDto.getListaProdutos();
    }

    public void calcularValorVenda() {
        double resultado;
        double valorSomaProdutos = 0;
        for (Produto produto : this.listaProdutos) {
            valorSomaProdutos += produto.getValor().doubleValue();
        }

        if (this.desconto > 0) {
            double valorDesconto = (valorSomaProdutos * this.desconto) / 100;
            resultado = valorSomaProdutos - valorDesconto;
        } else {
            resultado = valorSomaProdutos;
        }

        this.valor = BigDecimal.valueOf(resultado);
    }
}
