package dev.beckerhealth.infraestrutura.evento;

import dev.beckerhealth.dominio.compartilhado.evento.EventoObservador;
import dev.beckerhealth.dominio.compartilhado.usuario.Paciente;
import dev.beckerhealth.dominio.consultas.evento.ConsultaAgendadaEvento;
import dev.beckerhealth.dominio.notificacao.Notificacao;
import dev.beckerhealth.dominio.notificacao.NotificacaoRepository;
import dev.beckerhealth.infraestrutura.persistencia.jpa.JpaMapeador;
import dev.beckerhealth.infraestrutura.persistencia.jpa.PacienteJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class NotificacaoConsultaObservador implements EventoObservador<ConsultaAgendadaEvento> {
    @Autowired
    private NotificacaoRepository notificacaoRepository;
    
    @Autowired
    private PacienteJpaRepository pacienteJpaRepository;
    
    @Autowired
    private JpaMapeador mapeador;

    @Override
    public void observarEvento(ConsultaAgendadaEvento evento) {
        // Buscar o paciente do banco de dados
        var pacienteJpa = pacienteJpaRepository.findById(evento.getPacienteId())
                .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado com ID: " + evento.getPacienteId()));
        
        // Converter PacienteJpa para Paciente (que é um Usuario)
        Paciente paciente = mapeador.map(pacienteJpa, Paciente.class);
        
        // Criar a notificação com o paciente como destinatário
        Notificacao notificacao = new Notificacao(
            null,
            paciente, // Paciente é um Usuario, então pode ser usado como destinatário
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

