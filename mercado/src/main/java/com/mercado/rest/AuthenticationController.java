package com.mercado.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercado.entity.Usuario;
import com.mercado.repository.UsuarioRepository;
import com.mercado.rest.dto.AuthentificationDTO;
import com.mercado.rest.dto.LoginResponseDTO;
import com.mercado.rest.dto.RegisterDTO;
import com.mercado.security.TokenService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthenticationController  {
	
	private final UsuarioRepository usuarioRepository;
	private final AuthenticationManager authenticationManager;
	private final TokenService tokenService;
	
	@PostMapping("/login")
	public ResponseEntity login( @RequestBody @Valid AuthentificationDTO data) {
		var usernamePassword =  new UsernamePasswordAuthenticationToken(data.login(), data.password());
		var auth = this.authenticationManager.authenticate(usernamePassword);
		var token = tokenService.generateToken((Usuario)auth.getPrincipal());
		return ResponseEntity.ok(new LoginResponseDTO(token)); 
	}
	
	@PostMapping("/register")
	public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
		if(usuarioRepository.findByLogin(data.login())!=null) return ResponseEntity.badRequest().build();
		String encryptedPassword =  new BCryptPasswordEncoder().encode(data.password()); //salva senha criptografada
		Usuario newUser = new Usuario(data.login(), encryptedPassword, data.role());
		usuarioRepository.save(newUser);
		return ResponseEntity.ok().build();
	}
	
}
