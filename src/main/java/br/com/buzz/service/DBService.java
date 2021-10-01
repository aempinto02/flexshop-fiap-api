package br.com.buzz.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.buzz.domain.Auth;
import br.com.buzz.domain.Catalogo;
import br.com.buzz.domain.Conta;
import br.com.buzz.domain.Image;
import br.com.buzz.domain.Produto;
import br.com.buzz.domain.enums.UserRole;
import br.com.buzz.repository.AuthRepository;
import br.com.buzz.repository.CatalogoRepository;
import br.com.buzz.repository.ContaRepository;
import br.com.buzz.repository.ImageRepository;
import br.com.buzz.repository.ProdutoRepository;

@Service
public class DBService {

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private AuthRepository authRepository;

	@Autowired
	private ImageRepository imageRepository;

	@Autowired
	private CatalogoRepository catalogoRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	public void generateTokens() {

		Auth auth = new Auth(1L, UserRole.ADMIN, "andremartinspinto@outlook.com", pe.encode("lombadinha"));
		Auth auth2 = new Auth(2L, UserRole.SELLER, "lucas@yahoo.com", pe.encode("abumod127"));
		Auth auth3 = new Auth(3L, UserRole.COMMON, "aempinto02@gmail.com", pe.encode("teste123"));
		
		authRepository.saveAll(Arrays.asList(auth, auth2, auth3));
		
	}

