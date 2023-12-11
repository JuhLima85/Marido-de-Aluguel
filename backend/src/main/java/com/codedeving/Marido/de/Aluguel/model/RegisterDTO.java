package com.codedeving.Marido.de.Aluguel.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.codedeving.Marido.de.Aluguel.enuns.UsuarioRole;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RegisterDTO {

	@NotEmpty(message = "{campo.login.obrigatorio}")
	private final String login;

	@NotEmpty(message = "{campo.senha.obrigatorio}")
	private final String password;

	@NotNull(message = "{campo.perfil.obrigatorio}")
	private final UsuarioRole role;
}
