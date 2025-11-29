package dev.beckerhealth.aplicacao.consultas.validacao;

import dev.beckerhealth.dominio.consultas.Consulta;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ValidacaoConsultaStrategy {
    void validar(LocalDate dataConsulta, LocalTime horaConsulta, Consulta.TipoConsulta tipo);
}

