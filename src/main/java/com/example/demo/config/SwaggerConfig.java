package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {

        // Local server
        Server localServer = new Server()
                .url("http://localhost:8080")
                .description("Local Development Server");

        // Amypo public server
        Server amypoServer = new Server()
                .url("https://9144.408procr.amypo.ai")
                .description("Amypo Public Server");

        // JWT security scheme
        SecurityScheme bearerAuth = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name("Authorization");

        return new OpenAPI()
                .info(new Info()
                        .title("Parcel Damage Claim API")
                        .version("1.0")
                        .description("Parcel Damage Claim Management System API"))
                .addServersItem(localServer)
                .addServersItem(amypoServer)
                .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
                .components(
                        new io.swagger.v3.oas.models.Components()
                                .addSecuritySchemes("BearerAuth", bearerAuth)
                );
    }
}
