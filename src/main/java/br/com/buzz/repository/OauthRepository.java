package br.com.buzz.repository;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import br.com.buzz.domain.Oauth;

public interface OauthRepository extends JpaRepositoryImplementation<Oauth, Integer> {}
