package dev.beckerhealth.aplicacao.prontuario.dto;

import java.time.LocalDateTime;

public class ProntuarioResumo {
    private Long id;
    private String pacienteNome;
    private String pacienteCpf;
    private String medicoNome;
    private String medicoEspecialidade;
    private String anamnese;
    private String diagnostico;
    private String prescricao;
    private LocalDateTime dataAtendimento;
    private String tipoAtendimento;
    private String observacoes;

    public ProntuarioResumo() {}

    public ProntuarioResumo(Long id, String pacienteNome, String pacienteCpf, String medicoNome,
                           String medicoEspecialidade, String anamnese, String diagnostico,
                           String prescricao, LocalDateTime dataAtendimento, String tipoAtendimento,
                           String observacoes) {
        this.id = id;
        this.pacienteNome = pacienteNome;
        this.pacienteCpf = pacienteCpf;
        this.medicoNome = medicoNome;
        this.medicoEspecialidade = medicoEspecialidade;
        this.anamnese = anamnese;
        this.diagnostico = diagnostico;
        this.prescricao = prescricao;
        this.dataAtendimento = dataAtendimento;
        this.tipoAtendimento = tipoAtendimento;
        this.observacoes = observacoes;
    }

    public Long getId() { return id; }
    public String getPacienteNome() { return pacienteNome; }
    public String getPacienteCpf() { return pacienteCpf; }
    public String getMedicoNome() { return medicoNome; }
    public String getMedicoEspecialidade() { return medicoEspecialidade; }
    public String getAnamnese() { return anamnese; }
    public String getDiagnostico() { return diagnostico; }
    public String getPrescricao() { return prescricao; }
    public LocalDateTime getDataAtendimento() { return dataAtendimento; }
    public String getTipoAtendimento() { return tipoAtendimento; }
    public String getObservacoes() { return observacoes; }

    public void setId(Long id) { this.id = id; }
    public void setPacienteNome(String pacienteNome) { this.pacienteNome = pacienteNome; }
    public void setPacienteCpf(String pacienteCpf) { this.pacienteCpf = pacienteCpf; }
    public void setMedicoNome(String medicoNome) { this.medicoNome = medicoNome; }
    public void setMedicoEspecialidade(String medicoEspecialidade) { this.medicoEspecialidade = medicoEspecialidade; }
    public void setAnamnese(String anamnese) { this.anamnese = anamnese; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }
    public void setPrescricao(String prescricao) { this.prescricao = prescricao; }
    public void setDataAtendimento(LocalDateTime dataAtendimento) { this.dataAtendimento = dataAtendimento; }
    public void setTipoAtendimento(String tipoAtendimento) { this.tipoAtendimento = tipoAtendimento; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String pacienteNome;
        private String pacienteCpf;
        private String medicoNome;
        private String medicoEspecialidade;
        private String anamnese;
        private String diagnostico;
        private String prescricao;
        private LocalDateTime dataAtendimento;
        private String tipoAtendimento;
        private String observacoes;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder pacienteNome(String pacienteNome) { this.pacienteNome = pacienteNome; return this; }
        public Builder pacienteCpf(String pacienteCpf) { this.pacienteCpf = pacienteCpf; return this; }
        public Builder medicoNome(String medicoNome) { this.medicoNome = medicoNome; return this; }
        public Builder medicoEspecialidade(String medicoEspecialidade) { this.medicoEspecialidade = medicoEspecialidade; return this; }
        public Builder anamnese(String anamnese) { this.anamnese = anamnese; return this; }
        public Builder diagnostico(String diagnostico) { this.diagnostico = diagnostico; return this; }
        public Builder prescricao(String prescricao) { this.prescricao = prescricao; return this; }
        public Builder dataAtendimento(LocalDateTime dataAtendimento) { this.dataAtendimento = dataAtendimento; return this; }
        public Builder tipoAtendimento(String tipoAtendimento) { this.tipoAtendimento = tipoAtendimento; return this; }
        public Builder observacoes(String observacoes) { this.observacoes = observacoes; return this; }

        public ProntuarioResumo build() {
            return new ProntuarioResumo(id, pacienteNome, pacienteCpf, medicoNome, medicoEspecialidade,
                                      anamnese, diagnostico, prescricao, dataAtendimento, tipoAtendimento, observacoes);
        }
    }
}


