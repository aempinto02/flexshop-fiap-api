package br.com.buzz.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.buzz.domain.Catalogo;
import br.com.buzz.domain.Conta;
import br.com.buzz.domain.Image;
import br.com.buzz.dto.CatalogoInsertDTO;
import br.com.buzz.dto.CatalogoUpdateDTO;
import br.com.buzz.exception.AuthorizationException;
import br.com.buzz.exception.ObjectNotFoundException;
import br.com.buzz.repository.CatalogoRepository;
import br.com.buzz.repository.ContaRepository;
import br.com.buzz.repository.ImageRepository;
import br.com.buzz.security.UserSS;

@Service
public class CatalogoService {

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private CatalogoRepository catalogoRepository;
	
	@Autowired
	private ImageRepository imageRepository;

	public List<Catalogo> findByConta() {
		UserSS user = UserService.authenticated();
		Conta conta = contaRepository.findByEmail(user.getUsername()).get();
		return catalogoRepository.findByConta(conta);
	}

	public List<Catalogo> findAll() {
		return catalogoRepository.findAll();
	}

	public Catalogo insertCatalogo(CatalogoInsertDTO catalogoDto) {
		UserSS user = UserService.authenticated();
		Optional<Catalogo> optCatalogo = catalogoRepository.findByNome(catalogoDto.getNome());
		if (optCatalogo.isPresent()) throw new ObjectNotFoundException("Nome já existente de catálogo!");
		
		Image image = new Image(null, catalogoDto.getImageUrl());
		Image fotoSalvar = imageRepository.save(image);
		Conta conta = contaRepository.findById(user.getId()).get();
		Catalogo catalogo = new Catalogo(null, catalogoDto.getNome(),fotoSalvar, catalogoDto.getHorario(), conta, catalogoDto.getDescricao());
		catalogo.setQuantidadeProdutos(0);
		
		catalogoRepository.save(catalogo);
		conta.getCatalogos().add(catalogo);
		contaRepository.save(conta);

		return catalogoRepository.save(catalogo);
	}

	public Catalogo updateCatalogo(Long id, CatalogoUpdateDTO catalogoDto) {
		UserSS user = UserService.authenticated();
		Optional<Catalogo> optCatalogo = catalogoRepository.findById(id);
		optCatalogo.orElseThrow(() -> new ObjectNotFoundException("Catálogo não encontrado! Id: " + id));
		Catalogo catalogo = optCatalogo.get();

		if(!user.getUsername().equals(catalogo.getConta().getEmail()))
			throw new AuthorizationException("Esse catálogo não pertence à sua conta!");

		if(!catalogoDto.getNome().isBlank())
			catalogo.setNome(catalogoDto.getNome().trim());
		
		if(!catalogoDto.getHorario().isBlank())
			catalogo.setHorario(catalogoDto.getHorario().trim());
		
		if(!catalogoDto.getDescricao().isBlank())
				catalogo.setDescricao(catalogoDto.getDescricao().trim());
		
		return catalogoRepository.save(catalogo);
	}
	
	public Catalogo updateFotoCatalogo(Long id, Image foto) {
		UserSS user = UserService.authenticated();
		
		Optional<Catalogo> optCatalogo = catalogoRepository.findById(id);
		optCatalogo.orElseThrow(() -> new ObjectNotFoundException("Catálogo não encontrado! Id: " + id));
		Catalogo catalogo = optCatalogo.get();
		
		if(!user.getUsername().equals(catalogo.getConta().getEmail()))
			throw new AuthorizationException("Esse catálogo não pertence à sua conta!");
		
		Long idFoto = catalogo.getFoto().getId();
		Image fotoCatalogo = imageRepository.findById(idFoto).get();
		fotoCatalogo.setUrl(foto.getUrl());
		
		catalogo.setFoto(imageRepository.save(fotoCatalogo));
		
		return catalogoRepository.save(catalogo);
	}
	
	public void deleteCatalogo(Long id) {
		UserSS user = UserService.authenticated();
		Optional<Catalogo> optCatalogo = catalogoRepository.findById(id);
		optCatalogo.orElseThrow(() -> new ObjectNotFoundException("Catálogo não encontrado! Id: " + id));
		Catalogo catalogo = optCatalogo.get();
		
		if(!user.getUsername().equals(catalogo.getConta().getEmail()))
			throw new AuthorizationException("Esse catálogo não pertence à sua conta!");
		
		Conta conta = catalogo.getConta();
		conta.getCatalogos().remove(catalogo);
		contaRepository.save(conta);
		catalogo.setConta(null);
		
		catalogoRepository.deleteById(id);
	}
}
