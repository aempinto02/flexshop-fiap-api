package br.com.buzz.domain.enums;

public enum TipoPagamento {

	DINHEIRO(1, "Dinheiro"),
	CREDITO(2, "Cartão de Crédito"),
	DEBITO(3, "Cartão de Débito"),
	PIX(4, "PIX");

	private TipoPagamento(Integer code, String description) {
		this.code = code;
		this.description = description;
	}

	private Integer code;
	private String description;

	public Integer getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public static TipoPagamento toEnum(Integer code) {
		if (code == null) {
			return null;
		}

		for (TipoPagamento x : TipoPagamento.values()) {
			if (code.equals(x.getCode())) {
				return x;
			}
		}

		throw new IllegalArgumentException("Id inválido: " + code);
	}
}