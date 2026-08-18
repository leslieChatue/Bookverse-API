package com.chatue.bookverse.bookverse_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

	@Bean
	OpenAPI bookVerseOpenApi() {

		return new OpenAPI().info(new Info().contact(new Contact().email("Bookverse@example.com")).description("""
				Backend REST API pour la gestion des livres
				sur le sites par des utilisateurs

				Ces API gèrent :
				-La connexion
				-Toutes les opérations CRUD sur les ressources

				Toutes les requêtes différentes de get
				auront besoin d'authentification.

				Le système d'authentification est géré par Spring security avec JWT
				""").version("1.0.0")
				.license(new License().identifier("AZXSZ").name("Leslie chatue @copyright Août 2026")));

	}
}
