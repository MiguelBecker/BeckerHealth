package dev.beckerhealth.apresentacao.vaadin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
    "dev.beckerhealth.apresentacao.vaadin",
    "dev.beckerhealth.aplicacao",
    "dev.beckerhealth.infraestrutura"
})
@EntityScan(basePackages = {
    "dev.beckerhealth.dominio.compartilhado",
    "dev.beckerhealth.dominio.consultas",
    "dev.beckerhealth.dominio.prontuario",
    "dev.beckerhealth.dominio.notificacao",
    "dev.beckerhealth.dominio.relatorios"
})
@EnableJpaRepositories(basePackages = {
    "dev.beckerhealth.infraestrutura"
})
public class BeckerHealthApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeckerHealthApplication.class, args);
    }
}

