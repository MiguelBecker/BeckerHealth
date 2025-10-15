package dev.beckerhealth.apresentacao.backend.consultas;

import dev.beckerhealth.dominio.consultas.Consulta;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AgendarConsultaForm {

    @NotNull
    private Long pacienteId;

    @NotNull
    private Long medicoId;

    @NotNull
    private LocalDate dataConsulta;

    @NotNull
    private LocalTime horaConsulta;

    @NotNull
    private Consulta.TipoConsulta tipo;
}

