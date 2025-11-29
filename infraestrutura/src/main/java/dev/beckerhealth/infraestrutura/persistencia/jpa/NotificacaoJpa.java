package dev.beckerhealth.infraestrutura.persistencia.jpa;

import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

@Entity
@Table(name = "notificacoes")
class NotificacaoJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(optional = false)
    UsuarioJpa destinatario;

    @Column(nullable = false, length = 200)
    String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    String mensagem;

    @Column(nullable = false)
    LocalDateTime dataEnvio;

    LocalDateTime dataLeitura;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    TipoNotificacao tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    StatusNotificacao status;

    @Column(length = 500)
    String link;

    enum TipoNotificacao {
        CONSULTA_AGENDADA, CONSULTA_CANCELADA, CONSULTA_REMARCADA,
        RESULTADO_EXAME, PRESCRICAO, LEMBRETE, ALERTA
    }

    enum StatusNotificacao {
        NAO_LIDA, LIDA, ARQUIVADA
    }
}

interface NotificacaoJpaRepository extends JpaRepository<NotificacaoJpa, Long> {
    List<NotificacaoJpa> findByDestinatarioId(Long usuarioId);
    List<NotificacaoJpa> findByDestinatarioIdAndStatus(Long usuarioId, StatusNotificacao status);
    List<NotificacaoJpa> findByTipo(TipoNotificacao tipo);
    List<NotificacaoJpa> findByDataEnvioBetween(java.time.LocalDateTime dataInicio, java.time.LocalDateTime dataFim);
}

