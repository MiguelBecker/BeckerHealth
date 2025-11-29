package dev.beckerhealth.infraestrutura.evento;

import dev.beckerhealth.dominio.compartilhado.evento.EventoBarramento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class EventoConfiguracao {
    @Autowired
    private EventoBarramento eventoBarramento;

    @Autowired
    private NotificacaoConsultaObservador notificacaoConsultaObservador;

    @PostConstruct
    public void configurarObservadores() {
        eventoBarramento.adicionar(notificacaoConsultaObservador);
    }
}

