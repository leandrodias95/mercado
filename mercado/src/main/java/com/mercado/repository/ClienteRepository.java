package com.mercado.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mercado.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	@Query("select c from Cliente c where cpf=:cpf")
	Optional<Cliente> findByCPF(@Param(value="cpf") String cpfCliente);

}
