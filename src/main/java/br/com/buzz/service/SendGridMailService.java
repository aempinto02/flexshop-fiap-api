package br.com.buzz.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import br.com.buzz.domain.Catalogo;
import br.com.buzz.domain.Conta;
import br.com.buzz.domain.Pagamento;
import br.com.buzz.domain.enums.UserRole;
import br.com.buzz.dto.ContaUpdateDTO;
import br.com.buzz.utils.Constants;

@Service
public class SendGridMailService {
	
	@Value("${default.sender}")
	private String sender;
	
	@Value("${front.link}")
	private String urlFront;
	
	@Autowired
	private StandardPBEStringEncryptor encryptor;
	
	SendGrid sendGrid = new SendGrid("SendGrid CREDENTIAL API KEY HERE");

    @Autowired
	public SendGridMailService(SendGrid sendGrid) {
        this.sendGrid = sendGrid;
    }

    void sendMailContaInsert(Conta conta) {
    	Calendar calendar = Calendar.getInstance();
    	if (calendar.get(Calendar.DAY_OF_MONTH) == calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
    		calendar.add(Calendar.MONTH, 1);
    	}
    	calendar.add(Calendar.DAY_OF_MONTH, 1);
    	String date = Constants.DATE_FORMAT.format(new Date(calendar.getTimeInMillis()));
    	
    	Email from = new Email("acolhendosonhos0@gmail.com");
    	String subject = "Seja bem-vindo(a) " + conta.getNome() + " ao sistema de produtos e cátalogos ChoicEasy";
    	Email to = new Email(conta.getEmail());
    	Content content;
    	if(conta.getTipo().equals(UserRole.COMMON)) {
    		content = new Content("text/plain", "Seja bem vindo " + conta.getNome() + "! - " + date + "\n"
    				+ "Agora você pode escolher seus produtos preferidos "
    				+ "nos catálogos disponíveis!\n Escolha e a entrega será "
    				+ "feita em sua casa!\n Aproveite!");
    	} else {
    		content = new Content("text/plain", "Seja bem vindo " + conta.getNome() + "! - " + date + "\n"
    				+ "Agora você pode criar seus catálogos e vender!\n"
    				+ "Nos catálogos insira os produtos relacionados!\n"
    				+ "Encontre a melhor forma de divulgar seus produtos!");
    	}
    	Mail mail = new Mail(from, subject, to, content);
    	
    	Request request = new Request();
    	try {
    		request.setMethod(Method.POST);
    		request.setEndpoint("mail/send");
    		request.setBody(mail.build());
    		sendGrid.api(request);
    		System.out.println("Email de Conta inserida enviado!");
    	} catch (IOException exception) {
    		exception.printStackTrace();
    	}
    }
    
