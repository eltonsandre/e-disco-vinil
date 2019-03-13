# e-disco-vinil
*programa de fidelidade baseado em cashback* para aumentar o volume de vendas e conquistar novos clientes.


Código da API implantada no Heroku: em https://discos-vinil-api.herokuapp.com/
exemplo:
https://discos-vinil-api.herokuapp.com/catalogo?name=rock&genero=rock&page=0&size=20

possui também um front-end em Angular como client UI
https://wmairzwm.github.stackblitz.io/catalogos


## Construindo e executando:

```java
//usando o maven:  dentro do diretorio do projeto execute:
mvn install -U
```

## DOCKER
```java

docker build -t eltonsandre/disco-vinil-api:1.0.0 .

docker run -p 8080:8080 -t eltonsandre/disco-vinil-api:1.0.0 
```

### Documentação


Api construida usando:
- IDE https://spring.io/tools (eclipse)
- Java 10.
- maven 3.6
- Spring Boot
- Spring Data JPA
- lombok [[documentação lombok]][lombok]


### H2 banco em memoria. link para [[h2-console]][1]
	 - Driver Class: org.h2.Driver
	 - JDBC URL: jdbc: h2:./disco-vinil-data
	 - User Name: sa
	 - password:
### Swagger para documentar a API. link para a doc [[swagger]][2]
[2]: http://localhost:8080/swagger-ui.html "Swagger 2 doc API"
[1]: http://localhost:8080/h2-console "h2 console"
[lombok]: https://projectlombok.org/setup/eclipse "documentação lombok"
