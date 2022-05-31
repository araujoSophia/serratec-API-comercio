package com.residencia.comercio.services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {
	@Value("${spring.mail.host}")
	private String mailHost;

	@Value("${spring.mail.port}")
	private String mailPort;

	@Value("${spring.mail.username}")
	private String mailUserName;

	@Value("${spring.mail.password}")
	private String mailPassword;

	@Autowired
	JavaMailSender emailSender;

	public MailService(JavaMailSender javaMailSender) {
		this.emailSender = javaMailSender;
	}
	
	public void enviarEmailTexto(String destinatarioEmail, String assunto, String mensagemEmail) {
		SimpleMailMessage sMailMessage = new SimpleMailMessage();
		
		sMailMessage.setTo(destinatarioEmail);
		sMailMessage.setSubject(assunto);
		sMailMessage.setText(mensagemEmail);
		//cuidado no momento de usar um servidor real.
		sMailMessage.setFrom("teste@teste.com");
		
		emailSender.send(sMailMessage);
	}
	
	public void enviarEmailHtml(String destinatarioEmail, String assunto, String mensagemEmail) throws MessagingException {
		MimeMessage mimeMessage = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
		String htmlMsg = "<h3>Hello World!</h3>";
		
		helper.setText(htmlMsg, true);
		helper.setTo("someone@abc.com");
		helper.setSubject("This is the test message for testing gmail smtp server using spring mail");
		helper.setFrom("abc@gmail.com");
		emailSender.send(mimeMessage);
	}

}
