package com.codedeving.Marido.de.Aluguel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.codedeving.Marido.de.Aluguel.entity.Cliente;
import com.codedeving.Marido.de.Aluguel.repository.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ClienteControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	
	 @Autowired
	 private ClienteRepository clienteRepository; // usu apenas para get (buscar) pra varificaçoes

	private Cliente cliente;

	@BeforeEach // criando um cliente de teste antes de cada teste, ou seja, garante que sempre
				// tenha um obj cliente criado para ser usado em todos os testes.
	void setUp() {
		// Configuração inicial para o teste
		cliente = new Cliente(1, "Fernanda Lima", "80666040095", "Qd 103 Cj 01 - Brasilia - DF", "61999999999");
	}

	@Test
	@WithMockUser // Autenticação ao seu teste - simular um usuário autenticado durante o teste
	void salvarCliente() throws Exception {
		
		// Converte o objeto Cliente para JSON
		String clienteJson = objectMapper.writeValueAsString(cliente);

		// Execute a requisição POST para /api/clientes
		ResultActions result = mockMvc
				.perform(post("/api/clientes").contentType(MediaType.APPLICATION_JSON).content(clienteJson))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.id").value(cliente.getId()))
				.andExpect(jsonPath("$.nome").value(cliente.getNome()))
				.andExpect(jsonPath("$.cpf").value(cliente.getCpf()))
				.andExpect(jsonPath("$.endereco").value(cliente.getEndereco()))
				.andExpect(jsonPath("$.telefone").value(cliente.getTelefone()));		
	}

	@Test
	@WithMockUser
	void salvarClienteComCPFIvalidoDeveRetornarBadRequest() throws Exception {
		// Criar um cliente com CPF inválido
		Cliente clienteCpfInvalido = new Cliente(2, "Cliente CPF Inválido", "12345678901", "Endereço", "987654321");

		// Converte o objeto Cliente para JSON
		String clienteCpfInvalidoJson = objectMapper.writeValueAsString(clienteCpfInvalido);

		// Execute a requisição POST para /api/clientes
		ResultActions result = mockMvc
				.perform(post("/api/clientes").contentType(MediaType.APPLICATION_JSON).content(clienteCpfInvalidoJson));

		// Verifique se a resposta foi um status Bad Request (400)
		result.andExpect(status().isBadRequest());
		
		// Verifique se o cliente com CPF inválido não foi salvo no banco de dados
	    assertFalse(clienteRepository.existsById(clienteCpfInvalido.getId()));
	}

	@Test
	@WithMockUser
	void obterTodosUsandoMySQL() throws Exception {
		
		// Salva o cliete para ter algo na lista
		 Cliente savedCliente = clienteRepository.save(cliente);
		
		// Execute a requisição GET para /api/clientes
		ResultActions result = mockMvc.perform(get("/api/clientes")).andExpect(status().isOk());

		// Verifica o Status da resposta
		result.andExpect(status().isOk());
		// Verifica o Tipo de Conteúdo
		result.andExpect(content().contentType(MediaType.APPLICATION_JSON));

		MvcResult mvcResult = result.andReturn();
		String content = mvcResult.getResponse().getContentAsString();
				
		// Converte a resposta JSON para uma lista de clientes
	    List<Cliente> clientes = objectMapper.readValue(content, new TypeReference<List<Cliente>>() {});

	    // Verifica se a lista não está vazia
	    assertFalse(clientes.isEmpty());

	    // Verificações sobre cada cliente na lista
	    for (Cliente cliente : clientes) {
	        assertNotNull(cliente.getId());
	        assertNotNull(cliente.getNome());
	        assertNotNull(cliente.getCpf());
	        assertNotNull(cliente.getEndereco());
	        assertNotNull(cliente.getTelefone());
	    }
	}

	@Test
	@WithMockUser
	void buscarClientePorIdExistenteDeveRetornarOk() throws Exception {
		
		// Salva o cliete para ter algo no bd
		Cliente savedCliente = clienteRepository.save(cliente);
		
		// Execute a requisição GET para /api/clientes/{id}
		ResultActions result = mockMvc.perform(get("/api/clientes/{id}", cliente.getId())).andExpect(status().isOk())// verifica
																														// o
																														// status
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))// verificar o JSON retornado
				.andExpect(jsonPath("$.id").value(cliente.getId())) // verificam se contém os valores esperados para
																	// cada campo específico
				.andExpect(jsonPath("$.nome").value(cliente.getNome()))
				.andExpect(jsonPath("$.cpf").value(cliente.getCpf()))
				.andExpect(jsonPath("$.endereco").value(cliente.getEndereco()))
				.andExpect(jsonPath("$.telefone").value(cliente.getTelefone()));
		
		 // Converte a resposta JSON para um objeto Cliente
	    Cliente responseCliente = objectMapper.readValue(result.andReturn().getResponse().getContentAsString(), Cliente.class);

	    // Verificações adicionais sobre o cliente retornado
	    assertNotNull(responseCliente);
	    assertEquals(cliente.getId(), responseCliente.getId());
	    assertEquals(cliente.getNome(), responseCliente.getNome());
	    assertEquals(cliente.getCpf(), responseCliente.getCpf());
	    assertEquals(cliente.getEndereco(), responseCliente.getEndereco());
	    assertEquals(cliente.getTelefone(), responseCliente.getTelefone());
	}

	@Test
	@WithMockUser
	void buscarClientePorIdNaoExistenteDeveRetornarNotFound() throws Exception {
		// Execute a requisição GET para /api/clientes/{id} com um ID inexistente
		ResultActions result = mockMvc.perform(get("/api/clientes/{id}", 999)).andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser
	void atualizarClienteExistenteDeveRetornarNoContent() throws Exception {
		
		// Salva o cliete para ter algo na lista
		Cliente clienteExixtente = clienteRepository.save(cliente);
		
		// Criar um cliente para atualização
		Cliente clienteAtualizado = new Cliente(1, "Fernanda Atualizada", "89017284005", "Endereço Atualizado",
				"987654321");

		// Converte o objeto Cliente para JSON
		String clienteAtualizadoJson = objectMapper.writeValueAsString(clienteAtualizado);

		// Execute a requisição PUT para /api/clientes/{id}
		ResultActions result = mockMvc.perform(put("/api/clientes/{id}", cliente.getId())
				.contentType(MediaType.APPLICATION_JSON).content(clienteAtualizadoJson));

		// Verifique se a resposta foi um status No Content (204)
		result.andExpect(status().isNoContent());

		// Verifique se o corpo da resposta está vazio
		result.andExpect(content().string(""));
		
		 // vefica se os dados do o atual é diferente do existente
	    assertNotNull(clienteExixtente);
	    assertNotEquals(clienteAtualizado.getNome(), clienteExixtente.getNome());
	    assertNotEquals(clienteAtualizado.getCpf(), clienteExixtente.getCpf());
	    assertNotEquals(clienteAtualizado.getEndereco(), clienteExixtente.getEndereco());
	    assertNotEquals(clienteAtualizado.getTelefone(), clienteExixtente.getTelefone());
	}

	@Test
	@WithMockUser
	void atualizarClienteNaoExistenteDeveRetornarNotFound() throws Exception {
		// Criar um cliente atualizado
		Cliente clienteAtualizado = new Cliente(2, "Fernanda Atualizada", "89017284005", "Endereço Atualizado",
				"987654321");

		// Converte o objeto Cliente para JSON
		String clienteAtualizadoJson = objectMapper.writeValueAsString(clienteAtualizado);

		// Execute a requisição PUT para /api/clientes/{id} com um ID inexistente
		ResultActions result = mockMvc.perform(
				put("/api/clientes/{id}", 999).contentType(MediaType.APPLICATION_JSON).content(clienteAtualizadoJson))
				.andExpect(status().isNotFound());

		// Verifique se a resposta foi um status Not Found (404)
		result.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser
	void deletarClienteExistenteDeveRetornarNoContent() throws Exception {
		// Salva o cliete para ter algo na lista
				 Cliente savedCliente = clienteRepository.save(cliente);
				 
		// Execute a requisição DELETE para /api/clientes/{id}
		ResultActions result = mockMvc.perform(delete("/api/clientes/{id}", cliente.getId()))
				.andExpect(status().isNoContent());

		// Verifique se a resposta foi um status No Content (204)
		result.andExpect(status().isNoContent());

		// Verifique se o corpo da resposta está vazio
		result.andExpect(content().string(""));
		
		 // Verifique se o cliente foi removido do banco de dados
	    assertFalse(clienteRepository.existsById(cliente.getId()));		
		
	}

	@Test
	@WithMockUser
	void deletarClienteNaoExistenteDeveRetornarNotFound() throws Exception {
		// Execute a requisição DELETE para /api/clientes/{id} com um ID inexistente
		ResultActions result = mockMvc.perform(delete("/api/clientes/{id}", 999)).andExpect(status().isNotFound());

		// Verifique se a resposta foi um status Not Found (404)
		result.andExpect(status().isNotFound());
	}
}