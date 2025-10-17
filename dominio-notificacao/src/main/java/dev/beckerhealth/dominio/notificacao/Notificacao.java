package dev.beckerhealth.dominio.notificacao;

import dev.beckerhealth.dominio.compartilhado.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notificacoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Usuario destinatario;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensagem;

    @Column(nullable = false)
    private LocalDateTime dataEnvio;

    private LocalDateTime dataLeitura;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoNotificacao tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusNotificacao status = StatusNotificacao.NAO_LIDA;

    @Column(length = 500)
    private String link;

    public enum TipoNotificacao {
        CONSULTA_AGENDADA, CONSULTA_CANCELADA, CONSULTA_REMARCADA,
        RESULTADO_EXAME, PRESCRICAO, LEMBRETE, ALERTA
    }

    public enum StatusNotificacao {
        NAO_LIDA, LIDA, ARQUIVADA
    }

    public void marcarComoLida() {
        this.status = StatusNotificacao.LIDA;
        this.dataLeitura = LocalDateTime.now();
    }
}
