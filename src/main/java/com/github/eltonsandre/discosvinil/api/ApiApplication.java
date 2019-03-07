package com.github.eltonsandre.discosvinil.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.github.eltonsandre.discosvinil.api.service.InicializarDadosService;

@SpringBootApplication
public class ApiApplication {

	// @Autowired
	// private InicializarDadosService inicializarDados;

	public static void main(final String[] args) {
		// SpringApplication.run(ApiApplication.class, args);
		ConfigurableApplicationContext context = SpringApplication.run(ApiApplication.class, args);

		context.getBean(InicializarDadosService.class).gerarMassaDadosAlbum();
	}

}
