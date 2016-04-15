package br.com.davimonteiro.web.controller;

import static java.util.Objects.isNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.davimonteiro.domain.Contact;
import br.com.davimonteiro.service.ContactService;

@RestController
@RequestMapping(value = "/api/contacts")
public class ContactController {
	
	@Inject
	private ContactService contactService;
	
	
	//------------------- Create a contact ------------------ //
	
	@RequestMapping(method=POST, consumes={APPLICATION_JSON_VALUE}, produces={APPLICATION_JSON_VALUE})
	public ResponseEntity<Contact> create(@RequestBody Contact contact) {
		Contact result = contactService.save(contact);
		return new ResponseEntity<Contact>(result, HttpStatus.CREATED);
	}
	
	
	//------------------- Retrieve all contacts ------------------ //
	
	@RequestMapping(method=GET, produces={APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Contact>> findAll() {
        List<Contact> result = contactService.findAll();
        
        if(result.isEmpty()){
        	return new ResponseEntity<List<Contact>>(result, HttpStatus.NO_CONTENT);
        }
        
        return new ResponseEntity<List<Contact>>(result, HttpStatus.OK);
    }
	
	//------------------- Retrieve one contact ------------------ //
	
	@RequestMapping(value="/{id}", method=GET, produces={APPLICATION_JSON_VALUE})
    public ResponseEntity<Contact> findById(@PathVariable Long id) {
		Contact contact = contactService.findById(id);
		if (isNull(contact)) {
            return new ResponseEntity<Contact>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Contact>(contact, HttpStatus.OK);
    }
	
	//------------------- Update an entity ------------------ //
	
	@RequestMapping(value = "/{id}", method = PUT, consumes={APPLICATION_JSON_VALUE}, produces={APPLICATION_JSON_VALUE})
    public ResponseEntity<Contact> update(@PathVariable Long id, @RequestBody Contact contact) throws Exception {
		Contact currentContact = contactService.findById(id);
        
        if (isNull(currentContact)) {
            return new ResponseEntity<Contact>(HttpStatus.NOT_FOUND);
        }
        
        BeanUtils.copyProperties(currentContact, contact);
 
        contactService.update(currentContact);
        
        return new ResponseEntity<Contact>(currentContact, HttpStatus.OK);
    }

		
	//------------------- Delete an entity ------------------ //
	
	@RequestMapping(value="/{id}", method=DELETE, produces={APPLICATION_JSON_VALUE})
    public ResponseEntity<Contact> delete(@PathVariable Long id) {
		contactService.delete(id);
		return new ResponseEntity<Contact>(HttpStatus.NO_CONTENT);
    }
	
}
