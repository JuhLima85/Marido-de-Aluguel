package com.codedeving.Marido.de.Aluguel.exceptions;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

public class ApiErrors {

	@Getter
	private List<String> errors;

	public ApiErrors(List<String> errors) {
		this.errors = errors;
	}

	public ApiErrors(String message) {
		this.errors = Arrays.asList(message);
	}

}
