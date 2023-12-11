package com.codedeving.Marido.de.Aluguel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.codedeving.Marido.de.Aluguel.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	UserDetails findByLogin(String login);

}
