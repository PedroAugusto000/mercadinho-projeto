package com.produtosapi.mercadinho.controller.mapper;

import org.mapstruct.Mapper;

import com.produtosapi.mercadinho.controller.dto.ProdutoDTO;
import com.produtosapi.mercadinho.model.Produto;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

	Produto toEntity(ProdutoDTO dto);
	ProdutoDTO toDto(Produto entity);
}
