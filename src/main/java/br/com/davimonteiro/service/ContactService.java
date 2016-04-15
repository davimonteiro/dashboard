package br.com.davimonteiro.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.davimonteiro.domain.Contact;
import br.com.davimonteiro.repository.ContactRepository;

@Service
@Transactional
public class ContactService {
	
	@Inject
	private ContactRepository contactRepository;

	public Contact save(Contact contact) {
		return contactRepository.save(contact);
	}

	@Transactional(readOnly = true)
	public List<Contact> findAll() {
		return contactRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Contact findById(Long id) {
		return contactRepository.findById(id);
	}

	public void update(Contact contact) {
		contactRepository.update(contact);
	}

	public void delete(Long id) {
		contactRepository.delete(id);
	}
	
	public void deleteAll() {
		contactRepository.deleteAll();
	}
	
}
