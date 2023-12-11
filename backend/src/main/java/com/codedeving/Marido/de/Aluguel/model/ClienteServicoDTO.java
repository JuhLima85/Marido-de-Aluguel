package com.codedeving.Marido.de.Aluguel.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteServicoDTO {

	private Integer clienteId;
	private String clienteNome;
	private String clienteCpf;
	private LocalDate clienteDataCadastro;
	private String clienteEndereco;
	private String clienteTelefone;
	private Integer servicoId;
	private String servicoDescricao;
	private BigDecimal servicoValor;
	private BigDecimal servicoTotal;
	private LocalDate servicoData;

}
