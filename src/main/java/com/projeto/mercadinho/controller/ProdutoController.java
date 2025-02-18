package com.projeto.mercadinho.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.projeto.mercadinho.model.Produto;
import com.projeto.mercadinho.service.ProdutoService;

@Controller
public class ProdutoController {

	private final ProdutoService produtoService;

	public ProdutoController(ProdutoService produtoService) {
		this.produtoService = produtoService;
	}

	@GetMapping("/")
	public String listarProdutosParaIndex(Model model) {
		List<Produto> produtos = produtoService.listarTodos();
		model.addAttribute("produtos", produtos);
		return "index"; // Nome do arquivo HTML (index.html)
	}

	@RequestMapping("/produtos")
	@Controller
	public class ProdutosController {

		private final ProdutoService produtoService;

		public ProdutosController(ProdutoService produtoService) {
			this.produtoService = produtoService;
		}

		@GetMapping("/gerenciar")
		public String listarProdutos(Model model) {
			List<Produto> produtos = produtoService.listarTodos();
			model.addAttribute("produtos", produtos);
			return "gerenciarProduto"; // Nome do HTML correto
		}

		@GetMapping("/cadastrar")
		public String cadastrarProduto() {
			return "cadastrarProduto"; // Nome do HTML correto
		}

		@PostMapping("/salvar")
		public String salvarProduto(@ModelAttribute Produto produto, @RequestParam("imagem") MultipartFile imagem) {
			if (!imagem.isEmpty()) {
				try {
					// Define o diretório onde a imagem será salva
					String diretorio = "uploads/";
					Files.createDirectories(Paths.get(diretorio)); // Cria a pasta se não existir

					// Caminho completo do arquivo
					Path caminho = Paths.get(diretorio + imagem.getOriginalFilename());
					Files.write(caminho, imagem.getBytes());

					// Salva o nome do arquivo no banco, ao invés do caminho completo
					produto.setImagemPath(imagem.getOriginalFilename());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			produtoService.salvarProduto(produto);
			return "redirect:/produtos/gerenciar";
		}

		@GetMapping("/deletar/{id}")
		public String deletarProduto(@PathVariable Long id) {
			produtoService.deletarProduto(id);
			return "redirect:/produtos/gerenciar";
		}

		@GetMapping("/editar/{id}")
		public String editarProduto(@PathVariable Long id, Model model) {
			Produto produto = produtoService.buscarPorId(id)
					.orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + id));
			model.addAttribute("produto", produto);
			return "editarProduto"; // Nome da página de edição
		}

		@PostMapping("/atualizar")
		public String atualizarProduto(@ModelAttribute Produto produto,
				@RequestParam(value = "imagem", required = false) MultipartFile imagem) {
			if (imagem != null && !imagem.isEmpty()) {
				try {
					String diretorio = "uploads/";
					Files.createDirectories(Paths.get(diretorio));

					Path caminho = Paths.get(diretorio + imagem.getOriginalFilename());
					Files.write(caminho, imagem.getBytes());

					produto.setImagemPath(imagem.getOriginalFilename()); // Atualiza a imagem se for alterada
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			produtoService.salvarProduto(produto);
			return "redirect:/produtos/gerenciar";
		}

		@GetMapping("/uploads/{filename}")
		@ResponseBody
		public Resource getImagem(@PathVariable String filename) {
		    try {
		        // Define o caminho absoluto da pasta onde estão as imagens
		        Path caminho = Paths.get("C:/Projetos/mercadinho-projeto/mercadinho/uploads/").resolve(filename).normalize();
		        Resource recurso = new UrlResource(caminho.toUri());

		        if (recurso.exists() || recurso.isReadable()) {
		            return recurso;
		        } else {
		            throw new RuntimeException("Arquivo não encontrado: " + filename);
		        }
		    } catch (Exception e) {
		        throw new RuntimeException("Erro ao carregar a imagem: " + filename, e);
		    }
		}
	}
}
