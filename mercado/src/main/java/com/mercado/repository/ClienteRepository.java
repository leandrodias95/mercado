package com.mercado.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mercado.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

}
