package br.com.buzz.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CatalogoUpdateDTO {
	
	@NotNull
	@Size(min=4, message="O nome do catálogo precisa ter no mínimo 4 caracteres")
	private String nome;
	
	@NotNull
	private String horario;
	
	@NotNull
	@Size(min=4, message="A descrição do catálogo precisa ter no mínimo 4 caracteres")
	private String descricao;
	
	public CatalogoUpdateDTO() {}

	public CatalogoUpdateDTO(
			@Size(min = 4, message = "O nome do catálogo precisa ter no mínimo 4 caracteres") String nome,
			String horario,
			@Size(min = 4, message = "A descrição do catálogo precisa ter no mínimo 4 caracteres") String descricao) {
		super();
		this.nome = nome;
		this.horario = horario;
		this.descricao = descricao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
