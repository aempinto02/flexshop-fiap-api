package br.com.buzz.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.buzz.domain.Catalogo;
import br.com.buzz.domain.Conta;
import br.com.buzz.domain.Produto;

public interface CatalogoRepository extends JpaRepository<Catalogo, Long> {
	
	public List<Catalogo> findByConta(Conta conta);

	public Optional<Catalogo> findByNome(String nome);

	public Optional<Catalogo> findByProdutos(Produto produto);
	
}