    void sendMailContaUpdate(Conta conta, ContaUpdateDTO contaDto) {
    	Calendar calendar = Calendar.getInstance();
    	if (calendar.get(Calendar.DAY_OF_MONTH) == calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
    		calendar.add(Calendar.MONTH, 1);
    	}
    	calendar.add(Calendar.DAY_OF_MONTH, 1);
    	String date = Constants.DATE_FORMAT.format(new Date(calendar.getTimeInMillis()));
        
    	Email from = new Email("acolhendosonhos0@gmail.com");
        String subject = "Seu email cadastrado foi alterado no sistema ChoicEasy";
        Email to = new Email(conta.getEmail());
        
        Content content = new Content("text/plain", conta.getNome() + ", seu email foi alterado! Alterado em: " + date + "\n"
    			+ "Seu email antigo era: " + conta.getEmail() + "\n"
    			+ "Seu email novo é:  " + contaDto.getEmail() + "\n"
    			+ "Caso essa alteração não tenha sido feita por você, entre em contato!");
        Mail mail = new Mail(from, subject, to, content);
        mail.personalization.get(0).addTo(new Email(contaDto.getEmail()));

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            sendGrid.api(request);
            System.out.println("Email de Conta update enviado!");
        } catch (IOException exception) {
        	exception.printStackTrace();
        }
    }
    
    void sendMailPagamentoComprador(Pagamento pagamento, Conta contaComprador, Conta contaVendedor, Catalogo catalogo) {
    	Calendar calendar = Calendar.getInstance();
    	if (calendar.get(Calendar.DAY_OF_MONTH) == calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
    		calendar.add(Calendar.MONTH, 1);
    	}
    	calendar.add(Calendar.DAY_OF_MONTH, 1);
    	String date = Constants.DATE_FORMAT.format(new Date(calendar.getTimeInMillis()));
    	
    	Email from = new Email("acolhendosonhos0@gmail.com");
    	String subject = contaComprador.getNome() + ", uma compra no ChoicEasy foi solicitada para " + catalogo.getNome();
    	Email to = new Email(contaComprador.getEmail());
    	Content content = new Content("text/plain", "Uma compra foi solicitada no ChoicEasy para o catálogo " + catalogo.getNome() + "\n"
    			+ "--* Detalhes do pedido *--\n"
    			+ "Id: " + pagamento.getId() + "\n"
    			+ "Data: " + date + "\n"
    			+ "Valor: " + pagamento.getValor() + "\n"
    			+ "Forma de pagamento: " + pagamento.getTipo().getDescription() + "\n"
    			+ "Vendedor: " + contaVendedor.getNome() + "\n"
    			+ "Email do vendedor: " + contaVendedor.getEmail());
    	Mail mail = new Mail(from, subject, to, content);
    	
    	Request request = new Request();
    	try {
    		request.setMethod(Method.POST);
    		request.setEndpoint("mail/send");
    		request.setBody(mail.build());
    		sendGrid.api(request);
    		System.out.println("Email de Pagamento do comprador enviado!");
    	} catch (IOException exception) {
    		exception.printStackTrace();
    	}
    }
    
    void sendMailPagamentoVendedor(Pagamento pagamento, Conta contaComprador, Conta contaVendedor, Catalogo catalogo) {
    	Calendar calendar = Calendar.getInstance();
    	if (calendar.get(Calendar.DAY_OF_MONTH) == calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
    		calendar.add(Calendar.MONTH, 1);
    	}
    	calendar.add(Calendar.DAY_OF_MONTH, 1);
    	String date = Constants.DATE_FORMAT.format(new Date(calendar.getTimeInMillis()));
    	
    	Email from = new Email("acolhendosonhos0@gmail.com");
    	String subject = contaVendedor.getNome() + ", uma compra no ChoicEasy foi solicitada para " + catalogo.getNome();
    	Email to = new Email(contaVendedor.getEmail());
    	Content content = new Content("text/plain", "Uma compra foi solicitada no ChoicEasy para o catálogo " + catalogo.getNome() + "\n"
    			+ "--* Detalhes do pedido *--\n"
    			+ "Id: " + pagamento.getId() + "\n"
    			+ "Data: " + date + "\n"
    			+ "Valor: " + pagamento.getValor() + "\n"
    			+ "Forma de pagamento: " + pagamento.getTipo().getDescription() + "\n"
    			+ "Comprador: " + contaComprador.getNome() + "\n"
    			+ "Email do comprador: " + contaComprador.getEmail());
    	Mail mail = new Mail(from, subject, to, content);
    	
    	Request request = new Request();
    	try {
    		request.setMethod(Method.POST);
    		request.setEndpoint("mail/send");
    		request.setBody(mail.build());
    		sendGrid.api(request);
    		System.out.println("Email de Pagamento do vendedor enviado!");
    	} catch (IOException exception) {
    		exception.printStackTrace();
    	}
    }
    
    void sendNewPasswordEmail(String email) {
    	Calendar calendar = Calendar.getInstance();
    	if (calendar.get(Calendar.DAY_OF_MONTH) == calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
    		calendar.add(Calendar.MONTH, 1);
    	}
    	calendar.add(Calendar.DAY_OF_MONTH, 1);
    	String date = Constants.DATE_FORMAT.format(new Date(calendar.getTimeInMillis()));
    	String key = encryptor.encrypt(email + "," + date + ",change-password");
    	
    	Email from = new Email("acolhendosonhos0@gmail.com");
    	String subject = "Solicitação de nova senha - ChoicEasy";
    	Email to = new Email(email);
    	Content content = new Content("text/plain", "Você solicitou a recuperação de senha para a sua conta da Buzz.\n"
    			+ "Acesse o link " + urlFront + "recuperar-senha/" + key + "\n"
    			+ "\nAtenciosamente ChoicEasy.");
    	Mail mail = new Mail(from, subject, to, content);
    	
    	Request request = new Request();
    	try {
    		request.setMethod(Method.POST);
    		request.setEndpoint("mail/send");
    		request.setBody(mail.build());
    		sendGrid.api(request);
    		System.out.println("Email de recuperação de senha enviado!");
    	} catch (IOException exception) {
    		exception.printStackTrace();
    	}
    }   
}
