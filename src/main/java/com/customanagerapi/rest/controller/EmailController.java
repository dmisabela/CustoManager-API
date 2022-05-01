package com.customanagerapi.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.customanagerapi.exception.EmailException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@RestController
@RequestMapping("/email")
@CrossOrigin(origins = {"http://localhost:8080", "https://customanager.netlify.app"})
public class EmailController {

    @Autowired private JavaMailSender mailSender;

    @RequestMapping(path = "/send-to-user", method = RequestMethod.GET)
    public String sendMailToUser(String email, String token) throws EmailException {
        try {
            MimeMessage mail = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper( mail );
            helper.setTo( email );
            helper.setSubject( "Teste Envio de e-mail" );
            helper.setText("<p>url-trocar-senha" + token + "</p>", true);
            
            mailSender.send(mail);
            return "E-mail enviado com sucesso!";            
            
        } catch (EmailException | MessagingException e) {
        	throw new EmailException();
        }
    }
}
