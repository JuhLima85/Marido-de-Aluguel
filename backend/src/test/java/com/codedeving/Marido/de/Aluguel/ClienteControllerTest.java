package com.codedeving.Marido.de.Aluguel;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ClienteControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	private Cliente cliente;

	@Test
	@WithMockUser // Autenticação ao seu teste - simular um usuário autenticado durante o teste
	@BeforeEach // criando um cliente de teste no banco de dados antes de cada teste, ou seja,
				// garante que sempre tenha um cliente criado.
	void salvarCliente() throws Exception {

		cliente = new Cliente(1, "Fernanda Lima", "80666040095", "Qd 103 Cj 01 - Brasília - DF", "61999999999");
		// Converte o objeto Cliente para JSON
		String clienteJson = objectMapper.writeValueAsString(cliente);

		// Execute a requisição POST para /api/clientes
		ResultActions result = mockMvc
				.perform(post("/api/clientes").contentType(MediaType.APPLICATION_JSON).content(clienteJson))
				.andExpect(status().isCreated());
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
	}

	@Test
	@WithMockUser
	void obterTodosUsandoMySQL() throws Exception {
		// Execute a requisição GET para /api/clientes
		ResultActions result = mockMvc.perform(get("/api/clientes")).andExpect(status().isOk());

		// Verifica o Status da resposta
		result.andExpect(status().isOk());
		// Verifica o Tipo de Conteúdo
		result.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		// Imprimir a lista no console
		MvcResult mvcResult = result.andReturn();
		String content = mvcResult.getResponse().getContentAsString();
		System.out.println("Lista de clientes do H2: " + content);
	}

	@Test
	@WithMockUser
	void buscarClientePorIdExistenteDeveRetornarOk() throws Exception {
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
	    // Criar um cliente para atualização
	    Cliente clienteAtualizado = new Cliente(1, "Fernanda Atualizada", "89017284005", "Endereço Atualizado", "987654321");

	    // Converte o objeto Cliente para JSON
	    String clienteAtualizadoJson = objectMapper.writeValueAsString(clienteAtualizado);

	    // Execute a requisição PUT para /api/clientes/{id}
	    ResultActions result = mockMvc.perform(put("/api/clientes/{id}", cliente.getId())
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(clienteAtualizadoJson));

	    // Verifique se a resposta foi um status No Content (204)
	    result.andExpect(status().isNoContent());

	    // Verifique se o corpo da resposta está vazio
	    result.andExpect(content().string(""));
	}

	@Test
	@WithMockUser
	void atualizarClienteNaoExistenteDeveRetornarNotFound() throws Exception {
	    // Criar um cliente atualizado
	    Cliente clienteAtualizado = new Cliente(2, "Fernanda Atualizada", "89017284005", "Endereço Atualizado", "987654321");

	    // Converte o objeto Cliente para JSON
	    String clienteAtualizadoJson = objectMapper.writeValueAsString(clienteAtualizado);

	    // Execute a requisição PUT para /api/clientes/{id} com um ID inexistente
	    ResultActions result = mockMvc.perform(put("/api/clientes/{id}", 999)
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(clienteAtualizadoJson))
	            .andExpect(status().isNotFound());

	    // Verifique se a resposta foi um status Not Found (404)
	    result.andExpect(status().isNotFound());
	}
	
	@Test
	@WithMockUser
	void deletarClienteExistenteDeveRetornarNoContent() throws Exception {
	    // Execute a requisição DELETE para /api/clientes/{id}
	    ResultActions result = mockMvc.perform(delete("/api/clientes/{id}", cliente.getId()))
	            .andExpect(status().isNoContent());

	    // Verifique se a resposta foi um status No Content (204)
	    result.andExpect(status().isNoContent());

	    // Verifique se o corpo da resposta está vazio
	    result.andExpect(content().string(""));
	}
	
	@Test
	@WithMockUser
	void deletarClienteNaoExistenteDeveRetornarNotFound() throws Exception {
	    // Execute a requisição DELETE para /api/clientes/{id} com um ID inexistente
	    ResultActions result = mockMvc.perform(delete("/api/clientes/{id}", 999))
	            .andExpect(status().isNotFound());

	    // Verifique se a resposta foi um status Not Found (404)
	    result.andExpect(status().isNotFound());
	}

}
