package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurity {

	@Autowired
	private UserDetailsService detailService;
	@Autowired
	private AuthEntryPointJwt authEntryPointJwt;

	// Sring security: Automaticamente mi API queda asegurada
	// Configuración de mi API
	// API de obtener token debe ser publica

	/// API/v1.0/Seguridad/autorizaciones/jwt/**

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(authEntryPointJwt).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers("/autorizaciones/jwt/**").permitAll().antMatchers("/autorizaciones/jwt/**").permitAll()
				.anyRequest().authenticated();

		http.authenticationProvider(authenticationProvider());

		return http.build();
	}

	// Autenticación
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		// Dao: Acceso a datos
		// Accede al proveedor de autenticación
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		// Usuario
		authProvider.setUserDetailsService(detailService);
		// Password con el que se va a codificar
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	// Permite gestionar la autenticacion en mi API

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	// Es una mala practica que el password se guarde en texto plano

	@Bean
	public PasswordEncoder passwordEncoder() {
		// Tipo de algoritmo con el que se va a encriptar ne la base de datos
		return new BCryptPasswordEncoder();
	}

}
