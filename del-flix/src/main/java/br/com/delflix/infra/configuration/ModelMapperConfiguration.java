package br.com.delflix.infra.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration 
{
    @Bean
    public ModelMapper GetModel()
    {
        return new ModelMapper();
    }

}
