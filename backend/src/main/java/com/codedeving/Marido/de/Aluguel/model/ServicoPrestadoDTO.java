package com.codedeving.Marido.de.Aluguel.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicoPrestadoDTO {

	@NotEmpty(message = "{campo.descricao.obrigatorio}")
	private String descricao;

	@NotEmpty(message = "{campo.preco.obrigatorio}")
	private String preco;

	@NotEmpty(message = "{campo.data.obrigatorio}")
	private String data;

	@NotNull(message = "{campo.cliente.obrigatorio}")
	private String idCliente;

	private String total;

}
