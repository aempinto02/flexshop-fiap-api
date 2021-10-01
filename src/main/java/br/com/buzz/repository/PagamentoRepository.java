package br.com.buzz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.buzz.domain.Conta;
import br.com.buzz.domain.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
	
	List<Pagamento> findByContaComprador(Conta conta);
	List<Pagamento> findByContaVendedor(Conta conta);
	
	List<Pagamento> findByContaCompradorAndContaVendedor(Conta contaComprador, Conta contaVendedor);
}
