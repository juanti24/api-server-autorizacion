package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.security.JwtUtils;
import com.example.demo.service.to.UsuarioTo;

@RestController
@RequestMapping("/autorizaciones")
public class AuthorizationControllerRestFul {

	@Autowired
	private AuthenticationManager authM;

	@Autowired
	private JwtUtils jwt;

	@GetMapping(path = "jwt", consumes = MediaType.APPLICATION_JSON_VALUE)
	public String obtenerToken(@RequestBody UsuarioTo usuario) {
		// Autenticación
		// Validad el usuario y el password que sean correctos
		// si es correcta la autenticación ricien ahi retorno el token (autorización que
		// le permite enviar al servicio de negocio)
		this.autenticacion(usuario.getNombre(), usuario.getPassword());
		return this.jwt.buildTokenJwt(usuario.getNombre());
	}

	// Manera automática
	private void autenticacion(String usuario, String password) {
		UsernamePasswordAuthenticationToken usuarioToken = new UsernamePasswordAuthenticationToken(usuario, password);

		Authentication autenticacion = this.authM.authenticate(usuarioToken);

		SecurityContextHolder.getContext().setAuthentication(autenticacion);
	}

}
