package com.codedeving.Marido.de.Aluguel.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.codedeving.Marido.de.Aluguel.entity.Cliente;
import com.codedeving.Marido.de.Aluguel.entity.ServicoPrestado;
import com.codedeving.Marido.de.Aluguel.model.ClienteServicoDTO;
import com.codedeving.Marido.de.Aluguel.model.ServicoPrestadoDTO;
import com.codedeving.Marido.de.Aluguel.repository.ClienteRepository;
import com.codedeving.Marido.de.Aluguel.repository.ServicoPrestadoRepository;
import com.codedeving.Marido.de.Aluguel.util.BigDecimalConverter;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/servicos-prestados")
@RequiredArgsConstructor
public class ServicoPrestadoController {

	private final ClienteRepository clienteRepository;
	private final ServicoPrestadoRepository repository;
	private final BigDecimalConverter bigDecimalConverter;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ServicoPrestado criaServicoPrestado(@RequestBody @Valid ServicoPrestadoDTO dto) {

		Integer idCliente = Integer.parseInt(dto.getIdCliente());

		Cliente cliente = clienteRepository.findById(idCliente)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente inexistente."));

		ServicoPrestado servicoPrestado = ServicoPrestado.builder().descricao(dto.getDescricao())
				.valor(bigDecimalConverter.converter(dto.getPreco()))
				.total(bigDecimalConverter.converter(dto.getTotal()))
				.data(LocalDate.parse(dto.getData(), DateTimeFormatter.ofPattern("dd/MM/yyyy")))
				.cliente(cliente)
				.build();

		return repository.save(servicoPrestado);
	}

	@GetMapping
	public List<ServicoPrestado> pesquisarServicoPrestado(
			@RequestParam(value = "id", required = false, defaultValue = "") Integer id,
			@RequestParam(value = "nome", required = false, defaultValue = "") String nome,
			@RequestParam(value = "cpf", required = false, defaultValue = "") String cpf,
			@RequestParam(value = "mes", required = false) Integer mes) {
		if (nome != null && mes != null) {
			return repository.findByClienteNomeContainingIgnoreCaseAndData_Month("%" + nome + "%", mes);
		} else if (nome != null && !nome.isEmpty()) {
			return repository.findByClienteNomeContainingIgnoreCase("%" + nome + "%");
		} else if (mes != null) {
			return repository.findByData_Month(mes);
		} else if (cpf != null && !cpf.isEmpty()) {
			return repository.findByCpf(cpf);
		} else if (id != null && !cpf.isEmpty()) {
			return repository.findByIdCliente(id);
		} else {
			return repository.findAll();
		}
	}

	@GetMapping("{clienteId}")
	public List<ClienteServicoDTO> listarClientesEServicos(@PathVariable(name = "clienteId") Integer clienteId) {
		Cliente cliente = clienteRepository.findById(clienteId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente inexistente."));

		List<ServicoPrestado> servicosDoCliente = pesquisarServicoPrestado(null, null, cliente.getCpf(), null);

		List<ClienteServicoDTO> clienteServicoDTOs = new ArrayList<>();

		if (servicosDoCliente.isEmpty()) {
			ClienteServicoDTO clienteServicoDTO = ClienteServicoDTO.builder().clienteId(cliente.getId())
					.clienteNome(cliente.getNome()).clienteCpf(cliente.getCpf())
					.clienteDataCadastro(cliente.getDataCadastro()).clienteEndereco(cliente.getEndereco())
					.clienteTelefone(cliente.getTelefone()).build();
			clienteServicoDTOs.add(clienteServicoDTO);

		} else {
			for (ServicoPrestado servico : servicosDoCliente) {
				ClienteServicoDTO clienteServicoDTO = ClienteServicoDTO.builder().clienteId(cliente.getId())
						.clienteNome(cliente.getNome()).clienteCpf(cliente.getCpf())
						.clienteDataCadastro(cliente.getDataCadastro()).clienteEndereco(cliente.getEndereco())
						.clienteTelefone(cliente.getTelefone()).servicoId(servico.getId())
						.servicoDescricao(servico.getDescricao()).servicoValor(servico.getValor())
						.servicoTotal(servico.getTotal()).servicoData(servico.getData()).build();
				clienteServicoDTOs.add(clienteServicoDTO);
			}
		}
		return clienteServicoDTOs;
	}
}
