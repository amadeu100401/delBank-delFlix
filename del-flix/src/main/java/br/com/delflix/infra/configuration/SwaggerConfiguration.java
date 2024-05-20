package br.com.delflix.infra.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI CustomAPI() {
        return new OpenAPI().info(new Info()
                .title("DelFlix API")
                .description("DelFlix API")
                .version("v1.0.0"));
    }
}
