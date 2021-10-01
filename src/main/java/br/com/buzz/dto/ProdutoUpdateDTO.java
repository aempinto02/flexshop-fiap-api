package br.com.buzz.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProdutoUpdateDTO {
	
	@NotNull
	@Size(min=3, message="O produto precisa de um título de 3 caracteres no mínimo!")
	private String titulo;
	
	@NotNull
	@Size(min=6, message="O título precisa de ao menos 6 caracteres")
	private String descricao;
	
	@NotNull
	private BigDecimal preco;
	
	public ProdutoUpdateDTO() {}

	public ProdutoUpdateDTO(
			@NotNull @Size(min = 3, message = "O produto precisa de um título de 3 caracteres no mínimo!") String titulo,
			@NotNull @Size(min = 6, message = "O título precisa de ao menos 6 caracteres") String descricao,
			@NotNull BigDecimal preco) {
		super();
		this.titulo = titulo;
		this.descricao = descricao;
		this.preco = preco;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
}
