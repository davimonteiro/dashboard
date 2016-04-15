package br.com.davimonteiro.repository.impl;

import org.springframework.stereotype.Repository;

import br.com.davimonteiro.domain.Contact;
import br.com.davimonteiro.domain.QContact;
import br.com.davimonteiro.repository.ContactRepository;

@Repository
public class ContactRepositoryImpl extends GenericRepositoryImpl<Contact, Long> implements ContactRepository {

	public ContactRepositoryImpl() {
		super(Contact.class, QContact.contact);
	}

}
