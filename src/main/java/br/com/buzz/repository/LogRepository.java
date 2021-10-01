package br.com.buzz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.buzz.domain.Log;

public interface LogRepository extends JpaRepository<Log, Integer> {
	
}
