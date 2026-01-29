package com.mudanca.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// --- CONFIGURAÇÃO DO SWAGGER/OPENAPI ---
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Planeamento de Mudança")
                        .version("1.0")
                        .description("Documentação dos endpoints para gestão de tarefas e custos de mudança de apartamento."));
    }
}