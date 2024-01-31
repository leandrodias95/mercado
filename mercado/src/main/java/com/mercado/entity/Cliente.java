package com.mercado.entity;

import java.time.LocalDate;
import java.util.Date;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, length = 150)
	@NotEmpty(message="{nome.vazio}")
	private String nome;
	@Column(nullable = false)
	@NotNull(message="{cpf.vazio}")
	@CPF(message="{cpf.invalido}")
	private String cpf;
	@Column(nullable = false)
	@NotNull(message= "{data.nascimento.obrigatorio}")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataNascimento;
	@Column(updatable = false)
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataCadastro;
	
	@PrePersist
	public void prePersist() {
		setDataCadastro(LocalDate.now());
	}
}
