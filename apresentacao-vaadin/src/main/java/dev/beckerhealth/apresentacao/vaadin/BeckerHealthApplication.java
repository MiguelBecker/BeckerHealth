package dev.beckerhealth.apresentacao.vaadin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {
    "dev.beckerhealth.infraestrutura.persistencia.jpa"
})
@EnableJpaRepositories(basePackages = {
    "dev.beckerhealth.infraestrutura"
})
public class BeckerHealthApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeckerHealthApplication.class, args);
    }
}
