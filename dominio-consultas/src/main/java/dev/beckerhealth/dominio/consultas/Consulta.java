package dev.beckerhealth.dominio.consultas;

import static org.apache.commons.lang3.Validate.notNull;

import dev.beckerhealth.dominio.compartilhado.usuario.Medico;
import dev.beckerhealth.dominio.compartilhado.usuario.Paciente;

import java.time.LocalDate;
import java.time.LocalTime;

public class Consulta {
    private ConsultaId id;
    private Paciente paciente;
    private Medico medico;
    private LocalDate dataConsulta;
    private LocalTime horaConsulta;
    private TipoConsulta tipo;
    private StatusConsulta status = StatusConsulta.AGENDADA;

    public Consulta() {
    }

    public Consulta(ConsultaId id, Paciente paciente, Medico medico, LocalDate dataConsulta, 
                     LocalTime horaConsulta, TipoConsulta tipo, StatusConsulta status) {
        notNull(paciente, "O paciente não pode ser nulo");
        notNull(medico, "O médico não pode ser nulo");
        notNull(dataConsulta, "A data da consulta não pode ser nula");
        notNull(horaConsulta, "A hora da consulta não pode ser nula");
        notNull(tipo, "O tipo da consulta não pode ser nulo");
        notNull(status, "O status da consulta não pode ser nulo");
        
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.dataConsulta = dataConsulta;
        this.horaConsulta = horaConsulta;
        this.tipo = tipo;
        this.status = status;
    }

    public ConsultaId getId() {
        return id;
    }

    public void setId(ConsultaId id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        notNull(paciente, "O paciente não pode ser nulo");
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        notNull(medico, "O médico não pode ser nulo");
        this.medico = medico;
    }

    public LocalDate getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(LocalDate dataConsulta) {
        notNull(dataConsulta, "A data da consulta não pode ser nula");
        this.dataConsulta = dataConsulta;
    }

    public LocalTime getHoraConsulta() {
        return horaConsulta;
    }

    public void setHoraConsulta(LocalTime horaConsulta) {
        notNull(horaConsulta, "A hora da consulta não pode ser nula");
        this.horaConsulta = horaConsulta;
    }

    public TipoConsulta getTipo() {
        return tipo;
    }

    public void setTipo(TipoConsulta tipo) {
        notNull(tipo, "O tipo da consulta não pode ser nulo");
        this.tipo = tipo;
    }

    public StatusConsulta getStatus() {
        return status;
    }

    public void setStatus(StatusConsulta status) {
        notNull(status, "O status da consulta não pode ser nulo");
        this.status = status;
    }

    public enum TipoConsulta {
        ROTINA, RETORNO, URGENCIA
    }

    public enum StatusConsulta {
        AGENDADA, CONCLUIDA, CANCELADA
    }
}

