package br.com.buzz.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.buzz.domain.enums.UserRole;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
public class Conta {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@EqualsAndHashCode.Include
	private Long id;
	
	@NotNull
	@Size(min=3, message="O nome precisa ter pelo menos 3 caracteres!")
	private String nome;
	
	@NotNull
	@Email
	@Column(unique=true)
	private String email;
	
	@NotNull
	@NotBlank
	private String endereco;
	
	@NotNull
	private Integer tipo;
	
	@NotNull
	@OneToMany
	private List<Catalogo> catalogos;
	
	public UserRole getTipo() {
		return UserRole.toEnum(tipo);
	}
	
	public void setTipo(UserRole tipo) {
		this.tipo = tipo.getCode();
	}
	
	public Conta(Long id, @NotNull @NotBlank String endereco,
			@NotNull UserRole tipo,
			@NotNull @Size(min = 3, message = "O nome precisa ter pelo menos 3 caracteres!") String nome,
			@NotNull @Email String email) {
		super();
		this.id = id;
		this.endereco = endereco;
		this.tipo = tipo.getCode();
		this.nome = nome;
		this.email = email;
	}

}
