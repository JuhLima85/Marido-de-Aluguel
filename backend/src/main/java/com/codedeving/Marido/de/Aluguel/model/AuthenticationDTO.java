package com.codedeving.Marido.de.Aluguel.model;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AuthenticationDTO {

	@NotEmpty(message = "{campo.login.obrigatorio}")
	private final String login;

	@NotEmpty(message = "{campo.senha.obrigatorio}")
	private final String password;
}
