package com.produtosapi.mercadinho.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
public class ProdutosController implements GerarHeaderController{

	
	@Autowired
	ProdutoMapper mapper;
	
	@Autowired
	ProdutoService service;
		
	@PostMapping
	ResponseEntity<Void> salvar(@RequestBody ProdutoDTO dto){
		Produto produto = mapper.toEntity(dto);
		service.salvar(produto);
		var url = gerarHeaderLocation(produto.getId());
		return ResponseEntity.created(url).build();
	}
	
	@GetMapping("{id}")
	ResponseEntity<ProdutoDTO> consultarDetalhes(@PathVariable("id") String id) {
		return null;
	}
	
	@DeleteMapping("{id}")
	ResponseEntity<Object> deletar(@PathVariable("id") String id) {
		return service.obterPorId(UUID.fromString(id))
				.map(produto -> {
					service.deletar(produto);
					return ResponseEntity.noContent().build();
				}).orElseGet(() -> ResponseEntity.notFound().build());
	}
}
