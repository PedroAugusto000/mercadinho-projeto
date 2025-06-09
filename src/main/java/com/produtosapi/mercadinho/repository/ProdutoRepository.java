package com.produtosapi.mercadinho.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.JpaRepositoryConfigExtension;
import org.springframework.data.repository.CrudRepository;

import com.produtosapi.mercadinho.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, UUID>{

}
