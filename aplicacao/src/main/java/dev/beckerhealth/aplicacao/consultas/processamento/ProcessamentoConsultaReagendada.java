package dev.beckerhealth.aplicacao.consultas.processamento;

import org.springframework.stereotype.Component;

import dev.beckerhealth.dominio.consultas.Consulta;
import dev.beckerhealth.dominio.compartilhado.evento.EventoBarramento;
import dev.beckerhealth.dominio.consultas.evento.ConsultaAgendadaEvento;

@Component
public class ProcessamentoConsultaReagendada extends ProcessamentoConsultaTemplate {
    private final EventoBarramento eventoBarramento;

    public ProcessamentoConsultaReagendada(EventoBarramento eventoBarramento) {
        this.eventoBarramento = eventoBarramento;
    }

    @Override
    protected void executarProcessamento(Consulta consulta) {
        // Reutilizar o evento de consulta agendada para reagendamento
        ConsultaAgendadaEvento evento = new ConsultaAgendadaEvento(consulta);
        eventoBarramento.postar(evento);
    }

    @Override
    protected void validarPosProcessamento(Consulta consulta) {
        // Validar que a consulta foi reagendada corretamente
        if (consulta.getStatus() != Consulta.StatusConsulta.AGENDADA) {
            throw new IllegalStateException("Consulta não foi reagendada corretamente");
        }
    }
}

