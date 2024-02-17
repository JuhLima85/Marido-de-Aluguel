package com.codedeving.Marido.de.Aluguel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codedeving.Marido.de.Aluguel.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
}