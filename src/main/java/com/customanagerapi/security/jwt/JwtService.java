package com.customanagerapi.security.jwt;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.customanagerapi.domain.entity.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {	
	
	private long expiracaoMinutos = 120;
	private String chaveAssinatura = "Q3VzdG9NYW5hZ2VyQVBJLVRva2Vu";
	
	public String gerarToken (Usuario usuario) {		
		
	LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expiracaoMinutos);
	Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
	Date data = Date.from(instant);		
	
	HashMap<String, Object> claims = new HashMap<>();
	claims.put("id", usuario.getId());
	claims.put("external", usuario.isExternal());
	claims.put("admin", usuario.isAdmin());
	claims.put("funcionario", usuario.isFuncionario());
	claims.put("acessoAoSistema", usuario.isAcessoAoSistema());	
	
	return Jwts
				.builder()
				.setClaims(claims)
				.setSubject(usuario.getLogin())
				.setExpiration(data)
				.signWith( SignatureAlgorithm.HS512, chaveAssinatura )
				.compact();		
				
	} 		
	
	private Claims obterClaims (String token) throws ExpiredJwtException {		
		return Jwts
					.parser()
					.setSigningKey(chaveAssinatura)
					.parseClaimsJws(token)
					.getBody();
		
	}
	
	public boolean tokenValido (String token) {
		try {
			Claims claims = obterClaims(token);
			Date dataExpiracao = claims.getExpiration();			
			LocalDateTime data = dataExpiracao.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
						
			return !LocalDateTime.now().isAfter(data);
			
		}
		
		catch (Exception e) {
			return false;
		}
		
	}
	
	
	public String obterLoginUsuario(String token) throws ExpiredJwtException {		
		return (String) obterClaims(token).getSubject();		
	}

}
