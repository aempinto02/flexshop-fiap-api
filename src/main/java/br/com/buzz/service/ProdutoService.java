package br.com.buzz.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.buzz.domain.Catalogo;
import br.com.buzz.domain.Conta;
import br.com.buzz.domain.Image;
import br.com.buzz.domain.Produto;
import br.com.buzz.dto.ProdutoInsertDTO;
import br.com.buzz.dto.ProdutoUpdateDTO;
import br.com.buzz.exception.AuthorizationException;
import br.com.buzz.exception.ObjectNotFoundException;
import br.com.buzz.repository.CatalogoRepository;
import br.com.buzz.repository.ContaRepository;
import br.com.buzz.repository.ImageRepository;
import br.com.buzz.repository.ProdutoRepository;
import br.com.buzz.security.UserSS;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository repository;

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private CatalogoRepository catalogoRepository;
	
	@Autowired
	private ImageRepository imageRepository;

	public List<Produto> findAll() {
		return repository.findAll();
	}
	
	public Produto findById(Long id) {
		Optional<Produto> optProduto = repository.findById(id);
		optProduto.orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado! Id: " + id));
		return optProduto.get();
	}
	
	public List<Produto> findByCatalogo(Long id) {
		Optional<Catalogo> optCatalogo = catalogoRepository.findById(id);
		optCatalogo.orElseThrow(() -> new ObjectNotFoundException("Catálogo não encontrado! Id: " + id));
		return optCatalogo.get().getProdutos();
	}

	public List<Produto> findByConta() {
		UserSS user = UserService.authenticated();
		Conta conta = contaRepository.findByEmail(user.getUsername()).get();
		List<Produto> produtos = new ArrayList<Produto>();
		for(Catalogo catalogo : conta.getCatalogos()) {
			produtos.addAll(catalogo.getProdutos());
		}
		return produtos;
	}
	
	public Produto findByTitulo(String titulo) {
		Optional<Produto> optProduto = repository.findByTituloContains(titulo);
		optProduto.orElseThrow(() -> new ObjectNotFoundException("Não existe produto com '" + titulo + "' no nome"));
		return optProduto.get();
	}

	public Produto insertProdutoInCatalogo(Long id, ProdutoInsertDTO produtoDto) {
		UserSS user = UserService.authenticated();
		Optional<Catalogo> optCatalogo = catalogoRepository.findById(id);
		optCatalogo.orElseThrow(() -> new ObjectNotFoundException("Catálogo não encontrado! Id: " + id));
		Catalogo catalogo = optCatalogo.get();
		
		if(catalogo.getConta().getId() != user.getId())
			throw new AuthorizationException("Catálogo não pertence a essa conta!");
		
		
		Image fotoSalvar = new Image(null, produtoDto.getImageUrl());
		Produto produto = new Produto(produtoDto);
		produto.setFoto(fotoSalvar);
		
		produto.setCatalogo(catalogo);
		produto = repository.save(produto);
		
		catalogo.getProdutos().add(produto);
		catalogo.setQuantidadeProdutos(catalogo.getProdutos().size());
		catalogoRepository.save(catalogo);

		return produto;
	}

	public Produto updateProduto(Long id, ProdutoUpdateDTO produtoDto) {
		UserSS user = UserService.authenticated();
		Optional<Produto> optProduto = repository.findById(id);
		optProduto.orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado! Id: " + id));
		Produto produto = optProduto.get();
		
		Optional<Catalogo> optCatalogo = catalogoRepository.findByProdutos(produto);
		optCatalogo.orElseThrow(() -> new ObjectNotFoundException("Catálogo não encontrado! Id: " + id));
		Catalogo catalogo = optCatalogo.get();

		if(user.getId() != catalogo.getConta().getId())
			throw new AuthorizationException("Esse produto não pertence à sua conta!");

		if(!produtoDto.getTitulo().isBlank())
			produto.setTitulo(produtoDto.getTitulo().trim());
		
		if(!produtoDto.getDescricao().isBlank())
			produto.setDescricao(produtoDto.getDescricao().trim());
		
		produto.setPreco(produtoDto.getPreco());
		
		return repository.save(produto);
	}
	
	public void deleteProduto(Long id) {
		UserSS user = UserService.authenticated();
		Optional<Produto> optProduto = repository.findById(id);
		optProduto.orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado! Id: " + id));
		Produto produto = optProduto.get();
		
		Optional<Catalogo> optCatalogo = catalogoRepository.findByProdutos(produto);
		optCatalogo.orElseThrow(() -> new ObjectNotFoundException("Catálogo não encontrado! Id: " + id));
		Catalogo catalogo = optCatalogo.get();
		
		if(user.getId() != catalogo.getConta().getId())
			throw new AuthorizationException("Esse produto não pertence à sua conta!");
		
		catalogo.getProdutos().remove(produto);
		catalogo.setQuantidadeProdutos(catalogo.getProdutos().size());
		catalogoRepository.save(catalogo);
	}

	public Produto updateFotoProduto(Long id, Image foto) {
		UserSS user = UserService.authenticated();
		
		Optional<Produto> optProduto = repository.findById(id);
		optProduto.orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado! Id: " + id));
		Produto produto = optProduto.get();

		Optional<Catalogo> optCatalogo = catalogoRepository.findByProdutos(produto);
		optCatalogo.orElseThrow(() -> new ObjectNotFoundException("Catálogo não encontrado! Id: " + id));
		Catalogo catalogo = optCatalogo.get();

		if(user.getId() != catalogo.getConta().getId())
			throw new AuthorizationException("Esse produto não pertence à sua conta!");
		
		Image fotoProduto = imageRepository.findById(produto.getFoto().getId()).get();
		fotoProduto.setUrl(foto.getUrl());
		produto.setFoto(imageRepository.save(fotoProduto));
		
		return repository.save(produto);
	}
}
