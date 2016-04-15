package br.com.davimonteiro.web.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.davimonteiro.config.security.SecurityUtils;
import br.com.davimonteiro.domain.User;

@RestController
@RequestMapping(value = "api/usuarios")
public class UserController {
	
	@RequestMapping(method = GET, value = "/current", produces = { APPLICATION_JSON_VALUE })
	public ResponseEntity<User> getCurrentUser() {
		User usuario = SecurityUtils.getCurrentUser();
		return new ResponseEntity<User>(usuario, HttpStatus.OK);
	}
	
}
