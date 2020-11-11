package br.senai.sp.jandira.odonto.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.senai.sp.jandira.odonto.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

	@Value("${odonto.jwt.expiration}")
	private String expiration;
	
	@Value("${odonto.jwt.secret}")
	private String secret;
	
	public String gerarToken(Authentication authentication) {
		Usuario userLogado = (Usuario) authentication.getPrincipal();
		
		Date hoje = new Date();
		Date dataExpiração = new Date(hoje.getTime() + Long.parseLong(expiration));
		
		return Jwts.builder()
				.setIssuer("API Odonto")
				.setSubject(userLogado.getCodigo().toString())
				.setIssuedAt(hoje)
				.setExpiration(dataExpiração)
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}
	
}
