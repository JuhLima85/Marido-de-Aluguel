package com.codedeving.Marido.de.Aluguel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.codedeving.Marido.de.Aluguel.entity.Usuario;
import com.codedeving.Marido.de.Aluguel.exceptions.UsuarioCadastradoException;
import com.codedeving.Marido.de.Aluguel.model.RegisterDTO;
import com.codedeving.Marido.de.Aluguel.model.UpdateDTO;
import com.codedeving.Marido.de.Aluguel.repository.UsuarioRepository;

@Service
public class AuthorizationService implements UserDetailsService {

	@Autowired
	UsuarioRepository repository;

	public void registerUser(RegisterDTO data) {

		if (repository.findByLogin(data.getLogin()) != null) {
			throw new UsuarioCadastradoException(data.getLogin());
		}

		String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
		Usuario novoUsuario = new Usuario(data.getLogin(), encryptedPassword, data.getRole());

		repository.save(novoUsuario);
	}

	public void updateUser(UpdateDTO dadosAtualizacao) {

		if (repository.findByLogin(dadosAtualizacao.getUserExistenteLogin()) == null) {
			throw UsuarioCadastradoException.usuarioNaoEncontrado(dadosAtualizacao.getUserExistenteLogin());
		}

		if (repository.findByLogin(dadosAtualizacao.getUserAtualLogin()) != null) {
			throw new UsuarioCadastradoException(dadosAtualizacao.getUserAtualLogin());
		}

		Usuario usuarioExistente = new Usuario();
		usuarioExistente.setId(dadosAtualizacao.getUserId());
		usuarioExistente.setLogin(dadosAtualizacao.getUserAtualLogin());
		usuarioExistente.setPassword(new BCryptPasswordEncoder().encode(dadosAtualizacao.getUserAtualPassword()));
		usuarioExistente.setRole(dadosAtualizacao.getUserAtualRole());

		repository.save(usuarioExistente);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repository.findByLogin(username);
	}
}
