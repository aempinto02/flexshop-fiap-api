package br.com.buzz.dto;

import javax.validation.constraints.NotNull;

public class ItemDTO {
	
	@NotNull
	private Long produtoId;
	
	@NotNull
	private short unidades;
	
	public ItemDTO() {};
	
	public ItemDTO(Long produtoId, short unidades) {
		this.produtoId = produtoId;
		this.unidades = unidades;
	}

	public Long getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Long produtoId) {
		this.produtoId = produtoId;
	}

	public short getUnidades() {
		return unidades;
	}

	public void setUnidades(short unidades) {
		this.unidades = unidades;
	}
}
