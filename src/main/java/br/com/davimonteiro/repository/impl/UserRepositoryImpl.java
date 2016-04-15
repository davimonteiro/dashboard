package br.com.davimonteiro.repository.impl;

import org.springframework.stereotype.Repository;

import br.com.davimonteiro.domain.QUser;
import br.com.davimonteiro.domain.User;
import br.com.davimonteiro.repository.UserRepository;

@Repository
public class UserRepositoryImpl extends GenericRepositoryImpl<User, Long> implements UserRepository {

	public UserRepositoryImpl() {
		super(User.class, QUser.user);
	}

	@Override
	public User loadUserByUsername(String username) {
		QUser user = QUser.user;
		return query().selectFrom(user).where(user.userName.eq(username)).fetchOne();
	}

}
