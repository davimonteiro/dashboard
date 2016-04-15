# Prova de Conceito #
========================

### Dependências ###
----------------------------
* Oracle JDK 8
* Maven 3.x
* PostgreSQL

### Instalação ###
----------------------------

Primeiramente, deve-se criar um banco de dados com o nome contactsdb. Logo depois, deve-se alterar as configurações do banco da dados, listadas abaixo, no arquivo application.properties.

```
spring.datasource.url=jdbc:postgresql://localhost:5432/contactsdb
spring.datasource.driverClassName=org.postgresql.Driver
spring.datasource.username=postgres
spring.datasource.password=secret
```

### Execução ###
----------------------------
Para executar a aplicação, deve-se executar o comanda abaixo na raiz do projeto.


```
mvn spring-boot:run
```

### Tecnologias utilizadas ###
----------------------------
> AngularJS (OK)

> Angular Material (OK)

> Java (OK)

> Spring (Core / Data / Boot) (OK)

> Spring Security com OAuth 2

> RESTful Web Service (Level 2: HTTP Verbs) (OK)

> JPA (OK)

> QueryDSL (OK)

> PostgreSQL (OK)

> Flyway (Evolução automatizada de banco de dados) (OK)

> Testes (JUnit / Spring MVC Test) (OK)



### Diretórios ###
----------------------------
> src/main/java

> src/main/resources

> src/test/java

### Sobre ###
----------------------------
[Contato](http://www.davimonteiro.com.br)
