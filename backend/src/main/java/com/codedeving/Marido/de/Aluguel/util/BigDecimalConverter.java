package com.codedeving.Marido.de.Aluguel.util;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class BigDecimalConverter {
	
	public BigDecimal converter(String value) {
		if (value == null) {
			return null;
		}
		value = value.replace(".", "").replace(",", ".");
		return new BigDecimal(value);

	}

}
