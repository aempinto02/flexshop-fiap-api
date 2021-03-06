package br.com.buzz.security;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.buzz.domain.enums.UserRole;

public class UserSS implements UserDetails {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;

	public UserSS() {
		super();
	}

	public UserSS(Long id, String username, String password, UserRole role) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.authorities = Arrays.asList(new SimpleGrantedAuthority(role.getDescription()));
	}

	public Long getId() {
		return id;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public boolean hasRole(UserRole role) {
		return getAuthorities().contains(new SimpleGrantedAuthority(role.getDescription()));
	}
}
