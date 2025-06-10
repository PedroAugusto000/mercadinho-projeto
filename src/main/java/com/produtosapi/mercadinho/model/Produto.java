package com.produtosapi.mercadinho.model;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@Entity
@Table(name = "produto")
@Data
public class Produto {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
    
    @Column(name = "nome", length = 35, nullable = false)
    private String nome;
    
    @Column(name = "preco", precision = 18, scale = 2, nullable = false)
    private BigDecimal preco;
    
    @Column(name = "descricao", length = 200, nullable = false)
    private String descricao;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "categorias", length = 25, nullable = false)
    private Categorias categorias;
    
    @Column(name = "imagem_url", length = 255, nullable = true)
    private String imagemUrl;	
}
