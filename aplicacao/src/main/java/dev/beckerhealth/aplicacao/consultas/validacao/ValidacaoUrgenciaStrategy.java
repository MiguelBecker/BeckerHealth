package dev.beckerhealth.aplicacao.consultas.validacao;

import dev.beckerhealth.dominio.consultas.Consulta;

import java.time.LocalDate;
import java.time.LocalTime;

public class ValidacaoUrgenciaStrategy implements ValidacaoConsultaStrategy {
    @Override
    public void validar(LocalDate dataConsulta, LocalTime horaConsulta, Consulta.TipoConsulta tipo) {
        if (tipo == Consulta.TipoConsulta.URGENCIA) {
            if (dataConsulta == null) {
                throw new IllegalArgumentException("A data da consulta não pode ser nula");
            }
            if (dataConsulta.isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("A data da consulta não pode ser no passado");
            }
        }
    }
}
