package com.customanagerapi.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.customanagerapi.exception.EmailException;

@Service
public class EmailService {
	
	@Autowired private JavaMailSender mailSender;
		
	public String sendRecoveryPasswordEmail(String email, String token) throws EmailException {
		try {
            MimeMessage mail = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper( mail );
            helper.setTo( email );
            helper.setSubject( "Solicitação de troca de senha" );
            helper.setText("<p>Solicitação para trocar senha!"
            		+ "</p>" + "</br> <p> Token: " + token + "</p>", true);

            
            mailSender.send(mail);
            return "E-mail enviado com sucesso!";            
            
        } catch (EmailException | MessagingException e) {
        	throw new EmailException();
        }
	}
	

	public String sendVinculoEmail(String nomeRemetente, 
			String empresaSolicitante, String emailDestinatario) throws EmailException {		
		try {
            MimeMessage mail = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper( mail );
            helper.setTo( emailDestinatario );
            helper.setSubject( "Solicitação de vínculo" );
            helper.setText("<p> Foi solicitado vínculo ao seu usuário no "
            		+ "sistema CustoManager à empresa " + empresaSolicitante +
            		" pelo usuário " + nomeRemetente + "</p>", true);
            
            mailSender.send(mail);
            return "E-mail enviado com sucesso!";            
            
        } catch (EmailException | MessagingException e) {
        	throw new EmailException();
        }
		


	}
	

}
