package dev.beckerhealth.dominio.consultas.evento;

import dev.beckerhealth.dominio.consultas.ConsultaId;

public class ConsultaCanceladaEvento {
    private final ConsultaId consultaId;
    private final Long pacienteId;
    private final String motivo;

    public ConsultaCanceladaEvento(ConsultaId consultaId, Long pacienteId, String motivo) {
        this.consultaId = consultaId;
        this.pacienteId = pacienteId;
        this.motivo = motivo;
    }

    public ConsultaId getConsultaId() {
        return consultaId;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public String getMotivo() {
        return motivo;
    }
}

