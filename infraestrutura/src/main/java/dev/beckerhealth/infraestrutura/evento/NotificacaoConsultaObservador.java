package dev.beckerhealth.infraestrutura.evento;

import dev.beckerhealth.dominio.compartilhado.evento.EventoObservador;
import dev.beckerhealth.dominio.consultas.evento.ConsultaAgendadaEvento;
import dev.beckerhealth.dominio.notificacao.Notificacao;
import dev.beckerhealth.dominio.notificacao.NotificacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class NotificacaoConsultaObservador implements EventoObservador<ConsultaAgendadaEvento> {
    @Autowired
    private NotificacaoRepository notificacaoRepository;

    @Override
    public void observarEvento(ConsultaAgendadaEvento evento) {
        Notificacao notificacao = new Notificacao(
            null,
            null,
            "Consulta Agendada",
            String.format("Sua consulta foi agendada para %s às %s", 
                evento.getDataConsulta(), evento.getHoraConsulta()),
            LocalDateTime.now(),
            null,
            Notificacao.TipoNotificacao.CONSULTA_AGENDADA,
            Notificacao.StatusNotificacao.NAO_LIDA,
            "/consultas/" + evento.getConsultaId().getId()
        );
        notificacaoRepository.salvar(notificacao);
    }
}

