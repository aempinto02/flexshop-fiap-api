package br.com.buzz.resource;

import java.io.IOException;

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
import org.springframework.web.bind.annotation.RestController;

import br.com.buzz.domain.Image;
import br.com.buzz.service.ImageService;

@RestController
@RequestMapping("/image")
public class ImageResource {
	
	@Autowired
	ImageService imageService;
	
	@GetMapping(path = { "/{id}" } )
	public ResponseEntity<Image> getImageById(@PathVariable("id") Long id) throws IOException {
		
		return ResponseEntity.ok(imageService.getImage(id));
	}
	
	@PreAuthorize("!hasRole('COMMON')") 
	@PostMapping
	public ResponseEntity<Image> uploadImage(@RequestBody Image image) throws IOException {
		
		image = imageService.upload(image);
		
		return ResponseEntity.ok(image);
	}

	@PreAuthorize("!hasRole('COMMON')") 
	@PutMapping("/{id}")
	public ResponseEntity<Image> updateImage(@PathVariable Long id, @RequestBody Image image) throws IOException {

		Image imageUpdated = imageService.update(image, id);

		return ResponseEntity.ok(imageUpdated);
	}
	
	@PreAuthorize("!hasRole('COMMON')") 
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
		
		imageService.deleteImage(id);
		return ResponseEntity.accepted().build();
	}
}
