package dev.beckerhealth.dominio.prontuario;

import static org.apache.commons.lang3.Validate.notNull;

import java.time.LocalDateTime;

public class Exame {
    private Long id;
    private Prontuario prontuario;
    private Long consultaId;
    private String nomeExame;
    private String descricao;
    private LocalDateTime dataSolicitacao;
    private String resultado;
    private StatusExame status;
    private LocalDateTime dataLiberacao;
    private String observacoesMedico;

    public Exame() {
    }

    public Exame(Long id, Prontuario prontuario, Long consultaId,
                 String nomeExame, String descricao, LocalDateTime dataSolicitacao,
                 String resultado, StatusExame status, LocalDateTime dataLiberacao,
                 String observacoesMedico) {
        notNull(prontuario, "O prontuário não pode ser nulo");
        notNull(consultaId, "O ID da consulta vinculada não pode ser nulo");
        notNull(nomeExame, "O nome do exame não pode ser nulo");
        notNull(dataSolicitacao, "A data de solicitação não pode ser nula");

        this.id = id;
        this.prontuario = prontuario;
        this.consultaId = consultaId;
        this.nomeExame = nomeExame;
        this.descricao = descricao;
        this.dataSolicitacao = dataSolicitacao;
        this.resultado = resultado;
        this.status = status != null ? status : StatusExame.PENDENTE;
        this.dataLiberacao = dataLiberacao;
        this.observacoesMedico = observacoesMedico;
    }

    // Getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Prontuario getProntuario() { return prontuario; }
    public void setProntuario(Prontuario prontuario) {
        notNull(prontuario, "O prontuário não pode ser nulo");
        this.prontuario = prontuario;
    }

    public Long getConsultaId() { return consultaId; }
    public void setConsultaId(Long consultaId) {
        notNull(consultaId, "O ID da consulta vinculada não pode ser nulo");
        this.consultaId = consultaId;
    }

    public String getNomeExame() { return nomeExame; }
    public void setNomeExame(String nomeExame) {
        notNull(nomeExame, "O nome do exame não pode ser nulo");
        this.nomeExame = nomeExame;
    }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public LocalDateTime getDataSolicitacao() { return dataSolicitacao; }
    public void setDataSolicitacao(LocalDateTime dataSolicitacao) {
        notNull(dataSolicitacao, "A data de solicitação não pode ser nula");
        this.dataSolicitacao = dataSolicitacao;
    }

    public String getResultado() { return resultado; }
    public void setResultado(String resultado) { this.resultado = resultado; }

    public StatusExame getStatus() { return status; }
    public void setStatus(StatusExame status) { this.status = status; }

    public LocalDateTime getDataLiberacao() { return dataLiberacao; }
    public void setDataLiberacao(LocalDateTime dataLiberacao) { this.dataLiberacao = dataLiberacao; }

    public String getObservacoesMedico() { return observacoesMedico; }
    public void setObservacoesMedico(String observacoesMedico) { this.observacoesMedico = observacoesMedico; }

    public boolean isLiberado() {
        return status == StatusExame.LIBERADO;
    }

    public void liberarResultado(String resultado, String observacoes) {
        this.resultado = resultado;
        this.observacoesMedico = observacoes;
        this.status = StatusExame.LIBERADO;
        this.dataLiberacao = LocalDateTime.now();
    }

    public enum StatusExame {
        PENDENTE, EM_ANDAMENTO, LIBERADO, CANCELADO
    }
}
