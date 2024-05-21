package br.com.delflix.infra.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class ResourceErrorMessageConfiguration {

    @Bean
    public ResourceBundleMessageSource ResourceErrorMessage() {
        ResourceBundleMessageSource message = new ResourceBundleMessageSource();
        message.setBasename("ResourceErrorMessage");
        message.setDefaultEncoding("UTF-8");
        return message;
    }
}
