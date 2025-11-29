package dev.beckerhealth.apresentacao.backend.consultas;

import dev.beckerhealth.aplicacao.consultas.dto.ConsultaResumo;

import java.util.List;

public class AgendarConsultaForm {
    public ConsultaDto consulta;
    public List<ConsultaResumo> consultasExistentes;

    public AgendarConsultaForm(ConsultaDto consulta, List<ConsultaResumo> consultasExistentes) {
        this.consulta = consulta;
        this.consultasExistentes = consultasExistentes;
    }

    public AgendarConsultaForm() {
        this.consulta = new ConsultaDto();
    }

    public static class ConsultaDto {
        public Long pacienteId;
        public Long medicoId;
        public java.time.LocalDate dataConsulta;
        public java.time.LocalTime horaConsulta;
        public dev.beckerhealth.dominio.consultas.Consulta.TipoConsulta tipo;
    }
}