	public void instantiateDatabase() throws ParseException {
		
		Conta conta = new Conta(null, "Rua 1", UserRole.ADMIN, "André", "andremartinspinto@outlook.com");
		Conta conta2 = new Conta(null, "Avenida 2", UserRole.SELLER, "Lucas", "lucas@yahoo.com");
		Conta conta3 = new Conta(null, "Praça 3", UserRole.COMMON, "Teste", "aempinto02@gmail.com");
		
		contaRepository.saveAll(Arrays.asList(conta, conta2, conta3));

		Image fotoCatalogo = imageRepository.save(new Image(null, "https://buzz-choiceasy.s3.sa-east-1.amazonaws.com/moveis-industriais-catalogo.jpg"));
		Image fotoCatalogo1 = imageRepository.save(new Image(null, "https://buzz-choiceasy.s3.sa-east-1.amazonaws.com/padaria-catalogo.png"));
		Image fotoCatalogo2 = imageRepository.save(new Image(null, "https://buzz-choiceasy.s3.sa-east-1.amazonaws.com/brinquedo-catalogo"));
		
		Image fotoProduto0 = imageRepository.save(new Image(null, "https://buzz-choiceasy.s3.sa-east-1.amazonaws.com/moveis-industriais-produto-0.jpg" ));
		Image fotoProduto01 = imageRepository.save(new Image(null, "https://buzz-choiceasy.s3.sa-east-1.amazonaws.com/moveis-industriais-produto-1.jpg"));
		Image fotoProduto02 = imageRepository.save(new Image(null, "https://buzz-choiceasy.s3.sa-east-1.amazonaws.com/moveis-industriais-produto-2.jpg"));
		Image fotoProduto03 = imageRepository.save(new Image(null, "https://buzz-choiceasy.s3.sa-east-1.amazonaws.com/moveis-industriais-produto-3.jpg"));
		Image fotoProduto04 = imageRepository.save(new Image(null, "https://buzz-choiceasy.s3.sa-east-1.amazonaws.com/moveis-industriais-produto-4.jpg"));
		Image fotoProduto05 = imageRepository.save(new Image(null, "https://buzz-choiceasy.s3.sa-east-1.amazonaws.com/moveis-industriais-produto-5.jpg"));
		Image fotoProduto1 = imageRepository.save(new Image(null, "https://buzz-choiceasy.s3.sa-east-1.amazonaws.com/padaria-produto-0.jpg"));
		Image fotoProduto11 = imageRepository.save(new Image(null, "https://buzz-choiceasy.s3.sa-east-1.amazonaws.com/padaria-produto-1.jpg"));
		Image fotoProduto12 = imageRepository.save(new Image(null, "https://buzz-choiceasy.s3.sa-east-1.amazonaws.com/padaria-produto-2.jpg"));
		Image fotoProduto13 = imageRepository.save(new Image(null, "https://buzz-choiceasy.s3.sa-east-1.amazonaws.com/padaria-produto-3.jpg"));
		Image fotoProduto2 = imageRepository.save(new Image(null, "https://buzz-choiceasy.s3.sa-east-1.amazonaws.com/brinquedo-produto-0.jpg"));
		Image fotoProduto21 = imageRepository.save(new Image(null, "https://buzz-choiceasy.s3.sa-east-1.amazonaws.com/brinquedo-produto-1.jpg"));
		Image fotoProduto22 = imageRepository.save(new Image(null, "https://buzz-choiceasy.s3.sa-east-1.amazonaws.com/brinquedo-produto-2.jpg"));
		Image fotoProduto23 = imageRepository.save(new Image(null, "https://buzz-choiceasy.s3.sa-east-1.amazonaws.com/brinquedo-produto-3.jpg"));
		Image fotoProduto24 = imageRepository.save(new Image(null, "https://buzz-choiceasy.s3.sa-east-1.amazonaws.com/brinquedo-produto-4.jpg"));
		
		Catalogo catalogo = new Catalogo(null, "Móveis industriais Meira", fotoCatalogo, "Funcionamento: 08-18hrs", conta2, "Veja os melhores móveis industriais para a sua decoração de casa ou escritório");
		Catalogo catalogo1 = new Catalogo(null, "Padaria Doce Melodia", fotoCatalogo1, "Funcionamento: 07-20hrs", conta2, "Catálogo dos produtos da padaria que são únicos para quem aprecia comer");
		Catalogo catalogo2 = new Catalogo(null, "Brinquedos Sorriso infantil", fotoCatalogo2, "Funcionamento: 10-22hrs", conta2, "Faça a alegria de uma criança com nossos produtos");
		catalogo.setQuantidadeProdutos(6);
		catalogo1.setQuantidadeProdutos(4);
		catalogo2.setQuantidadeProdutos(5);
		catalogoRepository.saveAll(Arrays.asList(catalogo, catalogo1, catalogo2));

		Produto produto0 = new Produto(null, "Armário industrial", "Armário industrial de 2m por 0,8m em acabamento de madeira", catalogo, new BigDecimal("681.59"), fotoProduto0);
		Produto produto01 = new Produto(null, "Bancada industrial", "Bancada industrial de 0,4m por 1,5m em acabamento de madeira e estrutura de ferro", catalogo, new BigDecimal("312.93"), fotoProduto01);
		Produto produto02 = new Produto(null, "Prateleira industrial", "Prateleira industrial circular de 0,7m de raio em acabamento de madeira e estrutura de ferro", catalogo, new BigDecimal("271.11"), fotoProduto02);
		Produto produto03 = new Produto(null, "Conjunto de móveis industriais", "Conjunto de mesa, aparador, bancada em acabamento de madeira e estrutura de ferro", catalogo, new BigDecimal("2558.47"), fotoProduto03);
		Produto produto04 = new Produto(null, "Estante industrial", "Estante industrial de 2m por 0,5m em acabamento de madeira", catalogo, new BigDecimal("340.59"), fotoProduto04);
		Produto produto05 = new Produto(null, "Decorativo industrial", "Decorativo industrial quadrado (preço por unidade)", catalogo, new BigDecimal("127.59"), fotoProduto05);

		Produto produto1 = new Produto(null, "Pão artesanal", "Pão com trigo orgânico e integral feito de modo artesanal (preço por unidade)", catalogo1, new BigDecimal("4.10"), fotoProduto1);
		Produto produto11 = new Produto(null, "Sonho", "Doce com creme saboroso e textura suave (preço por unidade)", catalogo1, new BigDecimal("6.16"), fotoProduto11);
		Produto produto12 = new Produto(null, "Baguete", "Lanche com peito de peru, queijo muçarela, tomate, alface e versão vegetariana sem peito de peru (preço por unidade)", catalogo1, new BigDecimal("10.59"), fotoProduto12);
		Produto produto13 = new Produto(null, "Refrigerante", "Refrigerante de qualquer sabor ou marca de 2Litros (preço por unidade)", catalogo1, new BigDecimal("5.00"), fotoProduto13);

		Produto produto2 = new Produto(null, "Computador educativo", "Computador educativo para brincar e ensinar (Até 7 anos de idade)", catalogo2, new BigDecimal("259.70"), fotoProduto2);
		Produto produto21 = new Produto(null, "Casinha de plástico", "Casinha de plástico (Até 10 anos de idade)", catalogo2, new BigDecimal("500.00"), fotoProduto21);
		Produto produto22 = new Produto(null, "Brinquedo criativo", "Brinquedo com peças de madeira para estimular criatividade (Até 10 anos de idade)", catalogo2, new BigDecimal("110.49"), fotoProduto22);
		Produto produto23 = new Produto(null, "Peixe de plástico", "Peixe de plástico que nada com acionamento por botão (Até 8 anos de idade)", catalogo2, new BigDecimal("25.00"), fotoProduto23);
		Produto produto24 = new Produto(null, "Coruja interativa", "Coruja interativa que conversa (Até 10 anos de idade)", catalogo2, new BigDecimal("258.00"), fotoProduto24);
		
		produtoRepository.saveAll(Arrays.asList(produto0, produto01, produto02, produto03, produto04, produto05, produto1, produto11, produto12, produto13, produto2, produto21, produto22, produto23, produto24));
		
		catalogo.getProdutos().addAll(Arrays.asList(produto0, produto01, produto02, produto03, produto04, produto05));
		catalogo1.getProdutos().addAll(Arrays.asList(produto1, produto11, produto12, produto13));
		catalogo2.getProdutos().addAll(Arrays.asList(produto2, produto21, produto22, produto23, produto24));
		
		catalogoRepository.saveAll(Arrays.asList(catalogo, catalogo1, catalogo2));
		
		conta2.setCatalogos(Arrays.asList(catalogo,catalogo1,catalogo2));
		contaRepository.save(conta2);
	}
}
