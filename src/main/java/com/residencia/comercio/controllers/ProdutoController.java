package com.residencia.comercio.controllers;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.residencia.comercio.dtos.ProdutoDTO;
import com.residencia.comercio.entities.Produto;
import com.residencia.comercio.exceptions.NoSuchElementFoundException;
import com.residencia.comercio.services.ProdutoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/produto")
@Validated
@Tag(name = "Produto", description = "Endpoints")
public class ProdutoController {
	@Autowired
	ProdutoService produtoService;

	@GetMapping
	@Operation(summary = "Lista todos os produtos")
	public ResponseEntity<List<Produto>> findAllProduto() {
		List<Produto> produtoList = produtoService.findAllProduto();

		if (produtoList.isEmpty()) {
			throw new NoSuchElementFoundException("Nenhum produto encontrado.");
		} else {
			return new ResponseEntity<>(produtoList, HttpStatus.OK);
		}
	}

	@GetMapping("/dto/{id}")
	@Operation(summary = "Lista um produto por id via DTO")
	public ResponseEntity<ProdutoDTO> findProdutoDTOById(@PathVariable Integer id) {
		ProdutoDTO produtoDTO = produtoService.findProdutoDTOById(id);

		if (produtoDTO == null) {
			throw new NoSuchElementFoundException("Não foi encontrado Produto com o id = " + id + ".");
		} else {
			return new ResponseEntity<>(produtoDTO, HttpStatus.OK);
		}
	}

	@GetMapping("/{id}")
	@Operation(summary = "Lista um produto por id")
	public ResponseEntity<Produto> findProdutoById(@RequestParam Integer id) {
		Produto produto = produtoService.findProdutoById(id);
		if (null == produto)
			throw new NoSuchElementFoundException("Não foi encontrado Produto com o id " + id);
		else
			return new ResponseEntity<>(produto, HttpStatus.OK);
	}

	@GetMapping("/query")
	public ResponseEntity<Produto> findByIdQuery(
			@RequestParam @NotBlank(message = "O sku deve ser preenchido.") String sku) {
		return new ResponseEntity<>(null, HttpStatus.CONTINUE);
	}

	@GetMapping("/request")
	public ResponseEntity<Produto> findByIdRequest(
			@RequestParam @NotBlank(message = "O id deve ser preenchido.") Integer id) {
		return new ResponseEntity<>(null, HttpStatus.CONTINUE);
	}

	@PostMapping
	@Operation(summary = "Salva um novo produto")
	public ResponseEntity<Produto> saveProduto(@Valid @RequestBody Produto produto) {
		Produto novoProduto = produtoService.saveProduto(produto);
		return new ResponseEntity<>(novoProduto, HttpStatus.CREATED);
	}

	@PostMapping("/dto")
	@Operation(summary = "Salva um novo produto via DTO")
	public ResponseEntity<ProdutoDTO> saveProdutoDTO(@RequestBody ProdutoDTO produtoDTO) {
		ProdutoDTO novoProdutoDTO = produtoService.saveProdutoDTO(produtoDTO);
		return new ResponseEntity<>(novoProdutoDTO, HttpStatus.CREATED);
	}

	@PutMapping
	@Operation(summary = "Atualiza um produto")
	public ResponseEntity<Produto> updateProduto(@RequestBody Produto produto) {
		Produto novoProduto = produtoService.updateProduto(produto);
		return new ResponseEntity<>(novoProduto, HttpStatus.OK);
	}

	@DeleteMapping
	@Operation(summary = "Deleta um produto")
	public ResponseEntity<String> deletePoduto(Produto produto) {
		if (produtoService.findProdutoById(produto.getIdProduto()) == null) {
			return new ResponseEntity<>(
					"Não foi possível excluir. O Produto de id = " + produto.getIdProduto() + " não foi encontrado.",
					HttpStatus.NOT_FOUND);
		} else {
			produtoService.deleteProduto(produto);
			return new ResponseEntity<>("O Produto de id = " + produto.getIdProduto() + " foi excluído com sucesso.",
					HttpStatus.OK);
		}
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Deleta um produto por id")
	public ResponseEntity<String> deleteProdutoById(@PathVariable Integer id) {
		if (produtoService.findProdutoById(id) == null) {
			return new ResponseEntity<>("Não foi possível excluir. O Produto de id = " + id + " não foi encontrado.",
					HttpStatus.NOT_FOUND);
		} else {
			produtoService.deleteProdutoById(id);
			return new ResponseEntity<>("O Produto de id = " + id + " foi excluído com sucesso.", HttpStatus.OK);
		}
	}
}
