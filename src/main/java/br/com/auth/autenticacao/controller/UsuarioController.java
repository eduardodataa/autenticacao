package br.com.auth.autenticacao.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.auth.autenticacao.model.UsuarioModel;
import br.com.auth.autenticacao.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

	private final UsuarioRepository repository;
	
	private final PasswordEncoder encoder;
	
	public UsuarioController(UsuarioRepository usuarioRepository, PasswordEncoder encoder) {
		repository = usuarioRepository;
		this.encoder = encoder;
	}
	
	@GetMapping("/listarTodos")
	public ResponseEntity<List<UsuarioModel>> listarTodos(){
		return ResponseEntity.ok(repository.findAll());
	}
	

	@PostMapping("/salvar")
	public ResponseEntity<UsuarioModel> salvar(@RequestBody UsuarioModel usuarioModel){
		usuarioModel.setPassword(encoder.encode(usuarioModel.getPassword()));
		return ResponseEntity.ok(repository.save(usuarioModel));
	} 
	
	@GetMapping("/validar-senha")
	public ResponseEntity<Boolean> validarSenha(@RequestParam String login, @RequestParam String password){
		Optional<UsuarioModel> usuario = repository.findByLogin(login);
		if(usuario.isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
		}

		boolean valid = encoder.matches(password, usuario.get().getPassword());
		HttpStatus status = (valid)? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
		return ResponseEntity.status(status).body(valid);
	}
	
}
