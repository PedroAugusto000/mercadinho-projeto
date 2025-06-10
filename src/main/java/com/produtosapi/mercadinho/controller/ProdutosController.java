package com.produtosapi.mercadinho.controller;
import java.util.UUID;

import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.produtosapi.mercadinho.controller.dto.ProdutoDTO;
import com.produtosapi.mercadinho.controller.mapper.ProdutoMapper;
import com.produtosapi.mercadinho.model.Produto;
import com.produtosapi.mercadinho.service.ProdutoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutosController implements GerarHeaderController{

	private final ProdutoMapper mapper;
	
	private final ProdutoService service;
		
	//---Criar um produto---
	@PostMapping
	ResponseEntity<Void> salvar(@RequestBody ProdutoDTO dto){
		Produto produto = mapper.toEntity(dto);
		service.salvar(produto);
		var url = gerarHeaderLocation(produto.getId());
		return ResponseEntity.created(url).build();
	}
	
	//---Consultar um produto---
	@GetMapping("{id}")
	ResponseEntity<ProdutoDTO> consultarDetalhes(@PathVariable("id") String id) {
		return service.obterPorId(UUID.fromString(id))
				.map(produto -> ResponseEntity.ok(mapper.toDto(produto)))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	//---Deletar um produto---
	@DeleteMapping("{id}")
	ResponseEntity<Object> deletar(@PathVariable("id") String id) {
		return service.obterPorId(UUID.fromString(id))
				.map(produto -> {
					service.deletar(produto);
					return ResponseEntity.noContent().build();
				}).orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	//---Atualizar um produto---
	@PutMapping("{id}")
	ResponseEntity<Object> atualizarPorId(@PathVariable("id") String id, @RequestBody ProdutoDTO dto) {
		return service.obterPorId(UUID.fromString(id))
				.map(produto -> {
					Produto entidadeAux = mapper.toEntity(dto);
					
					produto.setNome(entidadeAux.getNome());
					produto.setPreco(entidadeAux.getPreco());
					produto.setDescricao(entidadeAux.getDescricao());
					produto.setCategorias(entidadeAux.getCategorias());
					
					service.atualizar(produto);
					
                    return ResponseEntity.noContent().build();

                }).orElseGet( () -> ResponseEntity.notFound().build());
	}
}
