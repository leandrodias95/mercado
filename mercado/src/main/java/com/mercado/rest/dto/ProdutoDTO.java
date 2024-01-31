package com.mercado.rest.dto;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProdutoDTO {

	private String produto;
	private String descricao;
	private BigDecimal valor;
	private String cpfCompra;
	private String codProduto;	
}
