/*
 * Requisitos para rodar:
 * 1. Java 17+
 * 2. MongoDB rodando localmente (mongodb://localhost:27017)
 * 3. Dependências Spring Boot: Web, Data MongoDB, Lombok
 */

package com.mudanca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class MudancaApApplication {
    public static void main(String[] args) {
        SpringApplication.run(MudancaApApplication.class, args);
    }
}





