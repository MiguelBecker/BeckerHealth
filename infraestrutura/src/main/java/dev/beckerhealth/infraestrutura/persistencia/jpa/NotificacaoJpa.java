package dev.beckerhealth.infraestrutura.persistencia.jpa;

import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "notificacoes")
public class NotificacaoJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(optional = false)
    public UsuarioJpa destinatario;

    @Column(nullable = false, length = 200)
    public String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    public String mensagem;

    @Column(nullable = false)
    public LocalDateTime dataEnvio;

    public LocalDateTime dataLeitura;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public TipoNotificacao tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public StatusNotificacao status;

    @Column(length = 500)
    public String link;

    public enum TipoNotificacao {
        CONSULTA_AGENDADA, CONSULTA_CANCELADA, CONSULTA_REMARCADA,
        RESULTADO_EXAME, PRESCRICAO, LEMBRETE, ALERTA
    }

    public enum StatusNotificacao {
        NAO_LIDA, LIDA, ARQUIVADA
    }

}

