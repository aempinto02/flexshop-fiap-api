package br.com.buzz.resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.buzz.dto.AuthDTO;

@RestController
public class LoginResource {
	@PostMapping("/login")
	public void fakeLogin(@RequestBody AuthDTO auth) {
		throw new IllegalStateException(
				"Este método não deve ser chamado. Ele é implementado pelo filtro do Spring Security.");
	}

	@PostMapping("/logout")
	public void fakeLogout() {
		throw new IllegalStateException(
				"Este método não deve ser chamado. Ele é implementado pelo filtro do Spring Security.");
	}
}
