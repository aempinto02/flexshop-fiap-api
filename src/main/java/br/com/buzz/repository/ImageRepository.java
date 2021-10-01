package br.com.buzz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.buzz.domain.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {}
