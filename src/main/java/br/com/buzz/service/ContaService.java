package br.com.buzz.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.buzz.domain.Auth;
import br.com.buzz.domain.Carrinho;
import br.com.buzz.domain.Conta;
import br.com.buzz.domain.enums.UserRole;
import br.com.buzz.dto.ContaInsertDTO;
import br.com.buzz.dto.ContaUpdateDTO;
import br.com.buzz.exception.AuthorizationException;
import br.com.buzz.exception.DataIntegrityException;
import br.com.buzz.exception.ObjectNotFoundException;
import br.com.buzz.repository.AuthRepository;
import br.com.buzz.repository.CarrinhoRepository;
import br.com.buzz.repository.CatalogoRepository;
import br.com.buzz.repository.ContaRepository;
import br.com.buzz.security.UserSS;

@Service
public class ContaService {
	
	@Autowired
	private CarrinhoRepository carrinhoRepository;
	
	@Autowired
	private CatalogoRepository catalogoRepository;

	@Autowired
	private ContaRepository repository;

	@Autowired
	private AuthRepository authRepository;
	
	
	@Autowired
	private SendGridMailService sgService;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	public Optional<Conta> findByEmail(String email) {
		return repository.findByEmail(email);
	}

	public Conta find() {
		UserSS user = UserService.authenticated();
		Optional<Conta> optConta = repository.findById(user.getId());
		optConta.orElseThrow(() -> new ObjectNotFoundException("Conta não encontrada! Id: " + user.getId()));
		
		return optConta.get();
	}

	public Conta findById(Long id) {
		Optional<Conta> optConta = repository.findById(id);
		optConta.orElseThrow(() -> new ObjectNotFoundException("Conta não encontrada! Id: " + id));
		return optConta.get();
	}

	public Conta insertConta(ContaInsertDTO contaDto) {
		UserSS user = UserService.authenticated();
		if(contaDto.getTipo() == 3 && !user.hasRole(UserRole.ADMIN))
			throw new AuthorizationException("Somente administrador pode inserir outro administrador!");
		if(contaDto.getTipo() != 1 && contaDto.getTipo() != 2)
			throw new DataIntegrityException("Só é possível inserir tipo=1(Comum) ou tipo=2(Vendedor)!");
		Conta conta = fromDTO(contaDto);
		conta = repository.save(conta);
		Auth auth = new Auth(conta.getId(), UserRole.toEnum(contaDto.getTipo()), contaDto.getEmail(), pe.encode(contaDto.getSenha()));
		authRepository.save(auth);
		carrinhoRepository.save(new Carrinho(conta.getId(), new BigDecimal(0.0)));
		sgService.sendMailContaInsert(conta);
		
		return conta;
	}

	public Conta updateConta(@Valid ContaUpdateDTO contaDto) {
		UserSS user = UserService.authenticated();
		if(user == null) throw new AuthorizationException("É preciso estar logado para alterar conta!");
		Optional<Conta> optConta = repository.findById(user.getId());
		optConta.orElseThrow(() -> new ObjectNotFoundException("Conta não encontrada! Id: " + user.getId()));
		Conta conta = repository.findById(user.getId()).get();
		if(contaDto.getNome() == null) contaDto.setNome("");
		if(!contaDto.getNome().isBlank() && !contaDto.getNome().equals(conta.getNome())) {
			conta.setNome(contaDto.getNome());
			repository.save(conta);
		}
		if(contaDto.getEmail() == null) contaDto.setEmail("");
		if(!contaDto.getEmail().isBlank() && !contaDto.getEmail().equals(conta.getEmail())) {
			Auth auth = authRepository.findByUsername(conta.getEmail()).get();
			sgService.sendMailContaUpdate(conta, contaDto);
			auth.setUsername(contaDto.getEmail());
			conta.setEmail(contaDto.getEmail());
			repository.save(conta);
			authRepository.save(auth);
		}
		
		return repository.save(conta);
	}

	public Conta updateTipoConta(Long id, Integer tipo) {
		if(tipo < 1 || tipo > 3) throw new DataIntegrityException("Só há três tipos de conta! 1-Comum;2-Vendedor;3-Admin");
		UserSS user = UserService.authenticated();
		Optional<Conta> optConta = repository.findById(id);
		optConta.orElseThrow(() -> new ObjectNotFoundException("Conta não encontrada! Id: " + id));
		Conta conta = optConta.get();
		
		if((user.getId() != conta.getId()) && !user.hasRole(UserRole.ADMIN)) throw new AuthorizationException("Só ADMIN altera outras contas!");

		if(conta.getTipo().getCode() != tipo) {
			if(tipo == 3 && conta.getTipo().getCode() != 3)
				throw new AuthorizationException("Apenas administrador pode tornar outro administrador!");
			conta.setTipo(UserRole.toEnum(tipo));
			Auth auth = authRepository.findById(conta.getId()).get();
			auth.setRole(UserRole.toEnum(tipo));
			authRepository.save(auth);
			
			return repository.save(conta);
		}
		
		return conta;
	}

	public void deleteConta(Long id) {
		UserSS user = UserService.authenticated();
		if(id != user.getId() && !user.hasRole(UserRole.ADMIN))
			throw new AuthorizationException("Você não tem perfil de ADMIN para deletar esta conta!");
		Optional<Conta> optConta = repository.findById(id);
		optConta.orElseThrow(() -> new ObjectNotFoundException("Conta não encontrada! Id: " + id));
		
		catalogoRepository.deleteAll(optConta.get().getCatalogos());
		authRepository.deleteById(id);
		repository.deleteById(id);
	}
	
	private Conta fromDTO(ContaInsertDTO contaDto) {
		Conta conta = new Conta(null, contaDto.getEndereco(), UserRole.toEnum(contaDto.getTipo()), contaDto.getNome(), contaDto.getEmail());
		conta.setCatalogos(new ArrayList<>());
		return conta;
	}
}
