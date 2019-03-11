package com.github.eltonsandre.discosvinil.api.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.eltonsandre.discosvinil.api.ApiApplication;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author <a href="mailto:elton.santos.andre@gmail.com">Elton S. André</a>
 * @date 10 de mar de 2019 23:38:12
 */
@Configuration
@EnableSwagger2
public class DocSwagger2Config {

	private static final Logger LOGGER = LoggerFactory.getLogger(DocSwagger2Config.class);

	@Bean
	public Docket apiDoc() {
		String basePackage = new ApiApplication().getClass().getPackageName().toString();
		LOGGER.debug("Documentação api: {}", basePackage);
		return new Docket(DocumentationType.SWAGGER_2) // @formatter:off
				 .select()
		          .apis(RequestHandlerSelectors.basePackage(basePackage))
		          .paths(PathSelectors.any())
		          .build()
				  .apiInfo(this.metaData());

				  }// @formatter:on

	/**
	 * @return ApiInfo
	 */
	private ApiInfo metaData() {
		LOGGER.debug("Documentação api metadata");
		return new ApiInfoBuilder()// @formatter:off
				.title("e-Discos Vinil")
				.description("\"programa de fidelidade baseado em cashback* para aumentar o volume de vendas e conquistar novos clientes.\"")
				.contact(
						new Contact("Elton S. André",
								"https://github.com/eltonsandre/e-disco-vinil",
								"elton.santos.andre@gmail.com")
						)
				.version("1.0.0").license("Apache License Version 2.0")
				.licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
				.build();
	}// @formatter:on

}
