package com.customanagerapi;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

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
	
	HashMap<String, Object> claims = new HashMap<>();
	claims.put("e-mail", usuario.getLogin());
	claims.put("external", usuario.isExternal());
	claims.put("admin", usuario.isAdmin());
	claims.put("funcionario", usuario.isFuncionario());
	claims.put("acessoAoSistema", usuario.isAcessoAoSistema());	
	
	return Jwts
				.builder()
				.setSubject(usuario.getLogin())
				.setExpiration(data)
				.setClaims(claims)
				.signWith( SignatureAlgorithm.HS512, chaveAssinatura )
				.compact();		
				
	} 	
	

}
