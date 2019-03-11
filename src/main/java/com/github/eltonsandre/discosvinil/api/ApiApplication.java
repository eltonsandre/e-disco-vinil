package com.github.eltonsandre.discosvinil.api;

import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.github.eltonsandre.discosvinil.api.service.InicializarDadosService;

/**
 * @author <a href="mailto:elton.santos.andre@gmail.com">Elton S. André</a>
 * @date 7 de mar de 2019 19:01:43
 */
@SpringBootApplication
public class ApiApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApiApplication.class);

	/**
	 * Atributo CHARSET_UTF8 Charset defaultCharset
	 */
	public static Charset CHARSET_UTF8 = Charset.defaultCharset();

	/**
	 * @param args void
	 */
	public static void main(final String[] args) {
		// SpringApplication.run(ApiApplication.class, args);
		ConfigurableApplicationContext context = SpringApplication.run(ApiApplication.class, args);

		context.getBean(InicializarDadosService.class).gerarDados(20);
	}

	/**
	 * @return RestTemplate
	 */
	@Bean
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public static RestTemplate restTemplateClient() {
		final RestTemplate rest = new RestTemplate();

		rest.getMessageConverters().add(new StringHttpMessageConverter(CHARSET_UTF8));
		rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		LOGGER.debug("@Bean restTemplateClient");
		return rest;
	}

}
