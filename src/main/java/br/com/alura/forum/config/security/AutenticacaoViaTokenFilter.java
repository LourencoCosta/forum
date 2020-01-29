package br.com.alura.forum.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.alura.forum.modelo.Usuario;
import br.com.alura.forum.repository.UsuarioRepository;

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter{

	private TokenService tokenService;
	
	private UsuarioRepository usuarioRepository;
	
	public AutenticacaoViaTokenFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
		this.tokenService = tokenService;
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = recuperarToken(request);
		boolean valido = tokenService.isTokenValido(token);
		
		if (valido){
			autenticacaoClient(token);
		}
		
		filterChain.doFilter(request, response);
		
	}
	
	/*
	 * A autenticacao no AutenticationManager é apenas para lógica de autenticacao via
	 * user/password para geração de token 
	 * SecurityContextHolder força a autenticacao no spring independente do mecanismo de auteticacao do usuario
	 */
	private void autenticacaoClient (String token){
		Long idUsario = tokenService.getIdUsario(token);
		Usuario usuario = usuarioRepository.findById(idUsario).get();
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());;
		SecurityContextHolder.getContext().setAuthentication(authentication); //dá a autenticacao ao usuario
	}

	private String recuperarToken(HttpServletRequest request) {
		
		String token = request.getHeader("Authorization");
		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")){
			return  null;
		}
		return token.substring(7, token.length());
	}

}
