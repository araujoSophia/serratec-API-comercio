package com.residencia.comercio.services;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.residencia.comercio.dtos.CadastroEmpresaReceitaDTO;
import com.residencia.comercio.dtos.CadastroEnderecoDTO;
import com.residencia.comercio.dtos.FornecedorDTO;
import com.residencia.comercio.entities.Fornecedor;
import com.residencia.comercio.repositories.FornecedorRepository;

@Service
public class FornecedorService {
	@Autowired
	FornecedorRepository fornecedorRepository;

	public List<Fornecedor> findAllFornecedor() {
		return fornecedorRepository.findAll();
	}

	public Fornecedor findFornecedorById(Integer id) {
		return fornecedorRepository.existsById(id) ? fornecedorRepository.findById(id).get() : null;
	}

	public FornecedorDTO findFornecedorDTOById(Integer id) {
		return fornecedorRepository.existsById(id) ? converterEntidadeParaDTO(fornecedorRepository.findById(id).get())
				: null;
	}

	public Fornecedor saveFornecedor(Fornecedor fornecedor) {
		return fornecedorRepository.save(fornecedor);
	}

	public FornecedorDTO saveFornecedorDTO(FornecedorDTO fornecedorDTO) {
		return converterEntidadeParaDTO(fornecedorRepository.save(converterDTOParaEntidade(fornecedorDTO)));
	}
	
	public FornecedorDTO saveFornecedorByCnpj(String cnpj) {
		return converterEntidadeParaDTO(fornecedorRepository.save(CnpjDTOparaFornecedor(consultarDadosPorCnpj(cnpj))));
	}

	public Fornecedor updateFornecedor(Fornecedor fornecedor) {
		return fornecedorRepository.save(fornecedor);
	}

	public FornecedorDTO updateFornecedorByCep(Integer id, String cep) {
		return converterEntidadeParaDTO(fornecedorRepository.save(updateEnderecoFornecedor(fornecedorRepository.findById(id).get(), consultarEnderecoPorCep(cep))));
	}
	
	public void deleteFornecedor(Fornecedor fornecedor) {
		fornecedorRepository.delete(fornecedor);
	}
	
	public void deleteFornecedorById(Integer id) {
		fornecedorRepository.deleteById(id);
	}

	private Fornecedor converterDTOParaEntidade(FornecedorDTO fornecedorDTO) {
		Fornecedor fornecedor = new Fornecedor();

		fornecedor.setBairro(fornecedorDTO.getBairro());
		fornecedor.setCep(fornecedorDTO.getCep());
		fornecedor.setCnpj(fornecedorDTO.getCnpj());
		fornecedor.setComplemento(fornecedorDTO.getComplemento());
		fornecedor.setDataAbertura(fornecedorDTO.getDataAbertura());
		fornecedor.setEmail(fornecedorDTO.getEmail());
		fornecedor.setIdFornecedor(fornecedorDTO.getIdFornecedor());
		fornecedor.setLogradouro(fornecedorDTO.getLogradouro());
		fornecedor.setMunicipio(fornecedorDTO.getMunicipio());
		fornecedor.setNomeFantasia(fornecedorDTO.getNomeFantasia());
		fornecedor.setNumero(fornecedorDTO.getNumero());
		fornecedor.setRazaoSocial(fornecedorDTO.getRazaoSocial());
		fornecedor.setStatusSituacao(fornecedorDTO.getStatusSituacao());
		fornecedor.setTelefone(fornecedorDTO.getTelefone());
		fornecedor.setTipo(fornecedorDTO.getTipo());
		fornecedor.setUf(fornecedorDTO.getUf());

		return fornecedor;
	}

