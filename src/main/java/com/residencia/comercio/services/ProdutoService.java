package com.residencia.comercio.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.residencia.comercio.dtos.ProdutoDTO;
import com.residencia.comercio.entities.Produto;
import com.residencia.comercio.repositories.CategoriaRepository;
import com.residencia.comercio.repositories.FornecedorRepository;
import com.residencia.comercio.repositories.ProdutoRepository;

@Service
public class ProdutoService {
	@Autowired
	ProdutoRepository produtoRepository;

	@Autowired
	FornecedorRepository fornecedorRepository;

	@Autowired

	CategoriaRepository categoriaRepository;

	public List<Produto> findAllProduto() {
		return produtoRepository.findAll();
	}

	public Produto findProdutoById(Integer id) {
		return produtoRepository.findById(id).isPresent() ? produtoRepository.findById(id).get() : null;
	}

	public ProdutoDTO findProdutoDTOById(Integer id) {
		return produtoRepository.existsById(id) ? converterEntidadeParaDTO(produtoRepository.findById(id).get()) : null;
	}

	public Produto saveProduto(Produto produto) {
		return produtoRepository.save(produto);
	}

	public ProdutoDTO saveProdutoDTO(ProdutoDTO produtoDTO) {
		return converterEntidadeParaDTO(produtoRepository.save(converterDTOParaEntidade(produtoDTO)));
	}

	public Produto updateProduto(Produto produto) {
		return produtoRepository.save(produto);
	}

	public void deleteProduto(Produto produto) {
		produtoRepository.delete(produto);
	}

	public void deleteProdutoById(Integer id) {
		produtoRepository.deleteById(id);
	}

	private Produto converterDTOParaEntidade(ProdutoDTO produtoDTO) {
		Produto produto = new Produto();

		if (produtoDTO.getFornecedorId() != null) {
			produto.setFornecedor(fornecedorRepository.findById(produtoDTO.getFornecedorId()).get());
		} else {
			produto.setFornecedor(null);
		}

		if (produtoDTO.getCategoriaId() != null) {
			produto.setCategoria(categoriaRepository.findById(produtoDTO.getCategoriaId()).get());
		} else {
			produto.setCategoria(null);
		}

		produto.setIdProduto(produtoDTO.getIdProduto());
		produto.setSku(produtoDTO.getSku());
		produto.setNomeProduto(produtoDTO.getNomeProduto());

		return produto;
	}

	private ProdutoDTO converterEntidadeParaDTO(Produto produto) {
		ProdutoDTO produtoDTO = new ProdutoDTO();

		if (produto.getFornecedor() != null) {
			produtoDTO.setFornecedorId(produto.getFornecedor().getIdFornecedor());
			produtoDTO.setFornecedorNome(produto.getFornecedor().getNomeFantasia());
		}

		if (produto.getCategoria() != null) {
			produtoDTO.setCategoriaId(produto.getCategoria().getIdCategoria());
			produtoDTO.setCategoriaNome(produto.getCategoria().getNomeCategoria());

			produtoDTO.setIdProduto(produto.getIdProduto());
			produtoDTO.setSku(produto.getSku());
			produtoDTO.setNomeProduto(produto.getNomeProduto());
		}
			return produtoDTO;
	}
}
