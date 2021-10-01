package br.com.buzz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.buzz.domain.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {}
