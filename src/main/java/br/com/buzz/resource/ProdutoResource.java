package br.com.buzz.resource;

import java.util.List;

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

import br.com.buzz.domain.Image;
import br.com.buzz.domain.Produto;
import br.com.buzz.dto.ProdutoInsertDTO;
import br.com.buzz.dto.ProdutoUpdateDTO;
import br.com.buzz.service.ProdutoService;

@RestController
@RequestMapping("/produto")
public class ProdutoResource {
	
	@Autowired
	ProdutoService produtoService;

	@GetMapping
	public ResponseEntity<List<Produto>> getProdutos() {
		return ResponseEntity.ok(produtoService.findAll());
	}

	@PreAuthorize("hasRole('ROLE_SELLER')")
	@GetMapping("/conta")
	public ResponseEntity<List<Produto>> getProdutosByConta() {
		return ResponseEntity.ok(produtoService.findByConta());
	}
	
	@GetMapping("/nome")
	public ResponseEntity<Produto> getProdutosByTitulo(@RequestParam("titulo") String titulo) {
		return ResponseEntity.ok(produtoService.findByTitulo(titulo));
	}
	
	@GetMapping("/catalogo/{id}")
	public ResponseEntity<List<Produto>> getProdutosByCatalogo(@PathVariable Long id) {
		return ResponseEntity.ok(produtoService.findByCatalogo(id));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Produto> getProduto(@PathVariable Long id) {
		return ResponseEntity.ok(produtoService.findById(id));
	}
	
	@PreAuthorize("hasRole('ROLE_SELLER')")
	@PostMapping("/{id}")
	public ResponseEntity<Produto> insertProdutoInCatalogo(@PathVariable Long id, @Valid @RequestBody ProdutoInsertDTO produtoDto) {
		Produto produtoInserido = produtoService.insertProdutoInCatalogo(id, produtoDto);
		return ResponseEntity.ok().body(produtoInserido);
	}
	
	@PreAuthorize("hasRole('ROLE_SELLER')")
	@PutMapping("/{id}")
	public ResponseEntity<Produto> updateProduto(@PathVariable Long id, @Valid @RequestBody ProdutoUpdateDTO produtoDto) {
		Produto produtoNovo = produtoService.updateProduto(id, produtoDto);
		return ResponseEntity.ok().body(produtoNovo);
	}
	
	@PreAuthorize("hasRole('ROLE_SELLER')")
	@PutMapping("/{id}/foto")
	public ResponseEntity<Produto> updateFotoProduto(@PathVariable Long id, @RequestBody Image foto) {
		Produto produtoNovo = produtoService.updateFotoProduto(id, foto);
		return ResponseEntity.ok().body(produtoNovo);
	}

	@PreAuthorize("hasRole('ROLE_SELLER')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
		produtoService.deleteProduto(id);
		return ResponseEntity.accepted().build();
	}
}
