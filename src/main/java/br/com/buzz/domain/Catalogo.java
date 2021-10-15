package br.com.buzz.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
public class Catalogo implements Serializable {
	
	private static final long serialVersionUID = 4694567246728518464L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@NotNull
	@Column(unique=true)
	@Size(min=4, message="O nome do catálogo precisa ter no mínimo 4 caracteres")
	private String nome;
	
	@NotNull
	@OneToOne
	private Image foto;
	
	@NotNull
	@NotEmpty
	private String horario;
	
	@NotNull
	@Size(min=4, message="A descrição do catálogo precisa ter no mínimo 4 caracteres")
	private String descricao;
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.DETACH)
	private Conta conta;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Produto> produtos;
	
	@NotNull
	private Integer quantidadeProdutos;
	
	public Catalogo(Long id, @NotNull @Size(min = 4, message = "O nome do catálogo precisa ter no mínimo 4 caracteres") String nome,
			@NotNull Image foto, @NotNull @NotEmpty String horario, @NotNull Conta conta,
			@NotNull @Size(min = 4, message = "A descrição do catálogo precisa ter no mínimo 4 caracteres") String descricao) {
		super();
		this.id = id;
		this.nome = nome;
		this.foto = foto;
		this.horario = horario;
		this.conta = conta;
		this.produtos = new ArrayList<Produto>();
		this.descricao = descricao;
		this.quantidadeProdutos = 0;
	}
	
}
