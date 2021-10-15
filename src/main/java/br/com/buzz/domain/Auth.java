package br.com.buzz.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import br.com.buzz.domain.enums.UserRole;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
public class Auth implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@EqualsAndHashCode.Include
	private Long id;

	@Setter(AccessLevel.NONE)
	@Getter(AccessLevel.NONE)
	private Integer role;

	@Column(unique = true)
	private String username;

	private String password;

	public UserRole getRole() {
		return UserRole.toEnum(this.role);
	}

	public void setRole(UserRole role) {
		this.role = role.getCode();
	}
	
	public Auth(Long id, UserRole role, String username, String password) {
		this.id = id;
		this.role = role.getCode();
		this.username = username;
		this.password = password;
	}

}
