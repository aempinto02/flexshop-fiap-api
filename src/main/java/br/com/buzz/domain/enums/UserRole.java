package br.com.buzz.domain.enums;

public enum UserRole {

	COMMON(1, "ROLE_COMMON"),
	SELLER(2, "ROLE_SELLER"),
	ADMIN(3, "ROLE_ADMIN");

	private UserRole(Integer code, String description) {
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

	public static UserRole toEnum(Integer code) {
		if (code == null) {
			return null;
		}

		for (UserRole x : UserRole.values()) {
			if (code.equals(x.getCode())) {
				return x;
			}
		}

		throw new IllegalArgumentException("Id inválido: " + code);
	}
}