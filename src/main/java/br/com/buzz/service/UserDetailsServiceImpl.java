package br.com.buzz.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.buzz.domain.Auth;
import br.com.buzz.repository.AuthRepository;
import br.com.buzz.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private AuthRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Auth> optionalUser = repository.findByUsername(username);
		if (!optionalUser.isPresent()) {
			throw new UsernameNotFoundException(username);
		}
		Auth user = optionalUser.get();
		
		return new UserSS(user.getId(), user.getUsername(), user.getPassword(), user.getRole());
	}

}
