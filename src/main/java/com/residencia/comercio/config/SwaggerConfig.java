package com.residencia.comercio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI springShopOpenAPI() {
		return new OpenAPI()
				.info(new Info()
				.title("Atividade API Comercio")
				.description("API comercial com relação entre fornecedores, produtos e categorias. Atividade para a Residência do Serratec."));
	}
}