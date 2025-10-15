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
public class ConsultaResumoExpandido {
    private Long id;
    private Long pacienteId;
    private String pacienteNome;
    private String pacienteCpf;
    private String pacienteEmail;
    private String pacienteConvenio;
    private Long medicoId;
    private String medicoNome;
    private String medicoCrm;
    private String medicoEspecialidade;
    private String medicoEmail;
    private LocalDate dataConsulta;
    private LocalTime horaConsulta;
    private String tipo;
    private String status;
}

