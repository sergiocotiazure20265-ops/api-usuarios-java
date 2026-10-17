package br.com.cotiinformatica.api_usuarios.configurations;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(
                        new Info()
                                .title("API de SSO - COTI Informática")
                                .description("""
                                        API REST para gerenciamento de autenticação
                                        e cadastro de usuários da COTI Informática.
                                        
                                        Principais funcionalidades:
                                        
                                        - Cadastro de usuários;
                                        - Autenticação de usuários;
                                        - Geração de tokens JWT;
                                        - Validação de usuários;
                                        - Controle de acesso aos endpoints protegidos.
                                        
                                        Esta API faz parte da arquitetura de SSO
                                        (Single Sign-On) da COTI Informática.
                                        """)
                                .version("1.0.0")
                                .contact(
                                        new Contact()
                                                .name("COTI Informática")
                                                .url("https://www.cotiinformatica.com.br")
                                )
                )
                .addSecurityItem(
                        new SecurityRequirement()
                                .addList("Bearer Authentication")
                )
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        "Bearer Authentication",
                                        new SecurityScheme()
                                                .name("Bearer Authentication")
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                );
    }
}