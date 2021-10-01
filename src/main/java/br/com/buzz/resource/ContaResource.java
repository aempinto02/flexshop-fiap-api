package br.com.buzz.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.buzz.domain.Conta;
import br.com.buzz.dto.ContaInsertDTO;
import br.com.buzz.dto.ContaUpdateDTO;
import br.com.buzz.service.ContaService;

@RestController
@RequestMapping("/conta")
public class ContaResource {
	
	@Autowired
	ContaService contaService;

	@GetMapping
	public ResponseEntity<Conta> getConta() {
		return ResponseEntity.ok(contaService.find());
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<Conta> getContaById(@PathVariable Long id) {
		return ResponseEntity.ok(contaService.findById(id));
	}
	
	@PostMapping
	public ResponseEntity<Conta> insertConta(@Valid @RequestBody ContaInsertDTO contaDto) {
		Conta contaInserida = contaService.insertConta(contaDto);
		return ResponseEntity.ok().body(contaInserida);
	}
	
	@PreAuthorize("!hasRole('ROLE_ADMIN')")
	@PutMapping
	public ResponseEntity<Conta> updateConta(@Valid @RequestBody ContaUpdateDTO contaDto) {
		Conta contaNova = contaService.updateConta(contaDto);
		return ResponseEntity.ok().body(contaNova);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Conta> updateTipoConta(@PathVariable Long id, @RequestParam Integer tipo) {
		Conta contaNova = contaService.updateTipoConta(id, tipo);
		return ResponseEntity.ok().body(contaNova);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteConta(@PathVariable Long id) {
		contaService.deleteConta(id);
		return ResponseEntity.accepted().build();
	}
}
