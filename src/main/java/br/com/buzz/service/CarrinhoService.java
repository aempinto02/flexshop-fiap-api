package br.com.buzz.service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.buzz.domain.Carrinho;
import br.com.buzz.domain.Conta;
import br.com.buzz.domain.Item;
import br.com.buzz.domain.Produto;
import br.com.buzz.dto.ItemDTO;
import br.com.buzz.exception.DataIntegrityException;
import br.com.buzz.exception.ObjectNotFoundException;
import br.com.buzz.repository.CarrinhoRepository;
import br.com.buzz.repository.ContaRepository;
import br.com.buzz.repository.ItemRepository;
import br.com.buzz.repository.ProdutoRepository;
import br.com.buzz.security.UserSS;

@Service
public class CarrinhoService {

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private CarrinhoRepository carrinhoRepository;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	public Carrinho findByConta() {
		UserSS user = UserService.authenticated();
		Conta conta = contaRepository.findByEmail(user.getUsername()).get();
		Carrinho carrinho = carrinhoRepository.findByContaId(conta.getId());
		return carrinho == null ? new Carrinho(conta.getId(), new BigDecimal("0.0")) : carrinho;
	}
	
	public BigDecimal valorTotal() {
		Carrinho carrinho = findByConta();
		if(carrinho.getItens().isEmpty()) return new BigDecimal("0.0");
		
		return carrinho.getValorTotal();
	}

	public Carrinho insertItemCarrinho(ItemDTO itemDto) {
		if(itemDto.getUnidades() < 0) throw new DataIntegrityException("Unidades de produto têm que ser positivas!");
		Carrinho carrinho = findByConta();

		Optional<Produto> optProduto = produtoRepository.findById(itemDto.getProdutoId());
		optProduto.orElseThrow(() -> new ObjectNotFoundException("Produto não existe na base de dados!"));
		Produto produto = optProduto.get();
		
		boolean temItemExcluir = false;
		short unidadesExcluir = 0;
		for(Item itemExcluir : carrinho.getItens()) {
			if(itemExcluir.getProduto().getId() == produto.getId()) {
				temItemExcluir = true;
				unidadesExcluir = itemExcluir.getUnidades();
			}
		}
		
		if(itemDto.getUnidades() == 0) {
			if(!temItemExcluir) throw new ObjectNotFoundException("Não há esse produto no carrinho para ser excluído!");
			carrinho.setValorTotal(carrinho.getValorTotal().subtract(produto.getPreco().multiply(new BigDecimal(unidadesExcluir))));
			Predicate<Item> predicate = (Item itemRemove) -> (itemRemove.getProduto().getId() == produto.getId());
			carrinho.getItens().removeIf(predicate);
			return carrinhoRepository.save(carrinho);
		}
		
		boolean existeItem = false;
		for(Item itemSearch : carrinho.getItens()) {
			if(itemSearch.getProduto().getId() == produto.getId()) {
				if(itemSearch.getUnidades() > itemDto.getUnidades()) {
					int diferenca = itemSearch.getUnidades() - itemDto.getUnidades();
					BigDecimal valorDiferenca = new BigDecimal(produto.getPreco().multiply(new BigDecimal(diferenca)).toString());
					carrinho.setValorTotal(carrinho.getValorTotal().subtract(valorDiferenca));
				} else if(itemSearch.getUnidades() < itemDto.getUnidades()) {
					int diferenca = itemDto.getUnidades() - itemSearch.getUnidades();
					BigDecimal valorDiferenca = new BigDecimal(produto.getPreco().multiply(new BigDecimal(diferenca)).toString());
					carrinho.setValorTotal(carrinho.getValorTotal().add(valorDiferenca));
				}
				itemSearch.setUnidades(itemDto.getUnidades());
				existeItem = true;
			}
		}

		Item item = null;
		if(!existeItem) {
			item = itemRepository.save(new Item(null, produto, itemDto.getUnidades()));
			carrinho.getItens().add(item);
			carrinho.setValorTotal(carrinho.getValorTotal().add(produto.getPreco().multiply(new BigDecimal(itemDto.getUnidades()))));
		}
		if(item == null) {
			item = new Item(itemDto.getProdutoId(), produto, itemDto.getUnidades());
			carrinho.setValorTotal(carrinho.getValorTotal().add(produto.getPreco().multiply(new BigDecimal(itemDto.getUnidades()))));
		}
		
		System.out.println("ITEM:"+item);
		
		return carrinhoRepository.save(carrinho);
	}
	
	public Carrinho insertNovoCarrinho() {
		UserSS user = UserService.authenticated();
		Carrinho carrinhoNovo = new Carrinho(user.getId(), new BigDecimal(0));
		
		return carrinhoRepository.save(carrinhoNovo);
	}

	public Carrinho deleteItemCarrinho(Long id) {
		UserSS user = UserService.authenticated();
		Optional<Item> optItem = itemRepository.findById(id);
		optItem.orElseThrow(() -> new DataIntegrityException("Não há item com esse id!"));
		Item item = optItem.get();
		Carrinho carrinho = carrinhoRepository.findByContaId(user.getId());
		if(carrinho.getItens().contains(item)) {
			carrinho.getItens().remove(item);
			carrinho = carrinhoRepository.save(carrinho);
		}
		return carrinho;
	}
}
