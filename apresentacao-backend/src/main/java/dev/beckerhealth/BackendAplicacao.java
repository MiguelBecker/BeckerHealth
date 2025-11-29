package dev.beckerhealth;

import static org.springframework.boot.SpringApplication.run;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import dev.beckerhealth.aplicacao.consultas.ConsultaRepositorioAplicacao;
import dev.beckerhealth.aplicacao.consultas.ConsultaServicoAplicacao;

@SpringBootApplication(scanBasePackages = {
    "dev.beckerhealth.apresentacao.backend",
    "dev.beckerhealth.aplicacao",
    "dev.beckerhealth.infraestrutura"
})
@EntityScan(basePackages = {
    "dev.beckerhealth.infraestrutura.persistencia.jpa"
})
@EnableJpaRepositories(basePackages = {
    "dev.beckerhealth.infraestrutura"
})
public class BackendAplicacao {
    @Bean
    public ConsultaServicoAplicacao consultaServicoAplicacao(ConsultaRepositorioAplicacao repositorio) {
        return new ConsultaServicoAplicacao(repositorio);
    }

    public static void main(String[] args) {
        run(BackendAplicacao.class, args);
    }
}

