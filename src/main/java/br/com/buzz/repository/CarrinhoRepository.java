package br.com.buzz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.buzz.domain.Carrinho;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {

	Carrinho findByContaId(Long contaId);
}
