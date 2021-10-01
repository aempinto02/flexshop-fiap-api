package br.com.buzz.dto;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ContaInsertDTO {
	
	@NotNull
	@Size(min=3, message="O nome precisa ter pelo menos 3 caracteres!")
	private String nome;
	
	@NotBlank
	@Email
	@Column(unique=true)
	private String email;
	
	@NotBlank
	@Size(min=5, message="O endereço precisa no mínimo de 5 caracteres!")
	private String endereco;
	
	@NotBlank(message = "Senha obrigatória!")
	@Size(min = 6, max = 100, message = "A senha precisa ter de 6 a 100 caracteres")
	@Pattern(regexp = "(?=\\S+$).+", message = "A senha não pode ter espaços em branco!")
	private String senha;
	
	@NotNull
	private Integer tipo;
	
	public ContaInsertDTO() {}
	
	public ContaInsertDTO(@NotNull @Size(min = 3, message = "O nome precisa ter pelo menos 3 caracteres!") String nome,
			@NotNull @Email String email,
			@NotBlank @Size(min=5, message="O endereço precisa no mínimo de 5 caracteres!") String endereco,
			@NotBlank(message = "Senha obrigatória!") @Size(min = 6, max = 100, message = "A senha precisa ter de 6 a 100 caracteres") @Pattern(regexp = "(?=\\S+$).+", message = "A senha não pode ter espaços em branco!") String senha,
			@NotNull Integer tipo) {
		super();
		this.nome = nome;
		this.email = email;
		this.endereco = endereco;
		this.senha = senha;
		this.tipo = tipo;
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
	
	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
}
