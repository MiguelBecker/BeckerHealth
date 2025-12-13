package dev.beckerhealth.apresentacao.vaadin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import dev.beckerhealth.aplicacao.consultas.ConsultaRepositorioAplicacao;
import dev.beckerhealth.aplicacao.consultas.ConsultaServicoAplicacao;
import dev.beckerhealth.aplicacao.consultas.processamento.ProcessamentoConsultaAgendada;
import dev.beckerhealth.dominio.compartilhado.evento.EventoBarramento;

@SpringBootApplication(scanBasePackages = {
    "dev.beckerhealth.apresentacao.vaadin",
    "dev.beckerhealth.aplicacao",
    "dev.beckerhealth.infraestrutura"
})
@EntityScan(basePackages = {
    "dev.beckerhealth.infraestrutura.persistencia.jpa"
})
@EnableJpaRepositories(basePackages = {
    "dev.beckerhealth.infraestrutura"
})
public class BeckerHealthApplication {

    @Bean
    public ConsultaServicoAplicacao consultaServicoAplicacao(ConsultaRepositorioAplicacao repositorio) {
        return new ConsultaServicoAplicacao(repositorio);
    }

    @Bean
    @Primary
    public ProcessamentoConsultaAgendada processamentoConsultaAgendada(EventoBarramento eventoBarramento) {
        return new ProcessamentoConsultaAgendada(eventoBarramento);
    }

    public static void main(String[] args) {
        SpringApplication.run(BeckerHealthApplication.class, args);
    }
}
