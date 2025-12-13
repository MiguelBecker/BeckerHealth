package dev.beckerhealth.aplicacao.notificacao.dto;

import java.time.LocalDateTime;

public class NotificacaoResumo {
    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataEnvio;
    private String tipo;
    private String status;
    private String link;
    private boolean naoLida;

    public NotificacaoResumo() {}

    public NotificacaoResumo(Long id, String titulo, String mensagem, LocalDateTime dataEnvio,
                             String tipo, String status, String link, boolean naoLida) {
        this.id = id;
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.dataEnvio = dataEnvio;
        this.tipo = tipo;
        this.status = status;
        this.link = link;
        this.naoLida = naoLida;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }
    public LocalDateTime getDataEnvio() { return dataEnvio; }
    public void setDataEnvio(LocalDateTime dataEnvio) { this.dataEnvio = dataEnvio; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }
    public boolean isNaoLida() { return naoLida; }
    public void setNaoLida(boolean naoLida) { this.naoLida = naoLida; }
}

