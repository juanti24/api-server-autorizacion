package com.example.demo.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {

	@Value("${app.jwtExpirationMs}")
	private int jwtExpirationMs;
	@Value("${app.jwtSemilla}")
	private String jwtSemilla;
	
	// Construir el token
	public String buildTokenJwt(String nombre) {

		// setSubject es el payload

		// Token: una fortaleza es que tiene tiempo de expiración
		return Jwts.builder().setSubject(nombre).setSubject("Hola Mundo").setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, this.jwtSemilla).compact();
	}

}
