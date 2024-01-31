package com.mercado.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String produto;
	@Column(nullable = false, length = 300)
	private String descricao;
	
	@Column(nullable= false)
	private BigDecimal valor;
	@ManyToOne
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;
	
	@Column
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataCompra;
	
	@Column
	private String codProduto;
	
	@Column
	private String cpfCompra;
	
	@PrePersist
	public void Prepersist() {
		setDataCompra(LocalDate.now());
	}
	
}
