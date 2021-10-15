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
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
public class Pagamento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
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
	@Setter(AccessLevel.NONE)
	@Getter(AccessLevel.NONE)
	private Integer tipo;
	
	@NotNull
	private BigDecimal valor;
	
	@NotNull
	@OneToMany
	private List<Item> itens;
	
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
	
	public TipoPagamento getTipo() {
		return TipoPagamento.toEnum(tipo);
	}

	public void setTipo(TipoPagamento tipo) {
		this.tipo = tipo.getCode();
	}
	
}
