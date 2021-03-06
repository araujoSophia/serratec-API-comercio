package com.residencia.comercio.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.residencia.comercio.dtos.CadastroEmpresaReceitaDTO;
import com.residencia.comercio.dtos.CadastroEnderecoDTO;
import com.residencia.comercio.dtos.FornecedorDTO;
import com.residencia.comercio.entities.Fornecedor;
import com.residencia.comercio.exceptions.NoSuchElementFoundException;
import com.residencia.comercio.services.FornecedorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/fornecedor")
@Tag(name = "Fornecedor", description = "Endpoints")
public class FornecedorController {
	@Autowired
	FornecedorService fornecedorService;

	@GetMapping
	@Operation(summary = "Lista todos os fornecedores")
	public ResponseEntity<List<Fornecedor>> findAllFornecedor() {
		List<Fornecedor> fornecedorList = fornecedorService.findAllFornecedor();
		
		if (fornecedorList.isEmpty()) {
			throw new NoSuchElementFoundException("Nenhum fornecedor encontrado.");
		} else {
		return new ResponseEntity<>(fornecedorList, HttpStatus.OK);
		}
	}

	@GetMapping("/cnpj/{cnpj}")
	@Operation(summary = "Consulta os dados por CNPJ")
	public ResponseEntity<CadastroEmpresaReceitaDTO> consultarDadosPorCnpj(String cnpj) {
		CadastroEmpresaReceitaDTO cadEmpresaDTO = fornecedorService.consultarDadosPorCnpj(cnpj);
		if (null == cadEmpresaDTO)
			throw new NoSuchElementFoundException("N??o foram encontrados dados para o CNPJ informado");
		else
			return new ResponseEntity<>(cadEmpresaDTO, HttpStatus.OK);
	}
	
	@GetMapping("/cep/{cep}")
	@Operation(summary = "Consulta os dados por CEP")
	public ResponseEntity<CadastroEnderecoDTO> consultarEnderecoPorCep(String cep) {
		CadastroEnderecoDTO cadEnderecoDTO = fornecedorService.consultarEnderecoPorCep(cep);
		if (null == cadEnderecoDTO)
			throw new NoSuchElementFoundException("N??o foram encontrados dados para o CEP informado");
		else
			return new ResponseEntity<>(cadEnderecoDTO, HttpStatus.OK);
	}

	@GetMapping("/dto/{id}")
	@Operation(summary = "Lista um fornecedor por id via DTO")
	public ResponseEntity<FornecedorDTO> findFornecedorDTOById(@PathVariable Integer id) {
		FornecedorDTO fornecedorDTO = fornecedorService.findFornecedorDTOById(id);
		return new ResponseEntity<>(fornecedorDTO, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Lista um fornecedor por id")
	public ResponseEntity<Fornecedor> findFornecedorById(@PathVariable Integer id) {
		Fornecedor fornecedor = fornecedorService.findFornecedorById(id);
		if (null == fornecedor)
			throw new NoSuchElementFoundException("N??o foi encontrado Fornecedor com o id " + id);
		else
			return new ResponseEntity<>(fornecedor, HttpStatus.OK);
	}

	@PostMapping
	@Operation(summary = "Salva um novo fornecedor")
	public ResponseEntity<Fornecedor> saveFornecedor(@Valid @RequestBody Fornecedor fornecedor) {
		Fornecedor novoFornecedor = fornecedorService.saveFornecedor(fornecedor);
		return new ResponseEntity<>(novoFornecedor, HttpStatus.CREATED);
	}

	@PostMapping("/dto")
	@Operation(summary = "Salva um novo fornecedor via DTO")
	public ResponseEntity<FornecedorDTO> saveFornecedorDTO(@RequestBody FornecedorDTO fornecedorDTO) {
		FornecedorDTO novoFornecedorDTO = fornecedorService.saveFornecedorDTO(fornecedorDTO);
		return new ResponseEntity<>(novoFornecedorDTO, HttpStatus.CREATED);
	}


	@PutMapping
	@Operation(summary = "Atualiza um fornecedor")
	public ResponseEntity<Fornecedor> updateFornecedor(@RequestBody Fornecedor fornecedor) {
		Fornecedor novoFornecedor = fornecedorService.updateFornecedor(fornecedor);
		return new ResponseEntity<>(novoFornecedor, HttpStatus.OK);
	}
	
	@DeleteMapping
	@Operation(summary = "Deleta um fornecedor")
	public ResponseEntity<String> deleteFornecedor(@RequestBody Fornecedor fornecedor) {
		if (fornecedorService.findFornecedorById(fornecedor.getIdFornecedor()) == null) {
			return new ResponseEntity<>("N??o foi poss??vel excluir. O Fornecedor de id = " + fornecedor.getIdFornecedor()
					+ " n??o foi encontrado.", HttpStatus.NOT_FOUND);
		} else {
			fornecedorService.deleteFornecedor(fornecedor);
			return new ResponseEntity<>(
					"O Fornecedor de id = " + fornecedor.getIdFornecedor() + "foi exclu??do com sucesso.",
					HttpStatus.OK);
		}
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Deleta um fornecedor por id")
	public ResponseEntity<String> deleteFornecedorById(@PathVariable Integer id) {
		if (fornecedorService.findFornecedorById(id) == null) {
			return new ResponseEntity<>("N??o foi poss??vel excluir. O Fornecedor de id = " + id + " n??o foi encontrado.",
					HttpStatus.NOT_FOUND);
		} else {
			fornecedorService.deleteFornecedorById(id);
			return new ResponseEntity<>("O Fornecedor de id = " + id + " foi exclu??do com sucesso.", HttpStatus.OK);
		}
	}

}
