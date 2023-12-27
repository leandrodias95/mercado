package com.mercado.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/mercado/api/clientes")
public class ClienteController {
	
	private final ClienteRepository repository;
	
	public ClienteController(ClienteRepository repository) {
		this.repository = repository;
	}

	@PostMapping(value="insert")
	@ResponseStatus(HttpStatus.CREATED) //resposta da minha requisição
	public ResponseEntity<Object> salvar(@RequestBody @Valid Cliente cliente, BindingResult result){ //BindingResult extend erros do validator
		if(result.hasErrors()){
			Map<String, String> errors = new HashMap<>(); ///associa chave aos valores
			for(FieldError error: result.getFieldErrors()) {
				errors.put(error.getField(),error.getDefaultMessage());	
			}
			return ResponseEntity.badRequest().body(errors);
		}
		Cliente clienteSalvo = repository.save(cliente);
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
	}
	
	@GetMapping("{id}")
	public Cliente acharPorId(@PathVariable Long id) {
		return repository.findById(id).orElseThrow(()-> new  ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletarCliente(@PathVariable Long id) {
	 repository.findById(id).map(cliente->{ //map transforma um elemento em outro
		repository.delete(cliente);
		return cliente; 
	 })
	 .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Object> atualizar(@PathVariable Long id, @RequestBody @Valid Cliente clienteAtualizar, BindingResult result) {
		if(!repository.findById(id).isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		else if(repository.findById(id).isPresent() && result.hasErrors() ) {
			Map<String, String> errors = new HashMap<>();
			for(FieldError error: result.getFieldErrors()) {
				errors.put(error.getField(), error.getDefaultMessage());
			}
			return ResponseEntity.badRequest().body(errors);
		}
		repository.findById(id).map(cliente->{
			clienteAtualizar.setId(cliente.getId());
			repository.save(clienteAtualizar);
			return ResponseEntity.noContent().build();
		});
		return ResponseEntity.noContent().build() ; 
	}
	
}
