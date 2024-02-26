package com.mercado.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Autowired
	SecurityFilter securiyFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
		return httpSecurity
				.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorize -> authorize
				.requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
				.requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
				.requestMatchers(HttpMethod.POST, "/mercado/api/clientes/insert").hasRole("USER")
				.requestMatchers(HttpMethod.GET, "/mercado/api/clientes/**").hasRole("USER")
				.requestMatchers(HttpMethod.DELETE, "/mercado/api/clientes/**").hasRole("USER")
				.requestMatchers(HttpMethod.PUT, "/mercado/api/clientes/**").hasRole("USER")
				.requestMatchers(HttpMethod.POST, "/mercado/api/produtos/insert").hasRole("USER")
				.requestMatchers(HttpMethod.GET, "/mercado/api/produtos/listaPorProduto/**").hasRole("USER")
				.requestMatchers(HttpMethod.GET, "/mercado/api/produtos/listaPorProdutoCod/**").hasRole("USER")
				.requestMatchers(HttpMethod.GET, "/mercado/api/produtos/getProcuarPorProdutoPorNomeCliente/**").hasRole("USER")
				.anyRequest().authenticated()
				)
				.addFilterBefore(securiyFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	} 
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() { //faz a criptografia da senha
		return new BCryptPasswordEncoder(); 
	}
}
