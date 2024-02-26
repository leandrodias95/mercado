package com.mercado.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.mercado.entity.Usuario;



public interface UsuarioRepository extends JpaRepository<Usuario, String>{

	UserDetails findByLogin(String login); 
}
