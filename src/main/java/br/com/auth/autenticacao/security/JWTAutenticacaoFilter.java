package br.com.auth.autenticacao.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.auth.autenticacao.data.DetalheUsuarioData;
import br.com.auth.autenticacao.model.UsuarioModel;

public class JWTAutenticacaoFilter extends UsernamePasswordAuthenticationFilter{
	
	public static int TOKEN_EXPIRACAO = 600_000;
	public static final String TOKEN_SENHA = "686a2d0d-88d2-479e-b4e2-17455b63e35f";
	private final AuthenticationManager authenticationManager;

	public JWTAutenticacaoFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		try {
			UsuarioModel usuarioModel = new ObjectMapper().readValue(request.getInputStream(), UsuarioModel.class);
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuarioModel.getLogin(), usuarioModel.getPassword()));
			
		} catch (StreamReadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabindException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Falha na autenticação do usuário", e);
		}
		return null;
	}	
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		// TODO Auto-generated method stub
		DetalheUsuarioData usuarioData = (DetalheUsuarioData) authResult.getPrincipal();
		String token = JWT.create()
				.withSubject(usuarioData.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRACAO))
				.sign(Algorithm.HMAC512(TOKEN_SENHA));
		
		response.getWriter().write(token);
		response.getWriter().flush();
		
	}
	

}
