package com.produtosapi.mercadinho.controller.dto;

import java.math.BigDecimal;
import java.util.UUID;
import com.produtosapi.mercadinho.model.Categorias;

public record ProdutoDTO(
    UUID id,
    String nome,
    BigDecimal preco,
    String descricao,
    Categorias categorias
) {}