package br.com.buzz.config;
//
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//import br.com.buzz.service.EmailService;
//import br.com.buzz.service.SmtpEmailService;
//
@Configuration
public class MailConfig {
	
	@Value("${email.secret}")
	private String tokenSecret;
	
	@Value("${email.algorithm}")
	private String tokenAlgorithm;
	
	@Bean
	public StandardPBEStringEncryptor encryptor() {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(tokenSecret);
		encryptor.setAlgorithm(tokenAlgorithm);
		encryptor.setStringOutputType("hexadecimal");
		return encryptor;
	}

}
