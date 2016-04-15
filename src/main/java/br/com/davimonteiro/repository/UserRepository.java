package br.com.davimonteiro.repository;

import br.com.davimonteiro.domain.User;

public interface UserRepository extends GenericRepository<User, Long> {
	
	User loadUserByUsername(String username);

}
