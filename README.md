# e-disco-vinil
*programa de fidelidade baseado em cashback* para aumentar o volume de vendas e conquistar novos clientes.

**Api construida usando:**
- IDE https://spring.io/tools (eclipse)
- Java 10.
- maven 3.6
- Spring Boot
- Spring Data JPA
- lombok [[documentação lombok]][lombok]

Código da API está implantada no Heroku: em https://discos-vinil-api.herokuapp.com/
exemplo:
https://discos-vinil-api.herokuapp.com/catalogo?name=rock&genero=rock&page=0&size=20

## Postman 
import .json para teste da API, na raiz do projeto -> *TesteApiDiscosVinil.postman_collection.json*

## Construindo e executando:
### Maven  [[download]][4]
```java
//usando o maven:  na raiz do projeto execute:
mvn install -U
```

### DOCKER 
```java
// na raiz do projeto onde está o Dockerfile execute os comandos abaixo
docker build -t eltonsandre/disco-vinil-api:1.0.0 .
docker run -p 8080:8080 -t eltonsandre/disco-vinil-api:1.0.0
```

### Documentação

### Banco de Dados: H2 banco em memoria. link para 

	 - Driver Class: org.h2.Driver
	 - JDBC URL: jdbc: h2:./disco-vinil-data
	 - User Name: sa
	 - password:
[[link localhost]][1]

### Swagger para documentar a API. link para a doc 
documentação pela api direto pelo Heroku https://discos-vinil-api.herokuapp.com/swagger-ui.html
[[link swagger localhost]][2]
[2]: http://localhost:8080/swagger-ui.html "Swagger 2 doc API"
[1]: http://localhost:8080/h2-console "h2 console"
[lombok]: https://projectlombok.org/setup/eclipse "documentação lombok"
[4]: http://ftp.unicamp.br/pub/apache/maven/maven-3/3.6.0/binaries/apache-maven-3.6.0-bin.zip "Download maven"
