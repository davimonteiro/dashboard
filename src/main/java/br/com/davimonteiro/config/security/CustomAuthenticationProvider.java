package br.com.davimonteiro.config.security;

import javax.inject.Inject;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.davimonteiro.domain.User;
import br.com.davimonteiro.service.UsuarioService;

@Service
@Transactional
public class CustomAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	@Inject
	private PasswordEncoder encoder;

	@Inject
	private UsuarioService usuarioService;

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
	}

	@Override
	@Transactional
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {

		if (StringUtils.isEmpty(username)) {
			setHideUserNotFoundExceptions(false);
			throw new UsernameNotFoundException("Informe seu endereço de email.");
		}

		String password = (String) authentication.getCredentials();

		if (password == null || StringUtils.isEmpty(password)) {
			throw new BadCredentialsException("Informe sua senha.");
		}

		User usuario = usuarioService.loadUserByUsername(username);

		if (usuario == null) {
			throw new UsernameNotFoundException("Usuário não encontrado.");
		}

		if (!encoder.matches(password, usuario.getPassword())) {
			throw new BadCredentialsException("Senha inválida.");
		}

		if (!usuario.isEnabled()) {
			throw new BadCredentialsException("Usuário desabilitado.");
		}

		return usuario;
	}
}