package com.codedeving.Marido.de.Aluguel.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServicoPrestado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, length = 150)
	private String descricao;

	@ManyToOne
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;

	@Column
	private BigDecimal valor;

	@Column
	private BigDecimal total;

	@Column
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate data;

	//Usado nos testes
	public ServicoPrestado(Integer id, String descricao, Cliente cliente, BigDecimal valor, LocalDate data) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.cliente = cliente;
		this.valor = valor;
		this.data = data;
	}	
	
	//Usado nos testes
		public ServicoPrestado(String descricao, Cliente cliente, BigDecimal valor, LocalDate data) {
			super();			
			this.descricao = descricao;
			this.cliente = cliente;
			this.valor = valor;
			this.data = data;
		}

}
