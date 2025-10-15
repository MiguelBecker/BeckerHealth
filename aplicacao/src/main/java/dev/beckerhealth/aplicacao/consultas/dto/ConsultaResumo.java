package dev.beckerhealth.aplicacao.consultas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
}

