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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.buzz.domain.Catalogo;
import br.com.buzz.domain.Image;
import br.com.buzz.dto.CatalogoInsertDTO;
import br.com.buzz.dto.CatalogoUpdateDTO;
import br.com.buzz.service.CatalogoService;

@RestController
@RequestMapping("/catalogo")
public class CatalogoResource {
	
	@Autowired
	CatalogoService catalogoService;

	@GetMapping
	@ResponseBody
	public List<Catalogo> getCatalogos() {
		return catalogoService.findAll();
	}
	
	@PreAuthorize("hasRole('ROLE_SELLER')")
	@GetMapping("/contaVendedor")
	@ResponseBody
	public List<Catalogo> getCatalogosContaSeller() {
		return catalogoService.findByConta();
	}
	
	@PreAuthorize("hasRole('ROLE_SELLER')")
	@PostMapping
	public ResponseEntity<Catalogo> insertCatalogo(@Valid @RequestBody CatalogoInsertDTO catalogoDto) {
		Catalogo catalogoInserido = catalogoService.insertCatalogo(catalogoDto);
		return ResponseEntity.ok().body(catalogoInserido);
	}
	
	@PreAuthorize("hasRole('ROLE_SELLER')")
	@PutMapping("/{id}")
	public ResponseEntity<Catalogo> updateCatalogo(@PathVariable Long id, @Valid @RequestBody CatalogoUpdateDTO catalogoDto) {
		Catalogo catalogoNovo = catalogoService.updateCatalogo(id, catalogoDto);
		return ResponseEntity.ok().body(catalogoNovo);
	}
	
	@PreAuthorize("hasRole('ROLE_SELLER')")
	@PutMapping("/{id}/foto")
	public ResponseEntity<Catalogo> updateFotoCatalogo(@PathVariable Long id, @RequestBody Image foto) {
		Catalogo catalogoNovo = catalogoService.updateFotoCatalogo(id, foto);
		return ResponseEntity.ok().body(catalogoNovo);
	}

	@PreAuthorize("hasRole('ROLE_SELLER')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCatalogo(@PathVariable Long id) {
		catalogoService.deleteCatalogo(id);
		return ResponseEntity.accepted().build();
	}
}
