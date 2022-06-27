package br.com.auth.autenticacao.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.auth.autenticacao.data.DetalheUsuarioData;
import br.com.auth.autenticacao.model.UsuarioModel;
import br.com.auth.autenticacao.repository.UsuarioRepository;

@Service
public class DetalheUsuarioServiceImpl implements UserDetailsService{

	private final UsuarioRepository repository;
	
	
	public DetalheUsuarioServiceImpl(UsuarioRepository repository) {
		this.repository = repository;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UsuarioModel> usuario = repository.findByLogin(username);
		if(usuario.isEmpty()) {
			throw new UsernameNotFoundException("Usuário ["+username+"] não encontrado");
		}
		return new DetalheUsuarioData(usuario);
	}
	
	

}
