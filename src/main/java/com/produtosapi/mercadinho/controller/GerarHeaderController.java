package com.produtosapi.mercadinho.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public interface GerarHeaderController {
	
    default URI gerarHeaderLocation(UUID id){
        return ServletUriComponentsBuilder
                .fromCurrentRequest() // Pegou a URL da requisição atual que chegou na controller
                .path("/{id}") // Adiciona um pedaço novo na URL, no caso o {id}, mas não coloca nada, só libera espaço
                .buildAndExpand(id) // Daqui ele já monta. Troca o {id} por um valor real
                .toUri(); // Converte a URL que a gente montou em um objeto para URI, que é a resposta HTTP no cabeçalho Location
    }
}

