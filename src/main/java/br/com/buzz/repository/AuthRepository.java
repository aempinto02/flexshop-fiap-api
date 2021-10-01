package br.com.buzz.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.buzz.domain.Auth;

public interface AuthRepository extends JpaRepository<Auth, Long> {

	@Transactional(readOnly = true)
	Optional<Auth> findByUsername(String username);

	@Transactional(readOnly = true)
	Optional<Auth> findById(Long id);
}
