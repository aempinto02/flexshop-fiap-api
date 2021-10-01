package br.com.buzz.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Carrinho implements Serializable {
	
	private static final long serialVersionUID = 8653710194615870609L;

	@Id
	private Long contaId;

	@NotNull
	private BigDecimal valorTotal = new BigDecimal("0.0");
	
	@NotNull
	@OneToMany
	private List<Item> itens = new ArrayList<Item>();
	
	public Carrinho() {}

	public Carrinho(@NotNull Long contaId, @NotNull BigDecimal valorTotal) {
		super();
		this.contaId = contaId;
		this.valorTotal = valorTotal;
		this.itens = new ArrayList<Item>();
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public List<Item> getItens() {
		return itens;
	}

	public void setItens(List<Item> itens) {
		this.itens = itens;
	}

	public Long getContaId() {
		return contaId;
	}

	public void setContaId(Long contaId) {
		this.contaId = contaId;
	}
	
}
