package com.residencia.comercio.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.residencia.comercio.dtos.CategoriaDTO;
import com.residencia.comercio.entities.Categoria;
import com.residencia.comercio.exceptions.NoSuchElementFoundException;
import com.residencia.comercio.services.CategoriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/categoria")
@Tag(name = "Categoria", description = "Endpoints")
public class CategoriaController {
	@Autowired
	CategoriaService categoriaService;

	@GetMapping
	@Operation(summary = "Lista todas as categorias")
	public ResponseEntity<List<Categoria>> findAllCategoria() {
		List<Categoria> categoriaList = categoriaService.findAllCategoria();
		return new ResponseEntity<>(categoriaList, HttpStatus.OK);
	}

	@GetMapping("/dto/{id}")
	@Operation(summary = "Lista uma categoria por id via DTO")
	public ResponseEntity<CategoriaDTO> findCategoriaDTOById(@PathVariable Integer id) {
		CategoriaDTO categoriaDTO = categoriaService.findCategoriaDTOById(id);
		return new ResponseEntity<>(categoriaDTO, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Lista uma categoria por id")
	public ResponseEntity<Categoria> findCategoriaById(@PathVariable Integer id) {
		Categoria categoria = categoriaService.findCategoriaById(id);
		if(null == categoria)
			throw new NoSuchElementFoundException("Não foi encontrada Categoria com o id " + id);
		else
			return new ResponseEntity<>(categoria, HttpStatus.OK);
	}
	
	@PostMapping
	@Operation(summary = "Salva uma nova categoria")
	public ResponseEntity<Categoria> saveCategoria(@Valid @RequestBody Categoria categoria) {
		Categoria novoCategoria = categoriaService.saveCategoria(categoria);
		return new ResponseEntity<>(novoCategoria, HttpStatus.CREATED);
	}

	@PostMapping("/dto")
	@Operation(summary = "Salva uma nova categoria via DTO")
	public ResponseEntity<CategoriaDTO> saveCategoriaDTO(@RequestBody CategoriaDTO categoriaDTO) {
		CategoriaDTO novoCategoriaDTO = categoriaService.saveCategoriaDTO(categoriaDTO);
		return new ResponseEntity<>(novoCategoriaDTO, HttpStatus.CREATED);
	}
	
	@PostMapping(value = "/com-foto", consumes = 
			{MediaType.APPLICATION_JSON_VALUE, 
			MediaType.MULTIPART_FORM_DATA_VALUE})
	@Operation(summary = "Salva uma nova categoria com foto")
	public ResponseEntity<Categoria> saveCategoriaComFoto(
			@RequestPart("categoria") String categoria,
			@RequestPart("file") MultipartFile file) throws Exception {
		
		Categoria novaCategoria = categoriaService.saveCategoriaComFoto(categoria, file);
		return new ResponseEntity<>(novaCategoria, HttpStatus.CREATED);
	}
	@PutMapping
	@Operation(summary = "Atualiza uma categoria")
	public ResponseEntity<Categoria> updateCategoria(@Valid @RequestBody Categoria categoria) {
		Categoria novaCategoria = categoriaService.updateCategoria(categoria);
		return new ResponseEntity<>(novaCategoria, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Deleta uma categoria")
	public ResponseEntity<String> deleteCategoria(@PathVariable Integer id) {
		if(null == categoriaService.findCategoriaById(id))
			return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
		
		categoriaService.deleteCategoria(id);
		return new ResponseEntity<>("", HttpStatus.OK);
	}

}
