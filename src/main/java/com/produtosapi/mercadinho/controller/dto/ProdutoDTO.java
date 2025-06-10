package com.produtosapi.mercadinho.controller.dto;

import java.math.BigDecimal;
import java.util.UUID;
import com.produtosapi.mercadinho.model.Categorias;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(name = "Produto")
public record ProdutoDTO(
		
    UUID id,
    
    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 35, message = "O nome deve ter no máximo 35 caracteres")
    @Schema(name = "nome")
    String nome,
    
    @NotNull(message = "O preço é obrigatório")
    @DecimalMin(value = "0.01", inclusive = true, message = "O preço deve ser maior que zero")
    @Digits(integer = 16, fraction = 2, message = "O preço deve ter no máximo 16 dígitos inteiros e 2 decimais")
    @Schema(name = "preco")
    BigDecimal preco,
    
    @NotBlank(message = "A descrição é obrigatória")
    @Size(max = 200, message = "A descrição deve ter no máximo 200 caracteres")
    @Schema(name = "descricao")
    String descricao,
    
    @NotNull(message = "A categoria é obrigatória")
    @Schema(name = "categorias")
    Categorias categorias
) {}