package br.com.buzz.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AuthDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Nome de usuário obrigatório! Necessariamento o email do usuário!")
	@Size(min = 4, max = 100, message = "O nome de usuário precisa ter de 4 a 100 caracteres")
	private String username;

	@NotBlank(message = "Senha obrigatória!")
	@Size(min = 6, max = 100, message = "A senha precisa ter de 6 a 100 caracteres")
	@Pattern(regexp = "(?=\\S+$).+", message = "A senha não pode ter espaços em branco!")
	private String password;
	
	private Integer role;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}
}
