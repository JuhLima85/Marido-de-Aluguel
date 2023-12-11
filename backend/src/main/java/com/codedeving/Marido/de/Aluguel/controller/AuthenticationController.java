package com.codedeving.Marido.de.Aluguel.controller;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.codedeving.Marido.de.Aluguel.config.TokenService;
import com.codedeving.Marido.de.Aluguel.entity.Usuario;
import com.codedeving.Marido.de.Aluguel.exceptions.UsuarioCadastradoException;
import com.codedeving.Marido.de.Aluguel.model.AuthenticationDTO;
import com.codedeving.Marido.de.Aluguel.model.LoginResponseDTO;
import com.codedeving.Marido.de.Aluguel.model.RegisterDTO;
import com.codedeving.Marido.de.Aluguel.model.UpdateDTO;
import com.codedeving.Marido.de.Aluguel.service.AuthorizationService;

@RestController
@RequestMapping("api/auth")
public class AuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private AuthorizationService service;
	@Autowired
	private TokenService tokenService;

	@PostMapping("/login")
	public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
		try {
			System.out.println("user: " + data.getLogin());
			var usernamePassword = new UsernamePasswordAuthenticationToken(data.getLogin(), data.getPassword());
			var auth = this.authenticationManager.authenticate(usernamePassword);
			var token = tokenService.generateToken((Usuario) auth.getPrincipal());

			return ResponseEntity.ok(new LoginResponseDTO(token));
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	
	@PostMapping("/register")
	public void register(@RequestBody @Valid RegisterDTO data) {
		try {
			service.registerUser(data);
		} catch (UsuarioCadastradoException e) {

			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@PutMapping("/update")
	public ResponseEntity updateUser(@RequestBody @Valid UpdateDTO dadosAtualizacao ) { 
	    try {
	        service.updateUser(dadosAtualizacao);
	        return ResponseEntity.ok().build();
	    } catch (EntityNotFoundException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	    } catch (UsuarioCadastradoException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}



}