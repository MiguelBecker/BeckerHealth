package dev.beckerhealth.apresentacao.vaadin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import dev.beckerhealth.aplicacao.consultas.ConsultaRepositorioAplicacao;
import dev.beckerhealth.aplicacao.consultas.ConsultaServicoAplicacao;
import dev.beckerhealth.aplicacao.consultas.processamento.ProcessamentoConsultaAgendada;
import dev.beckerhealth.dominio.compartilhado.evento.EventoBarramento;
import dev.beckerhealth.dominio.prontuario.ExameRepository;
import dev.beckerhealth.dominio.prontuario.PrescricaoRepository;
import dev.beckerhealth.dominio.prontuario.ProntuarioRepository;
import dev.beckerhealth.dominio.prontuario.ProntuarioService;

@SpringBootApplication(scanBasePackages = {
    "dev.beckerhealth.apresentacao.vaadin",
    "dev.beckerhealth.aplicacao",
    "dev.beckerhealth.infraestrutura",
    "dev.beckerhealth.dominio"
})
@EntityScan(basePackages = {
    "dev.beckerhealth.infraestrutura.persistencia.jpa"
})
@EnableJpaRepositories(basePackages = {
    "dev.beckerhealth.infraestrutura.persistencia.jpa"
})
public class BeckerHealthApplication {

    @Bean
    @ConditionalOnBean(ConsultaRepositorioAplicacao.class)
    public ConsultaServicoAplicacao consultaServicoAplicacao(ConsultaRepositorioAplicacao repositorio) {
        return new ConsultaServicoAplicacao(repositorio);
    }

    @Bean
    @Primary
    @ConditionalOnBean(EventoBarramento.class)
    public ProcessamentoConsultaAgendada processamentoConsultaAgendada(EventoBarramento eventoBarramento) {
        return new ProcessamentoConsultaAgendada(eventoBarramento);
    }

    @Bean
    @ConditionalOnBean({ProntuarioRepository.class, ExameRepository.class, PrescricaoRepository.class})
    public ProntuarioService prontuarioService(ProntuarioRepository prontuarioRepository,
                                               ExameRepository exameRepository,
                                               PrescricaoRepository prescricaoRepository) {
        return new ProntuarioService(prontuarioRepository, exameRepository, prescricaoRepository);
    }

    public static void main(String[] args) {
        SpringApplication.run(BeckerHealthApplication.class, args);
    }
}
