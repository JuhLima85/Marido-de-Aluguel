package com.codedeving.Marido.de.Aluguel.model;

import com.codedeving.Marido.de.Aluguel.enuns.UsuarioRole;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class UpdateDTO {

	private final Long userId;

	private final String userExistenteLogin;

	private final String userAtualLogin;

	private final String userAtualPassword;

	private final UsuarioRole userAtualRole;

}