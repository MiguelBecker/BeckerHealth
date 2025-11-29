package dev.beckerhealth.dominio.notificacao;

import static org.apache.commons.lang3.Validate.notNull;

import dev.beckerhealth.dominio.compartilhado.usuario.Usuario;

import java.time.LocalDateTime;

public class Notificacao {
    private Long id;
    private Usuario destinatario;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataEnvio;
    private LocalDateTime dataLeitura;
    private TipoNotificacao tipo;
    private StatusNotificacao status = StatusNotificacao.NAO_LIDA;
    private String link;

    public Notificacao() {
    }

    public Notificacao(Long id, Usuario destinatario, String titulo, String mensagem,
                       LocalDateTime dataEnvio, LocalDateTime dataLeitura, TipoNotificacao tipo,
                       StatusNotificacao status, String link) {
        notNull(destinatario, "O destinatário não pode ser nulo");
        notNull(titulo, "O título não pode ser nulo");
        notNull(mensagem, "A mensagem não pode ser nula");
        notNull(dataEnvio, "A data de envio não pode ser nula");
        notNull(tipo, "O tipo não pode ser nulo");
        notNull(status, "O status não pode ser nulo");
        
        this.id = id;
        this.destinatario = destinatario;
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.dataEnvio = dataEnvio;
        this.dataLeitura = dataLeitura;
        this.tipo = tipo;
        this.status = status;
        this.link = link;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Usuario destinatario) {
        notNull(destinatario, "O destinatário não pode ser nulo");
        this.destinatario = destinatario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        notNull(titulo, "O título não pode ser nulo");
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        notNull(mensagem, "A mensagem não pode ser nula");
        this.mensagem = mensagem;
    }

    public LocalDateTime getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(LocalDateTime dataEnvio) {
        notNull(dataEnvio, "A data de envio não pode ser nula");
        this.dataEnvio = dataEnvio;
    }

    public LocalDateTime getDataLeitura() {
        return dataLeitura;
    }

    public void setDataLeitura(LocalDateTime dataLeitura) {
        this.dataLeitura = dataLeitura;
    }

    public TipoNotificacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoNotificacao tipo) {
        notNull(tipo, "O tipo não pode ser nulo");
        this.tipo = tipo;
    }

    public StatusNotificacao getStatus() {
        return status;
    }

    public void setStatus(StatusNotificacao status) {
        notNull(status, "O status não pode ser nulo");
        this.status = status;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void marcarComoLida() {
        this.status = StatusNotificacao.LIDA;
        this.dataLeitura = LocalDateTime.now();
    }

    public enum TipoNotificacao {
        CONSULTA_AGENDADA, CONSULTA_CANCELADA, CONSULTA_REMARCADA,
        RESULTADO_EXAME, PRESCRICAO, LEMBRETE, ALERTA
    }

    public enum StatusNotificacao {
        NAO_LIDA, LIDA, ARQUIVADA
    }
}
