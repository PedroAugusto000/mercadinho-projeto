package com.produtosapi.mercadinho.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.produtosapi.mercadinho.controller.dto.ProdutoDTO;
import com.produtosapi.mercadinho.model.Produto;
import com.produtosapi.mercadinho.repository.ProdutoRepository;

import lombok.RequiredArgsConstructor;

@Service
public class ProdutoService {

	@Autowired
	ProdutoRepository repository;
	
	public Produto salvar(Produto produto) {
		return repository.save(produto);
	}
	
	public Optional<Produto> obterPorId(UUID id) {
		return repository.findById(id);
	}
	
	public void deletar(Produto produto) {
		repository.delete(produto);
	}
	
	public void atualizar(Produto produto) {
		repository.save(produto);
	}
}
