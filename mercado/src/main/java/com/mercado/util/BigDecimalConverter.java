package com.mercado.util;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class BigDecimalConverter {

	
	public BigDecimal converter(String valor) {
		if(valor==null) {
			return null;
		}
			valor = valor.replace(".", "").replace(",", ".");
		return new BigDecimal(valor);
	}
}
