package br.senai.sp.jandira.odonto.resource;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.jandira.odonto.model.Usuario;
import br.senai.sp.jandira.odonto.repository.UsuarioRepository;
import br.senai.sp.jandira.odonto.resource.dto.TokenDto;
import br.senai.sp.jandira.odonto.resource.form.FormLogin;
import br.senai.sp.jandira.odonto.security.SecurityConfiguration;
import br.senai.sp.jandira.odonto.security.TokenService;

@RestController
@RequestMapping("/login")
public class AutenticacaoResource{

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private UsuarioRepository userRepository;
	
	@Autowired
	private TokenService tokenService;
	
//	@PostMapping
//	public ResponseEntity<?> autenticar(@RequestBody @Valid FormLogin form) {
//		UsernamePasswordAuthenticationToken dataLogin =
//				new UsernamePasswordAuthenticationToken(form.getEmail(), form.getSenha());
//		
//		try {
//			Authentication authentication = authManager.authenticate(dataLogin);
//			
//			return ResponseEntity.ok(authentication);
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().build();
//		}
//		
//	}
//	
	@GetMapping
	public ResponseEntity<?> auth() {
		Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Usuario userLogado = new Usuario();
		
		if (user instanceof Usuario) {
			userLogado.setEmail(((Usuario)user).getUsername());
			userLogado.setNome(((Usuario)user).getNome());
			userLogado.setCodigo(((Usuario)user).getCodigo());
		    return ResponseEntity.ok(userLogado);
		} else {
		    return ResponseEntity.badRequest().build();
		}
	}
	
}
