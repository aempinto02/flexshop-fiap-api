package br.com.buzz.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RecoverPasswordDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Nova senha obrigatória")
	@Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
	@Pattern(regexp = "(?=.*[a-z]).+", message = "Senha deve conter ao menos 1 letra minúscula")
	@Pattern(regexp = "(?=.*[A-Z]).+", message = "Senha deve conter ao menos 1 letra maiúscula")
	@Pattern(regexp = "(?=\\S+$).+", message = "Senha não deve conter espaços em branco")
	private String newPassword;

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public RecoverPasswordDTO() {
		super();
	}
}
