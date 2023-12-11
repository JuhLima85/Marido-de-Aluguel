package com.codedeving.Marido.de.Aluguel.exceptions;

public class UsuarioCadastradoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UsuarioCadastradoException(String login) {
		super("O nome de usuário " + login + " já está em uso.");
	}

	public static UsuarioCadastradoException usuarioNaoEncontrado(String login) {
		return new UsuarioCadastradoException("Usuário não encontrado: " + login);
	}

}
