package br.com.buzz.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ContaUpdateDTO {
	
	@NotNull
	@Size(min=4, message="O nome do usário precisa ter no mínimo 4 caracteres")
	private String nome;
	
	@NotNull(message = "Email obrigatório. Mesmo que não for mudar, mande o mesmo email!")
	@Email
	private String email;
	
	public ContaUpdateDTO() {}
	
	public ContaUpdateDTO(
			@NotNull @Size(min = 4, message = "O nome do usário precisa ter no mínimo 4 caracteres") String nome,
			@NotNull(message = "Email obrigatório. Mesmo que não for mudar, mande o mesmo email!") @Email String email) {
		super();
		this.nome = nome;
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
