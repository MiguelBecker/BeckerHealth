package dev.beckerhealth.aplicacao.consultas.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ConsultaResumo {
    private Long id;
    private String pacienteNome;
    private String pacienteCpf;
    private String medicoNome;
    private String medicoEspecialidade;
    private LocalDate dataConsulta;
    private LocalTime horaConsulta;
    private String tipo;
    private String status;

    public ConsultaResumo() {}

    public ConsultaResumo(Long id, String pacienteNome, String pacienteCpf, String medicoNome,
                         String medicoEspecialidade, LocalDate dataConsulta, LocalTime horaConsulta,
                         String tipo, String status) {
        this.id = id;
        this.pacienteNome = pacienteNome;
        this.pacienteCpf = pacienteCpf;
        this.medicoNome = medicoNome;
        this.medicoEspecialidade = medicoEspecialidade;
        this.dataConsulta = dataConsulta;
        this.horaConsulta = horaConsulta;
        this.tipo = tipo;
        this.status = status;
    }

    // Getters
    public Long getId() { return id; }
    public String getPacienteNome() { return pacienteNome; }
    public String getPacienteCpf() { return pacienteCpf; }
    public String getMedicoNome() { return medicoNome; }
    public String getMedicoEspecialidade() { return medicoEspecialidade; }
    public LocalDate getDataConsulta() { return dataConsulta; }
    public LocalTime getHoraConsulta() { return horaConsulta; }
    public String getTipo() { return tipo; }
    public String getStatus() { return status; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setPacienteNome(String pacienteNome) { this.pacienteNome = pacienteNome; }
    public void setPacienteCpf(String pacienteCpf) { this.pacienteCpf = pacienteCpf; }
    public void setMedicoNome(String medicoNome) { this.medicoNome = medicoNome; }
    public void setMedicoEspecialidade(String medicoEspecialidade) { this.medicoEspecialidade = medicoEspecialidade; }
    public void setDataConsulta(LocalDate dataConsulta) { this.dataConsulta = dataConsulta; }
    public void setHoraConsulta(LocalTime horaConsulta) { this.horaConsulta = horaConsulta; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setStatus(String status) { this.status = status; }

    // Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String pacienteNome;
        private String pacienteCpf;
        private String medicoNome;
        private String medicoEspecialidade;
        private LocalDate dataConsulta;
        private LocalTime horaConsulta;
        private String tipo;
        private String status;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder pacienteNome(String pacienteNome) { this.pacienteNome = pacienteNome; return this; }
        public Builder pacienteCpf(String pacienteCpf) { this.pacienteCpf = pacienteCpf; return this; }
        public Builder medicoNome(String medicoNome) { this.medicoNome = medicoNome; return this; }
        public Builder medicoEspecialidade(String medicoEspecialidade) { this.medicoEspecialidade = medicoEspecialidade; return this; }
        public Builder dataConsulta(LocalDate dataConsulta) { this.dataConsulta = dataConsulta; return this; }
        public Builder horaConsulta(LocalTime horaConsulta) { this.horaConsulta = horaConsulta; return this; }
        public Builder tipo(String tipo) { this.tipo = tipo; return this; }
        public Builder status(String status) { this.status = status; return this; }

        public ConsultaResumo build() {
            return new ConsultaResumo(id, pacienteNome, pacienteCpf, medicoNome, medicoEspecialidade,
                                    dataConsulta, horaConsulta, tipo, status);
        }
    }
}

