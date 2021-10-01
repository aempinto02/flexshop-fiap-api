package br.com.buzz.service;

import java.text.ParseException;
import java.util.Date;
import java.util.Optional;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.buzz.domain.Auth;
import br.com.buzz.domain.Conta;
import br.com.buzz.domain.enums.UserRole;
import br.com.buzz.dto.AuthDTO;
import br.com.buzz.exception.AuthorizationException;
import br.com.buzz.exception.DataIntegrityException;
import br.com.buzz.exception.ObjectNotFoundException;
import br.com.buzz.repository.AuthRepository;
import br.com.buzz.security.UserSS;
import br.com.buzz.utils.Constants;

@Service
public class AuthService {
	
	@Autowired
	private AuthRepository repository;
	
	@Autowired
	private SendGridMailService sgService;

	@Autowired
	private ContaService contaService;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private StandardPBEStringEncryptor encryptor;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public Auth find(Long id) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado!");
		}
		Optional<Auth> optAuth = repository.findById(id);
		optAuth.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Auth.class.getName()
			));
		Auth authSearch = optAuth.get();
		return authSearch;
	}
	
	public Auth findByUsername(String username) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado!");
		}
		Optional<Auth> optAuth = repository.findByUsername(username);
		optAuth.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Username: " + username + ", Tipo: " + Auth.class.getName()
			));
		Auth authSearch = optAuth.get();
		return authSearch;
	}
	
	public Auth insert(Auth auth) {
		auth.setId(null);
		auth = repository.save(auth);
		return auth;
	}
	
	public Auth insert(Long id, UserRole role, String username, String senha) {
		Auth auth = new Auth(id, role, username, pe.encode(senha));
		return repository.save(auth);
	}
	
	public Auth update(Auth auth) {
		Auth newAuth = find(auth.getId());
		updateData(newAuth, auth);
		return repository.save(auth);
	}
	
	public Auth fromDTO(AuthDTO authDto) {
		Auth newAuth = new Auth(null, UserRole.toEnum(authDto.getRole()), authDto.getUsername(), pe.encode(authDto.getPassword()));
		return newAuth;
	}
	
	private void updateData(Auth newAuth, Auth auth) {
		newAuth.setUsername(auth.getUsername());
		newAuth.setRole(auth.getRole());
		
		if (auth.getPassword() != null && !auth.getPassword().equals("")) {
			newAuth.setPassword(pe.encode(auth.getPassword()));
		}
	}
	
	public Auth changeRole(Long id) {
		Auth auth = find(id);
		UserRole role = auth.getRole().equals(UserRole.COMMON) ? UserRole.SELLER : UserRole.COMMON;
		auth.setRole(role);
		return repository.save(auth);
	}
	
	public void recoverPassword(String key, String password) {
		try {			
			String decryptedKey = encryptor.decrypt(key);
			String[] keyData = decryptedKey.split(",");
			String email = keyData[0];
			Date time = new Date();
			try {
				time = Constants.DATE_FORMAT.parse(keyData[1]);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			if (new Date(System.currentTimeMillis()).getTime() > time.getTime()) {
				sgService.sendNewPasswordEmail(email);
				throw new DataIntegrityException("Token expirado, tente novamente. Enviamos um novo email para você");
			}
			
			if (keyData[2] == null || !keyData[2].equals("change-password")) {
				throw new DataIntegrityException("Chave URL inválida");
			}
			Optional<Conta> optConta = contaService.findByEmail(email);
			
			if (optConta.isPresent()) {
				Auth auth = repository.findByUsername(email).get();
				auth.setPassword(encoder.encode(password));
				repository.save(auth);
			} else {
				throw new ObjectNotFoundException("Email não encontrado");
			}
		} catch (EncryptionOperationNotPossibleException e) {
			throw new DataIntegrityException("Chave URL inválida");
		}

	}
	
	public void forgotPassword(String email) {
		Optional<Conta> optConta = contaService.findByEmail(email);

		if (!optConta.isPresent()) {
			throw new ObjectNotFoundException("Conta não encontrada!");
		}
	}
}
