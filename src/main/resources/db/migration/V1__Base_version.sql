
--V1__Base_version.sql

CREATE SEQUENCE postgres_sequence
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9999999999999999
  START 1
  CACHE 1;
ALTER TABLE postgres_sequence OWNER TO postgres;
GRANT ALL ON TABLE postgres_sequence TO postgres;
GRANT ALL ON TABLE postgres_sequence TO public;


CREATE TABLE contact (
	contact numeric(12) NOT NULL,
	name character varying(100) NOT NULL,
	address character varying(100),
	city character varying(100),
	state character varying(100),
	postalcode character varying(100)
);

ALTER TABLE contact OWNER to postgres;

ALTER TABLE contact ADD CONSTRAINT pk_contact PRIMARY KEY (contact);

ALTER TABLE contact ADD CONSTRAINT uk_contact1 UNIQUE (NAME);


CREATE TABLE usuario (
	usuario numeric(12) NOT NULL,
	username character varying(100) NOT NULL,
	password character varying(60),
	email character varying(50)
);


-- data.sql


insert into contact (contact, name, address, city, state, postalcode) values (1, 'Maria Sousa da Silva', 'Rua 1', 'Fortaleza', 'Ceara', '60342333');
insert into contact (contact, name, address, city, state, postalcode) values (2, 'Joao Sousa da Silva', 'Rua 1', 'Fortaleza', 'Ceara', '60342333');
insert into contact (contact, name, address, city, state, postalcode) values (3, 'Mario Sousa da Silva', 'Rua 1', 'Fortaleza', 'Ceara', '60342333');
insert into contact (contact, name, address, city, state, postalcode) values (4, 'Joaquim Sousa da Silva', 'Rua 1', 'Fortaleza', 'Ceara', '60342333');
insert into contact (contact, name, address, city, state, postalcode) values (5, 'Jose Sousa da Silva', 'Rua 1', 'Fortaleza', 'Ceara', '60342333');
insert into contact (contact, name, address, city, state, postalcode) values (6, 'Alberto Sousa da Silva', 'Rua 1', 'Fortaleza', 'Ceara', '60342333');
insert into contact (contact, name, address, city, state, postalcode) values (7, 'Carlos Sousa da Silva', 'Rua 1', 'Fortaleza', 'Ceara', '60342333');
insert into contact (contact, name, address, city, state, postalcode) values (8, 'Bruna Sousa da Silva', 'Rua 1', 'Fortaleza', 'Ceara', '60342333');



insert into usuario (usuario, username, password, email) values (1, 'davimonteiro', '$2a$10$fQeM4rLLdiil5p4XwwgCLeCG.2s.geSf9np1yMyB9st1rAgMDDrM.', 'davimonteiro.ce@gmail.com');

