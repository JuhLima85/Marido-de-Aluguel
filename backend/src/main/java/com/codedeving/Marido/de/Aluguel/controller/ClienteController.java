package com.codedeving.Marido.de.Aluguel.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.codedeving.Marido.de.Aluguel.entity.Cliente;
import com.codedeving.Marido.de.Aluguel.entity.ServicoPrestado;
import com.codedeving.Marido.de.Aluguel.repository.ClienteRepository;
import com.codedeving.Marido.de.Aluguel.repository.ServicoPrestadoRepository;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

	@Autowired
	private ClienteRepository repository;

	@Autowired
	private ServicoPrestadoRepository spRepository;

	@GetMapping
	public List<Cliente> obterTodos() {
		return repository.findAll();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente salvar(@RequestBody @Valid Cliente cliente) {
		return repository.save(cliente);
	}

	@GetMapping("{id}")
	public Cliente acharPorId(@PathVariable Integer id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Transactional(rollbackFor = Exception.class)
	public void deletar(@PathVariable Integer id) {
		Cliente cliente = repository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

		List<ServicoPrestado> servicosDoCliente = spRepository.findByIdCliente(id);

		if (!servicosDoCliente.isEmpty()) {
			spRepository.deletarServicosDeUmCliente(id);
		}

		repository.delete(cliente);
	}

	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@PathVariable Integer id, @RequestBody @Valid Cliente clienteAtualizado) {
		repository.findById(id).map(cliente -> {
			cliente.setNome(clienteAtualizado.getNome());
			cliente.setCpf(clienteAtualizado.getCpf());
			return repository.save(cliente);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
	}
}
