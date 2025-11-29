package dev.beckerhealth.dominio.prontuario;

import static org.apache.commons.lang3.Validate.notNull;

import dev.beckerhealth.dominio.compartilhado.usuario.Medico;
import dev.beckerhealth.dominio.compartilhado.usuario.Paciente;

import java.time.LocalDateTime;

public class Prontuario {
    private Long id;
    private Paciente paciente;
    private Medico medicoResponsavel;
    private String anamnese;
    private String diagnostico;
    private String prescricao;
    private LocalDateTime dataAtendimento;
    private TipoAtendimento tipoAtendimento;
    private String observacoes;

    public Prontuario() {
    }

    public Prontuario(Long id, Paciente paciente, Medico medicoResponsavel, String anamnese,
                      String diagnostico, String prescricao, LocalDateTime dataAtendimento,
                      TipoAtendimento tipoAtendimento, String observacoes) {
        notNull(paciente, "O paciente não pode ser nulo");
        notNull(anamnese, "A anamnese não pode ser nula");
        notNull(dataAtendimento, "A data de atendimento não pode ser nula");
        notNull(tipoAtendimento, "O tipo de atendimento não pode ser nulo");
        
        this.id = id;
        this.paciente = paciente;
        this.medicoResponsavel = medicoResponsavel;
        this.anamnese = anamnese;
        this.diagnostico = diagnostico;
        this.prescricao = prescricao;
        this.dataAtendimento = dataAtendimento;
        this.tipoAtendimento = tipoAtendimento;
        this.observacoes = observacoes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        notNull(paciente, "O paciente não pode ser nulo");
        this.paciente = paciente;
    }

    public Medico getMedicoResponsavel() {
        return medicoResponsavel;
    }

    public void setMedicoResponsavel(Medico medicoResponsavel) {
        this.medicoResponsavel = medicoResponsavel;
    }

    public String getAnamnese() {
        return anamnese;
    }

    public void setAnamnese(String anamnese) {
        notNull(anamnese, "A anamnese não pode ser nula");
        this.anamnese = anamnese;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getPrescricao() {
        return prescricao;
    }

    public void setPrescricao(String prescricao) {
        this.prescricao = prescricao;
    }

    public LocalDateTime getDataAtendimento() {
        return dataAtendimento;
    }

    public void setDataAtendimento(LocalDateTime dataAtendimento) {
        notNull(dataAtendimento, "A data de atendimento não pode ser nula");
        this.dataAtendimento = dataAtendimento;
    }

    public TipoAtendimento getTipoAtendimento() {
        return tipoAtendimento;
    }

    public void setTipoAtendimento(TipoAtendimento tipoAtendimento) {
        notNull(tipoAtendimento, "O tipo de atendimento não pode ser nulo");
        this.tipoAtendimento = tipoAtendimento;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public enum TipoAtendimento {
        CONSULTA, RETORNO, URGENCIA, EMERGENCIA
    }
}
