package br.com.davimonteiro.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Contact {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "postgres_sequence")
	@SequenceGenerator(name = "postgres_sequence", sequenceName = "postgres_sequence")
	@Column(name = "contact")
	private Long id;

	@Column(name = "name")
	private String name;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "state")
	private String state;
	
	@Column(name = "postalcode")
	private String postalCode;
	
}
