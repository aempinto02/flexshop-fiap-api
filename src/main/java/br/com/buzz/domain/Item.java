package br.com.buzz.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Item implements Serializable {

	private static final long serialVersionUID = 6120526242455645790L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@NotNull
	@OneToOne
	private Produto produto;
	
	@NotNull
	private short unidades = 0;
	
	public Item() {}

	public Item(Long id, Produto produto, @NotNull short unidades) {
		super();
		this.id = id;
		this.produto = produto;
		this.unidades = unidades;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	};
	
	public short getUnidades() {
		return unidades;
	}

	public void setUnidades(short unidades) {
		this.unidades = unidades;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	};
	
	@Override
	public String toString() {
		return "Item [id=" + id + ", produto=" + produto + ", unidades=" + unidades + "]";
	}
	
}