	private FornecedorDTO converterEntidadeParaDTO(Fornecedor fornecedor) {
		FornecedorDTO fornecedorDTO = new FornecedorDTO();

		fornecedorDTO.setBairro(fornecedor.getBairro());
		fornecedorDTO.setCep(fornecedor.getCep());
		fornecedorDTO.setCnpj(fornecedor.getCnpj());
		fornecedorDTO.setComplemento(fornecedor.getComplemento());
		fornecedorDTO.setDataAbertura(fornecedor.getDataAbertura());
		fornecedorDTO.setEmail(fornecedor.getEmail());
		fornecedorDTO.setIdFornecedor(fornecedor.getIdFornecedor());
		fornecedorDTO.setLogradouro(fornecedor.getLogradouro());
		fornecedorDTO.setMunicipio(fornecedor.getMunicipio());
		fornecedorDTO.setNomeFantasia(fornecedor.getNomeFantasia());
		fornecedorDTO.setNumero(fornecedor.getNumero());
		fornecedorDTO.setRazaoSocial(fornecedor.getRazaoSocial());
		fornecedorDTO.setStatusSituacao(fornecedor.getStatusSituacao());
		fornecedorDTO.setTelefone(fornecedor.getTelefone());
		fornecedorDTO.setTipo(fornecedor.getTipo());
		fornecedorDTO.setUf(fornecedor.getUf());

		return fornecedorDTO;
	}

	public Fornecedor CnpjDTOparaFornecedor(CadastroEmpresaReceitaDTO cadastroEmpresaReceitaDTO) {
		Fornecedor fornecedor = new Fornecedor();

		Date data = new Date();

		fornecedor.setCnpj(cadastroEmpresaReceitaDTO.getCnpj());
		fornecedor.setDataAbertura(data);
		fornecedor.setEmail(cadastroEmpresaReceitaDTO.getEmail());
		fornecedor.setNomeFantasia(cadastroEmpresaReceitaDTO.getFantasia());
		fornecedor.setRazaoSocial(cadastroEmpresaReceitaDTO.getNome());
		fornecedor.setStatusSituacao(cadastroEmpresaReceitaDTO.getSituacao());
		fornecedor.setTelefone(cadastroEmpresaReceitaDTO.getTelefone());
		fornecedor.setTipo(cadastroEmpresaReceitaDTO.getTipo());
		fornecedor.setNumero(cadastroEmpresaReceitaDTO.getNumero());

		return fornecedor;
	}

	public Fornecedor updateEnderecoFornecedor(Fornecedor fornecedor, CadastroEnderecoDTO cadastroEnderecoDTO) {
		fornecedor.setLogradouro(cadastroEnderecoDTO.getLogradouro());
		fornecedor.setBairro(cadastroEnderecoDTO.getBairro());
		fornecedor.setComplemento(cadastroEnderecoDTO.getComplemento());
		fornecedor.setMunicipio(cadastroEnderecoDTO.getLocalidade());
		fornecedor.setUf(cadastroEnderecoDTO.getUf());
		fornecedor.setCep(cadastroEnderecoDTO.getCep());

		return fornecedor;
	}

	public CadastroEmpresaReceitaDTO consultarDadosPorCnpj(String cnpj) {
		RestTemplate restTemplate = new RestTemplate();
		String uri = "https://www.receitaws.com.br/v1/cnpj/{cnpj}";
		Map<String, String> params = new HashMap<String, String>();
		params.put("cnpj", cnpj);

		CadastroEmpresaReceitaDTO cadastroEmpresaReceitaDTO = restTemplate.getForObject(uri,
				CadastroEmpresaReceitaDTO.class, params);

		return cadastroEmpresaReceitaDTO;
	}

	public CadastroEnderecoDTO consultarEnderecoPorCep(String cep) {
		RestTemplate restTemplate = new RestTemplate();
		String uri = "https://www.viacep.com.br/ws/{cep}/json/";
		Map<String, String> params = new HashMap<String, String>();
		params.put("cep", cep);

		CadastroEnderecoDTO consultarEnderecoPorCep = restTemplate.getForObject(uri, CadastroEnderecoDTO.class, params);

		return consultarEnderecoPorCep;
	}
}
