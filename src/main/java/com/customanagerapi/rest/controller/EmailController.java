package com.customanagerapi.rest.controller;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.customanagerapi.exception.EmailException;
import com.customanagerapi.service.EmailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@RestController
@RequestMapping("/email")
@CrossOrigin(origins = {"http://localhost:8080", "https://customanager.netlify.app"})
public class EmailController {

    @Autowired private JavaMailSender mailSender;
    
    @Autowired 
    @Lazy
    private EmailService service;

    @RequestMapping(path = "/send-recovery-password-email", method = RequestMethod.GET)
    public String sendMailToUser(String email, String token) throws EmailException {
    	return service.sendRecoveryPasswordEmail(email, token);
    }
}
