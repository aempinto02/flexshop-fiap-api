package br.com.buzz.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.buzz.domain.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long> {
	
	public Optional<Conta> findByEmail(String email);
}
