package dev.beckerhealth.dominio.consultas.evento;

import dev.beckerhealth.dominio.consultas.Consulta;
import dev.beckerhealth.dominio.consultas.ConsultaId;

import java.time.LocalDate;
import java.time.LocalTime;

public class ConsultaAgendadaEvento {
    private final ConsultaId consultaId;
    private final Long pacienteId;
    private final Long medicoId;
    private final LocalDate dataConsulta;
    private final LocalTime horaConsulta;
    private final Consulta.TipoConsulta tipo;

    public ConsultaAgendadaEvento(Consulta consulta) {
        this.consultaId = consulta.getId();
        this.pacienteId = consulta.getPaciente().getId();
        this.medicoId = consulta.getMedico().getId();
        this.dataConsulta = consulta.getDataConsulta();
        this.horaConsulta = consulta.getHoraConsulta();
        this.tipo = consulta.getTipo();
    }

    public ConsultaId getConsultaId() {
        return consultaId;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public Long getMedicoId() {
        return medicoId;
    }

    public LocalDate getDataConsulta() {
        return dataConsulta;
    }

    public LocalTime getHoraConsulta() {
        return horaConsulta;
    }

    public Consulta.TipoConsulta getTipo() {
        return tipo;
    }
}

