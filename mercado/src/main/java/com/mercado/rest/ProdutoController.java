package com.mercado.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mercado.entity.Cliente;
import com.mercado.entity.Produto;
import com.mercado.repository.ClienteRepository;
import com.mercado.repository.ProdutoRepository;
import com.mercado.rest.dto.ProdutoDTO;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor 
@RequestMapping("/mercado/api/produtos")
public class ProdutoController {

	private final ClienteRepository clienteRepository;
	private final ProdutoRepository produtoRepository;
	//private final BigDecimalConverter bigDecimalConverter;
	
	@Operation(summary = "insertProduto")
	@PostMapping(value="insert")
	@ResponseStatus(HttpStatus.CREATED)
	public Produto salvar(@RequestBody ProdutoDTO produtoDTO) {
		Produto produto = new Produto();
		//BigDecimal valor = bigDecimalConverter.converter(produtoDTO.getValor());
		Optional<Cliente> optCliente = clienteRepository.findByCPF(produtoDTO.getCpfCompra());
		if(optCliente.isPresent()) {
			Cliente cliente1 = optCliente.get();
			produto.setProduto(produtoDTO.getProduto());
			produto.setDescricao(produtoDTO.getDescricao());
			produto.setValor(produtoDTO.getValor());
			produto.setCliente(cliente1);
			produto.setCodProduto(produtoDTO.getCodProduto());
			produto.setCpfCompra(produtoDTO.getCpfCompra());
			return produtoRepository.save(produto); 
		}
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado!");
		}
	}
	
	@Operation(summary = "getProcuarPorProduto")
	@GetMapping(value="listaPorProduto")
	@ResponseStatus(HttpStatus.OK)
	public List<Produto> procurarPorProduto(@RequestParam(value="nomeCliente", required = false, defaultValue ="") String nomeCliente, @RequestParam(value="codProduto", required = false) String codProduto, 
			@RequestParam(value="dia", required = false) Integer dia, @RequestParam(value="mes", required = false) Integer mes, @RequestParam(value="ano", required = false) Integer ano){
		List<Produto> resultado = produtoRepository.meetProduct("%"+nomeCliente+"%", codProduto, dia, mes, ano);
		return Optional.ofNullable(resultado).filter(list->!list.isEmpty()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado!") );
	}
	
	@Operation(summary = "getProcuarPorProdutoCod")
	@GetMapping(value="listaPorProdutoCod")
	@ResponseStatus(HttpStatus.OK)
	public List<Produto> procurarPorProdutoCod(@RequestParam(value="nomeCliente", required =true) String nomeCliente, @RequestParam(value="codProduto", required = true) String codProduto){
		List<Produto> resultado = produtoRepository.meetProductCod("%"+nomeCliente+"%", codProduto);
		return Optional.ofNullable(resultado).filter(list->!list.isEmpty()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado!"));
	}
	
	@Operation(summary = "getProcuarPorProdutoPorNomeCliente")
	@GetMapping(value="listaPorProdutoNomeCliente")
	@ResponseStatus(HttpStatus.OK)
	public List<Produto> procurarPorProdutoPorNomeCliente(@RequestParam(value="nomeCliente", required =true) String nomeCliente){
		List<Produto> resultado = produtoRepository.meetProductCodPorNomeCliente("%"+nomeCliente+"%");
		return Optional.ofNullable(resultado).filter(list->!list.isEmpty()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum produto está vinculado a esse cliente!"));
	}
}
