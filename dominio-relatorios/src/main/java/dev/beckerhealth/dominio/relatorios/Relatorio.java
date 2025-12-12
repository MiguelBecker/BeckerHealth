package dev.beckerhealth.dominio.relatorios;

import static org.apache.commons.lang3.Validate.notNull;

import java.time.LocalDateTime;

public class Relatorio {
    private Long id;
    private String titulo;
    private String conteudo;
    private TipoRelatorio tipo;
    private Long geradoPorId;
    private LocalDateTime dataGeracao;
    private StatusRelatorio status = StatusRelatorio.GERADO;
    private String observacoes;

    public Relatorio() {
    }

    public Relatorio(Long id, String titulo, String conteudo, TipoRelatorio tipo,
                     Long geradoPorId, LocalDateTime dataGeracao, StatusRelatorio status,
                     String observacoes) {
        notNull(titulo, "O título não pode ser nulo");
        notNull(conteudo, "O conteúdo não pode ser nulo");
        notNull(tipo, "O tipo não pode ser nulo");
        notNull(dataGeracao, "A data de geração não pode ser nula");
        notNull(status, "O status não pode ser nulo");
        
        this.id = id;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.tipo = tipo;
        this.geradoPorId = geradoPorId;
        this.dataGeracao = dataGeracao;
        this.status = status;
        this.observacoes = observacoes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        notNull(titulo, "O título não pode ser nulo");
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        notNull(conteudo, "O conteúdo não pode ser nulo");
        this.conteudo = conteudo;
    }

    public TipoRelatorio getTipo() {
        return tipo;
    }

    public void setTipo(TipoRelatorio tipo) {
        notNull(tipo, "O tipo não pode ser nulo");
        this.tipo = tipo;
    }

    public Long getGeradoPorId() {
        return geradoPorId;
    }

    public void setGeradoPorId(Long geradoPorId) {
        this.geradoPorId = geradoPorId;
    }

    public LocalDateTime getDataGeracao() {
        return dataGeracao;
    }

    public void setDataGeracao(LocalDateTime dataGeracao) {
        notNull(dataGeracao, "A data de geração não pode ser nula");
        this.dataGeracao = dataGeracao;
    }

    public StatusRelatorio getStatus() {
        return status;
    }

    public void setStatus(StatusRelatorio status) {
        notNull(status, "O status não pode ser nulo");
        this.status = status;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public enum TipoRelatorio {
        CONSULTAS_POR_PERIODO, CONSULTAS_POR_MEDICO, CONSULTAS_POR_PACIENTE,
        PRONTUARIOS_POR_PERIODO, NOTIFICACOES_POR_PERIODO,
        ESTATISTICAS_GERAIS, FATURAMENTO
    }

    public enum StatusRelatorio {
        GERADO, PROCESSANDO, CONCLUIDO, ERRO
    }
}

