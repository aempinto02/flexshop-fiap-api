package br.com.buzz.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.buzz.domain.Carrinho;
import br.com.buzz.domain.Catalogo;
import br.com.buzz.domain.Conta;
import br.com.buzz.domain.Item;
import br.com.buzz.domain.Pagamento;
import br.com.buzz.domain.Produto;
import br.com.buzz.domain.enums.TipoPagamento;
import br.com.buzz.domain.enums.UserRole;
import br.com.buzz.exception.AuthorizationException;
import br.com.buzz.exception.DataIntegrityException;
import br.com.buzz.exception.ObjectNotFoundException;
import br.com.buzz.repository.CarrinhoRepository;
import br.com.buzz.repository.ContaRepository;
import br.com.buzz.repository.PagamentoRepository;
import br.com.buzz.repository.ProdutoRepository;
import br.com.buzz.security.UserSS;

@Service
public class PagamentoService {

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private CarrinhoRepository carrinhoRepository;

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private SendGridMailService sgService;

	public List<Pagamento> findByConta() {
		UserSS user = UserService.authenticated();
		Conta conta = contaRepository.findByEmail(user.getUsername()).get();
		List<Pagamento> pagamentos = null;
		if(conta.getTipo().equals(UserRole.SELLER)) {
			pagamentos = pagamentoRepository.findByContaVendedor(conta);
		} else if(conta.getTipo().equals(UserRole.COMMON)) {
			pagamentos = pagamentoRepository.findByContaComprador(conta);
		}

		return pagamentos;
	}

	public Pagamento findById(Long id) {
		UserSS user = UserService.authenticated();
		Optional<Pagamento> optPagamento = pagamentoRepository.findById(id);
		optPagamento.orElseThrow(() -> new ObjectNotFoundException("Pagamento não encontrado! Id: " + id));
		Pagamento pagamento = optPagamento.get();

		Conta conta = contaRepository.findById(user.getId()).get();

		boolean contaAssociada = false;
		if(conta.getTipo().equals(UserRole.COMMON)) {
			List<Pagamento> pagamentosComprador = pagamentoRepository.findByContaComprador(conta);
			for(Pagamento pagamentoBusca : pagamentosComprador) {
				if(pagamentoBusca.getId() == id) contaAssociada = true;
			}
		} else if(conta.getTipo().equals(UserRole.SELLER)) {
			List<Pagamento> pagamentosVendedor = pagamentoRepository.findByContaVendedor(conta);
			for(Pagamento pagamentoBusca : pagamentosVendedor) {
				if(pagamentoBusca.getId() == id) contaAssociada = true;
			}
		}
		
		if(!contaAssociada) throw new AuthorizationException("O pagamento não pertence à sua conta!");
		
		return pagamento;
	}

	public List<Pagamento> findByContaVendedor(Conta contaVendedor) {
		UserSS user = UserService.authenticated();
		if(contaVendedor.getId() == null) throw new DataIntegrityException("Id do vendedor necessário!");
		Long idVendedor = contaVendedor.getId();
		Conta contaComprador = contaRepository.findById(user.getId()).get();
		Optional<Conta> optConta = contaRepository.findById(idVendedor);
		optConta.orElseThrow(() -> new ObjectNotFoundException("Conta de vendedor não encontrada! Id: " + idVendedor));
		contaVendedor = optConta.get();

		if(!contaVendedor.getTipo().equals(UserRole.SELLER)) throw new AuthorizationException("A conta informada não é de vendedor!");

		return pagamentoRepository.findByContaCompradorAndContaVendedor(contaComprador, contaVendedor);
	}

	public List<Pagamento> findAll() {
		return pagamentoRepository.findAll();
	}

	public Pagamento insertPagamento(Integer tipoPagamento) {
		UserSS user = UserService.authenticated();
		Conta contaComprador = contaRepository.findById(user.getId()).get();

		Optional<Carrinho> optCarrinho = carrinhoRepository.findById(contaComprador.getId());
		optCarrinho.orElseThrow(() -> new ObjectNotFoundException("Carrinho não existente!"));
		Carrinho carrinho = optCarrinho.get();
		if(carrinho.getItens().isEmpty()) throw new DataIntegrityException("O carrinho está vazio para formar o pagamento!");

		Item item = carrinho.getItens().get(0);
		Optional<Produto> optProduto = produtoRepository.findById(item.getProduto().getId());
		optProduto.orElseThrow(() -> new ObjectNotFoundException("Produto não existente! Id: " + item.getProduto().getId()));
		Conta contaVendedor = optProduto.get().getCatalogo().getConta();

		List<Item> itens = List.copyOf(carrinho.getItens());
		Pagamento pagamento = new Pagamento(null, contaComprador, contaVendedor, TipoPagamento.toEnum(tipoPagamento), carrinho.getValorTotal(), itens);

		carrinho = new Carrinho(contaComprador.getId(), new BigDecimal("0.0"));
		carrinhoRepository.save(carrinho);
		Catalogo catalogo = optProduto.get().getCatalogo();

		pagamento = pagamentoRepository.save(pagamento);
		sgService.sendMailPagamentoComprador(pagamento, contaComprador, contaVendedor, catalogo);
		sgService.sendMailPagamentoVendedor(pagamento, contaComprador, contaVendedor, catalogo);
		
		return pagamento;
	}
	
	public Pagamento changePagamento(Long id) {
		Optional<Pagamento> optPagamento = pagamentoRepository.findById(id);
		optPagamento.orElseThrow(() -> new ObjectNotFoundException("Pagamento não existente! Id: " + id));
		Pagamento pagamento = optPagamento.get();
		if(pagamento.isRecebido()) throw new DataIntegrityException("Pagamento já recebido!");
		pagamento.setRecebido(true);
		return pagamentoRepository.save(pagamento);
	}

	public void deletePagamento(Long id) {
		Optional<Pagamento> optPagamento = pagamentoRepository.findById(id);
		optPagamento.orElseThrow(() -> new ObjectNotFoundException("Pagamento não encontrado! Id: " + id));

		pagamentoRepository.deleteById(id);
	}
}
