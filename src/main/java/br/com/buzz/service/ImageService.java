package br.com.buzz.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.buzz.domain.Catalogo;
import br.com.buzz.domain.Conta;
import br.com.buzz.domain.Image;
import br.com.buzz.domain.Produto;
import br.com.buzz.domain.enums.UserRole;
import br.com.buzz.exception.AuthorizationException;
import br.com.buzz.exception.ObjectNotFoundException;
import br.com.buzz.repository.CatalogoRepository;
import br.com.buzz.repository.ContaRepository;
import br.com.buzz.repository.ImageRepository;
import br.com.buzz.security.UserSS;

@Service
public class ImageService {

	@Autowired
	private ImageRepository imageRepository;

	@Autowired
	private CatalogoRepository catalogoRepository;

	@Autowired
	private ContaRepository contaRepository;
	
	public Image getImage(Long id) {
		Optional<Image> optImage = imageRepository.findById(id);
		optImage.orElseThrow(() -> new ObjectNotFoundException("Imagem não encontrada! Id: " + id));
		
		return optImage.get();
	}

	public Image upload(Image image) {

		Image imageInserida = imageRepository.save(image);

		return imageInserida;
	}

	public Image update(Image image, Long id) {
		Optional<Image> optImage = imageRepository.findById(id);
		optImage.orElseThrow(() -> new ObjectNotFoundException("Imagem não encontrada! Id: " + id));
		Image imageUpdate = optImage.get();
		imageUpdate.setUrl(image.getUrl());

		UserSS user = UserService.authenticated();
		Conta conta = contaRepository.findById(user.getId()).get();

		if(conta.getTipo().equals(UserRole.COMMON)) throw new AuthorizationException("Você não tem permissão para alterar fotos!");

		if(conta.getTipo().equals(UserRole.ADMIN)) imageUpdate = imageRepository.save(imageUpdate);

		if(conta.getTipo().equals(UserRole.SELLER)) {

			List<Catalogo> catalogos = catalogoRepository.findByConta(conta);

			boolean fotoPertenceConta = false;
			for(Catalogo catalogo : catalogos) {
				if(catalogo.getFoto().getId() == id) {
					imageUpdate = imageRepository.save(imageUpdate);
					fotoPertenceConta = true;
				}

				for(Produto produto : catalogo.getProdutos()) {
					if(produto.getFoto().getId() == id) {
						imageUpdate = imageRepository.save(imageUpdate);
						fotoPertenceConta = true;
					}
				}
			}

			if(!fotoPertenceConta) throw new AuthorizationException("A foto não pertence a nenhum catálogo ou produto vinculado à sua conta!");
		}
		
		return imageUpdate;
	}

	public void deleteImage(Long id) {
		Optional<Image> optImage = imageRepository.findById(id);
		optImage.orElseThrow(() -> new ObjectNotFoundException("Imagem não encontrada! Id: " + id));
		Image image = optImage.get();

		UserSS user = UserService.authenticated();
		Conta conta = contaRepository.findById(user.getId()).get();

		if(conta.getTipo().equals(UserRole.COMMON)) throw new AuthorizationException("Você não tem permissão para deletar fotos!");

		if(conta.getTipo().equals(UserRole.ADMIN)) imageRepository.delete(optImage.get());

		if(conta.getTipo().equals(UserRole.SELLER)) {

			List<Catalogo> catalogos = catalogoRepository.findByConta(conta);

			boolean fotoPertenceConta = false;
			for(Catalogo catalogo : catalogos) {
				if(catalogo.getFoto().equals(image)) {
					imageRepository.delete(optImage.get());
					fotoPertenceConta = true;
				}

				for(Produto produto : catalogo.getProdutos()) {
					if(produto.getFoto().equals(image)) {
						imageRepository.delete(optImage.get());
						fotoPertenceConta = true;
					}
				}
			}

			if(!fotoPertenceConta) throw new AuthorizationException("A foto não pertence a nenhum catálogo ou produto vinculado à sua conta!");
		}
	}
}
