package br.com.delflix.delflix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages={"br.com.delflix.api", "br.com.delflix.application", 
"br.com.delflix.infra", "br.com.delflix.domain", "br.com.delflix.shared"})
@EntityScan("br.com.delflix.domain.entity")
@EnableJpaRepositories("br.com.delflix.infra.repository")
@SpringBootApplication
public class DelFlixApplication {

	public static void main(String[] args) {
		SpringApplication.run(DelFlixApplication.class, args);
	}

}
