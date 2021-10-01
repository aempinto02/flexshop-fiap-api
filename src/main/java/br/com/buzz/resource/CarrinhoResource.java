package br.com.buzz.resource;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.buzz.domain.Carrinho;
import br.com.buzz.dto.ItemDTO;
import br.com.buzz.service.CarrinhoService;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoResource {
	
	@Autowired
	CarrinhoService carrinhoService;

	@PreAuthorize("hasRole('ROLE_COMMON')")
	@GetMapping
	public ResponseEntity<Carrinho> getCarrinho() {
		return ResponseEntity.ok(carrinhoService.findByConta());
	}
	
	@PreAuthorize("hasRole('ROLE_COMMON')")
	@GetMapping("/valorTotal")
	public ResponseEntity<BigDecimal> getValorTotal() {
		return ResponseEntity.ok(carrinhoService.valorTotal());
	}
	
	@PreAuthorize("hasRole('ROLE_COMMON')")
	@PostMapping
	public ResponseEntity<Carrinho> insertItemCarrinho(@RequestBody ItemDTO itemDto) {
		return ResponseEntity.ok(carrinhoService.insertItemCarrinho(itemDto));
	}
	
	@PreAuthorize("hasRole('ROLE_COMMON')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Carrinho> deleteItemCarrinho(@PathVariable Long id) {
		return ResponseEntity.ok(carrinhoService.deleteItemCarrinho(id));
	}
	
	@PostMapping("/novo")
	public ResponseEntity<Carrinho> insertNovoCarrinho() {
		return ResponseEntity.ok(carrinhoService.insertNovoCarrinho());
	}
	
}
