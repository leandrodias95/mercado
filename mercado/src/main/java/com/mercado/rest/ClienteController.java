package com.mercado.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mercado.entity.Cliente;
import com.mercado.repository.ClienteRepository;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/mercado/api/clientes")
public class ClienteController {

	private final ClienteRepository repository;

	public ClienteController(ClienteRepository repository) {
		this.repository = repository;
	}

	@Operation(summary = "insert")
	@PostMapping(value = "insert")
	@ResponseStatus(HttpStatus.CREATED) // resposta da minha requisição
	public Cliente salvar(@RequestBody @Valid Cliente cliente) { 
		return repository.save(cliente); 
	}

	@Operation(summary = "getId")
	@GetMapping("{id}")
	public ResponseEntity<Cliente> acharPorId(@PathVariable Long id) {
	    Optional<Cliente> optCliente = repository.findById(id);
	    if(optCliente.isPresent()) {
	    	Cliente cliente = optCliente.get();
	    	return ResponseEntity.ok(cliente);
	    }
	    else {
	    	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
	    }
	}


	@Operation(summary = "delete")
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletarCliente(@PathVariable Long id) {
		repository.findById(id).map(cliente -> { // map transforma um elemento em outro
			repository.delete(cliente);
			return cliente;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
	}

	@Operation(summary = "update")
	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@PathVariable Long id, @RequestBody @Valid Cliente clienteAtualizar) {
		repository.findById(id).map(cliente->{
			repository.save(clienteAtualizar);
			return cliente;
		}).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cliente não encontrado"));
	}
	
	@Operation(summary = "getAll")
	@GetMapping(value= "listAll")
	public List<Cliente> listaTodos(){
		return repository.findAll();
	}
}
