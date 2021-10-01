package br.com.buzz.domain;

import java.util.List;
import java.util.Objects;

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

@Entity
public class Conta {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
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
	
	public Conta() {}
	
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

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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
	
	public UserRole getTipo() {
		return UserRole.toEnum(tipo);
	}
	
	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public void setTipo(UserRole tipo) {
		this.tipo = tipo.getCode();
	}

	public List<Catalogo> getCatalogos() {
		return catalogos;
	}
	
	public void setCatalogos(List<Catalogo> catalogos) {
		this.catalogos = catalogos;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Conta other = (Conta) obj;
		return Objects.equals(email, other.email) && Objects.equals(id, other.id);
	}
	
}
