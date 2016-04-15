package br.com.davimonteiro.service;

import javax.inject.Inject;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.davimonteiro.domain.User;
import br.com.davimonteiro.repository.UserRepository;

@Service
public class UsuarioService {
	
	@Inject
	@Lazy
	private PasswordEncoder encoder;
	
	@Inject
	private UserRepository userRepository;
	
	public User loadUserByUsername(String username) {
		return userRepository.loadUserByUsername(username);
	}
	
	public User save(User user) {
		user.setPassword((encoder.encode(user.getPassword())));
		return userRepository.save(user);
	}

}
