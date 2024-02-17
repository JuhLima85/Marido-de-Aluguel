package com.codedeving.Marido.de.Aluguel;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.codedeving.Marido.de.Aluguel.entity.Cliente;
import com.codedeving.Marido.de.Aluguel.entity.ServicoPrestado;
import com.codedeving.Marido.de.Aluguel.model.ServicoPrestadoDTO;
import com.codedeving.Marido.de.Aluguel.repository.ClienteRepository;
import com.codedeving.Marido.de.Aluguel.repository.ServicoPrestadoRepository;
import com.codedeving.Marido.de.Aluguel.util.BigDecimalConverter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServicoPrestadoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private BigDecimalConverter bigDecimalConverter;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ServicoPrestadoRepository servicoPrestadoRepository;

	private Cliente cliente;

	private ServicoPrestadoDTO dto;

	@Test
	@WithMockUser
	@BeforeEach
	public void setUp() {
		/* Configuração inicial para o teste */
		// Salvamos um cliente para usar nos teste
		cliente = new Cliente ("Fernanda Lima", "80666040095", LocalDate.now(), "Qd 103 Cj 01 - Brasília - DF",
				"61999999999");
		clienteRepository.save(cliente);

		// Criamos um obj sp
		dto = new ServicoPrestadoDTO("Instalação elétrica", "110.00", "01/02/2024", String.valueOf(cliente.getId()), "110");
	}

	@Test
	@Order(1)
	@WithMockUser(username = "Juliana", password = "123", roles = "ADMIN") // Autenticação ao seu teste - simular um
																			// usuário autenticado durante o teste. Lá
																			// no SecurityConfig eu liberei esta rota
	void criaServicoPrestado() throws Exception {			
		mockMvc.perform(post("/api/servicos-prestados").contentType("application/json")
				.content(objectMapper.writeValueAsString(dto))).andExpect(status().isCreated())		
				.andExpect(jsonPath("$.id").exists()) // Verifica os dados retornados pelo método.
				.andExpect(jsonPath("$.descricao").value(dto.getDescricao()))
				.andExpect(jsonPath("$.valor").value(bigDecimalConverter.converter(dto.getPreco())))
				.andExpect(jsonPath("$.total").value(bigDecimalConverter.converter(dto.getTotal())))
				.andExpect(jsonPath("$.data").value(dto.getData()))
				.andExpect(jsonPath("$.cliente.id").value(cliente.getId()))
				.andExpect(jsonPath("$.cliente.nome").value(cliente.getNome()));
		}

	@Test
	@Order(2)
	@WithMockUser(username = "Juliana", password = "123", roles = "ADMIN")
	void pesquisarServicoPrestado() throws Exception {
		// Cria alguns serviços prestados para teste
		ServicoPrestado servico1 = new ServicoPrestado("Servico 1", cliente, BigDecimal.valueOf(100), LocalDate.now());
		ServicoPrestado servico2 = new ServicoPrestado("Servico 2", cliente, BigDecimal.valueOf(150), LocalDate.now());
		servicoPrestadoRepository.saveAll(Arrays.asList(servico1, servico2));

		// Execute a requisição GET para /api/servicos-prestados
		ResultActions result = mockMvc.perform(get("/api/servicos-prestados")).andExpect(status().isOk());

		// Converte a resposta JSON para uma lista de ServicoPrestado
		List<ServicoPrestado> servicoPrestados = objectMapper.readValue(
				result.andReturn().getResponse().getContentAsString(), new TypeReference<List<ServicoPrestado>>() {
				});

		// Verifica se a lista não está vazia
		assertFalse(servicoPrestados.isEmpty());

		// Verificações sobre cada serviço na lista
		for (ServicoPrestado servicoPrestado : servicoPrestados) {
			assertNotNull(servicoPrestado.getDescricao());
			assertNotNull(servicoPrestado.getValor());
			assertNotNull(servicoPrestado.getData());
			assertNotNull(servicoPrestado.getCliente());
		}
	}

	@Test
	@Order(3)
	@WithMockUser(username = "Juliana", password = "123", roles = "ADMIN")
	void listarClientesEServicos() throws Exception {
		ServicoPrestado servico = new ServicoPrestado(1, "Instalação elétrica", cliente, BigDecimal.valueOf(100),
				LocalDate.now());
		servicoPrestadoRepository.save(servico);
		String url = "/api/servicos-prestados/" + cliente.getId();

		mockMvc.perform(get(url)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].clienteId").value(cliente.getId()))
				.andExpect(jsonPath("$[0].clienteNome").value(cliente.getNome()))
				.andExpect(jsonPath("$[0].clienteCpf").value(cliente.getCpf()))
				.andExpect(jsonPath("$[0].clienteDataCadastro").value(cliente.getDataCadastro().toString()))
				.andExpect(jsonPath("$[0].clienteEndereco").value(cliente.getEndereco()))
				.andExpect(jsonPath("$[0].clienteTelefone").value(cliente.getTelefone()))
				.andExpect(jsonPath("$[0].servicoId").value(servico.getId()))
				.andExpect(jsonPath("$[0].servicoDescricao").value(servico.getDescricao()))
				.andExpect(jsonPath("$[0].servicoValor")
						.value(servico.getValor().setScale(1, RoundingMode.HALF_UP).toString()))
				.andExpect(jsonPath("$[0].servicoData").value(servico.getData().toString()));
	}
}
