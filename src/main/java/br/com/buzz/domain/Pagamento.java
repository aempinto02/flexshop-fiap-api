package br.com.buzz.domain;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import br.com.buzz.domain.enums.TipoPagamento;

@Entity
public class Pagamento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private boolean recebido;
	
	@NotNull
	@OneToOne
	private Conta contaComprador;
	
	@NotNull
	@OneToOne
	private Conta contaVendedor;
	
	@NotNull
	private Integer tipo;
	
	@NotNull
	private BigDecimal valor;
	
	@NotNull
	@OneToMany
	private List<Item> itens;
	
	public Pagamento() {}
	
	public Pagamento(Long id, @NotNull Conta contaComprador, @NotNull Conta contaVendedor, @NotNull TipoPagamento tipo,
			@NotNull BigDecimal valor, @NotNull List<Item> itens) {
		super();
		this.id = id;
		this.recebido = false;
		this.contaComprador = contaComprador;
		this.contaVendedor = contaVendedor;
		this.tipo = tipo.getCode();
		this.valor = valor;
		this.itens = itens;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Conta getContaComprador() {
		return contaComprador;
	}

	public void setContaComprador(Conta contaComprador) {
		this.contaComprador = contaComprador;
	}

	public Conta getContaVendedor() {
		return contaVendedor;
	}

	public void setContaVendedor(Conta contaVendedor) {
		this.contaVendedor = contaVendedor;
	}

	public TipoPagamento getTipo() {
		return TipoPagamento.toEnum(tipo);
	}

	public void setTipo(TipoPagamento tipo) {
		this.tipo = tipo.getCode();
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public boolean isRecebido() {
		return recebido;
	}

	public void setRecebido(boolean recebido) {
		this.recebido = recebido;
	}

	public List<Item> getItens() {
		return itens;
	}

	public void setItens(List<Item> itens) {
		this.itens = itens;
	}
	
}
