package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Parcel Claim API")
                        .version("1.0")
                        .description("API via Amypo public URL"))
                .addServersItem(new Server()
                        .url("https://9144.408procr.amypo.ai")
                        .description("Amypo Public Server"));
    }
}
