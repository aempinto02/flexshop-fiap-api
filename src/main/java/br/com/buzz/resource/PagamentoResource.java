package br.com.buzz.resource;

import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.buzz.domain.Conta;
import br.com.buzz.domain.Pagamento;
import br.com.buzz.service.PagamentoService;

@RestController
@RequestMapping("/pagamento")
public class PagamentoResource {
	
	@Autowired
	PagamentoService pagamentoService;

	@GetMapping
	@ResponseBody
	public List<Pagamento> getPagamentos() {
		return pagamentoService.findByConta();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Pagamento> getPagamento(@PathVariable Long id) {
		return ResponseEntity.ok(pagamentoService.findById(id));
	}
	
	@PreAuthorize("hasRole('ROLE_COMMON')")
	@GetMapping("/vendedor")
	@ResponseBody
	public List<Pagamento> getPagamentosByVendedor(@RequestBody Conta conta) {
		return pagamentoService.findByContaVendedor(conta);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/pagamentos")
	@ResponseBody
	public List<Pagamento> getPagamentosAll() {
		return pagamentoService.findAll();
	}

	@PreAuthorize("hasRole('ROLE_COMMON')")
	@PostMapping("/{tipoPagamento}")
	public ResponseEntity<Pagamento> insertPagamento(@PathVariable Integer tipoPagamento) {
		Pagamento pagamentoInserido = pagamentoService.insertPagamento(tipoPagamento);
		return ResponseEntity.ok().body(pagamentoInserido);
	}
	
	@PreAuthorize("hasRole('ROLE_SELLER')")
	@PutMapping("/recebido/{id}")
	public ResponseEntity<Pagamento> updatePagamento(@PathVariable Long id) {
		return ResponseEntity.ok(pagamentoService.changePagamento(id));
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePagamento(@PathVariable Long id) {
		pagamentoService.deletePagamento(id);
		return ResponseEntity.accepted().build();
	}
}
