package dev.beckerhealth.aplicacao.consultas.processamento;

import org.springframework.stereotype.Component;

import dev.beckerhealth.dominio.consultas.Consulta;
import dev.beckerhealth.dominio.compartilhado.evento.EventoBarramento;
import dev.beckerhealth.dominio.consultas.evento.ConsultaCanceladaEvento;

@Component
public class ProcessamentoConsultaCancelada extends ProcessamentoConsultaTemplate {
    private final EventoBarramento eventoBarramento;

    public ProcessamentoConsultaCancelada(EventoBarramento eventoBarramento) {
        this.eventoBarramento = eventoBarramento;
    }

    @Override
    protected void executarProcessamento(Consulta consulta) {
        ConsultaCanceladaEvento evento = new ConsultaCanceladaEvento(
            consulta.getId(),
            consulta.getPaciente() != null ? consulta.getPaciente().getId() : null,
            "Consulta cancelada pelo paciente"
        );
        eventoBarramento.postar(evento);
    }

    @Override
    protected void validarPosProcessamento(Consulta consulta) {
        // Validar que a consulta foi cancelada corretamente
        if (consulta.getStatus() != Consulta.StatusConsulta.CANCELADA) {
            throw new IllegalStateException("Consulta não foi cancelada corretamente");
        }
    }
}

