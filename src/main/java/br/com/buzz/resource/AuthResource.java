package br.com.buzz.resource;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.buzz.domain.Auth;
import br.com.buzz.dto.AuthDTO;
import br.com.buzz.dto.RecoverPasswordDTO;
import br.com.buzz.service.AuthService;

@RestController
@RequestMapping(value = "auth")
public class AuthResource {
	
	@Autowired
	private AuthService service;
	
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public ResponseEntity<Auth> find(@PathVariable("id") Long id){
		Auth obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody AuthDTO authDTO){
		Auth auth = service.fromDTO(authDTO);
		auth = service.insert(auth);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(auth.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Auth> update(@Valid @RequestBody AuthDTO objDTO, @PathVariable Long id){
		Auth auth = service.fromDTO(objDTO);
		auth.setId(id);
		auth = service.update(auth);
		return ResponseEntity.ok().body(auth);
	}
	
	@RequestMapping(value = "/recover-password", method=RequestMethod.PUT)
	public ResponseEntity<Void> recoverPassword(@RequestParam("key") String key,
			@Valid @RequestBody RecoverPasswordDTO dto) {
		service.recoverPassword(key, dto.getNewPassword());
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/forgot-password", method=RequestMethod.GET)
	public ResponseEntity<Void> forgot(@RequestParam("email") String email) {
		service.forgotPassword(email);
		return ResponseEntity.noContent().build();
	}
}
