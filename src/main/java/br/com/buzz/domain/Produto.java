package br.com.buzz.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.buzz.dto.ProdutoInsertDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
public class Produto implements Serializable {
	
	private static final long serialVersionUID = -3609453336991152023L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@EqualsAndHashCode.Include
	private Long id;
	
	@NotNull
	@Size(min=3, message="O produto precisa de um título de 3 caracteres no mínimo!")
	private String titulo;
	
	@NotNull
	@Size(min=6, message="O título precisa de ao menos 6 caracteres")
	private String descricao;
	
	@NotNull
	private BigDecimal preco;
	
	@NotNull
	@ManyToOne
	@JsonIgnore
	private Catalogo catalogo;
	
	@NotNull
	@OneToOne
	private Image foto;
	
	public Produto(Long id,
			@NotNull @Size(min = 3, message = "O produto precisa de um título de 3 caracteres no mínimo!") String titulo,
			@NotNull @Size(min = 6, message = "O título precisa de ao menos 6 caracteres") String descricao,
			@NotNull Catalogo catalogo,
			@NotNull BigDecimal preco,
			@NotNull Image foto) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
		this.catalogo = catalogo;
		this.preco = preco;
		this.foto = foto;
	}
	
	public Produto(ProdutoInsertDTO produtoDto) {
		this.titulo = produtoDto.getTitulo();
		this.descricao = produtoDto.getDescricao();
		this.preco = produtoDto.getPreco();
	}
}
