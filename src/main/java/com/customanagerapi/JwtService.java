package com.customanagerapi;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import org.springframework.stereotype.Service;

import com.customanagerapi.entity.Usuario;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {	
	
	private long expiracaoMinutos = 30;
	private String chaveAssinatura = "Q3VzdG9NYW5hZ2VyQVBJLVRva2Vu";
	
	public String gerarToken (Usuario usuario) {		
		
	LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expiracaoMinutos);
	Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
	Date data = Date.from(instant);		
	return Jwts
				.builder()
				.setSubject(usuario.getLogin())
				.setExpiration(data)
				.signWith( SignatureAlgorithm.HS512, chaveAssinatura )
				.compact();		
				
	} 	
	

}
