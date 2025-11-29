package dev.beckerhealth.aplicacao.consultas.validacao;

import dev.beckerhealth.dominio.consultas.Consulta;

import java.time.LocalDate;
import java.time.LocalTime;

public class ValidacaoHorarioStrategy implements ValidacaoConsultaStrategy {
    @Override
    public void validar(LocalDate dataConsulta, LocalTime horaConsulta, Consulta.TipoConsulta tipo) {
        if (dataConsulta == null) {
            throw new IllegalArgumentException("A data da consulta não pode ser nula");
        }

        if (horaConsulta == null) {
            throw new IllegalArgumentException("A hora da consulta não pode ser nula");
        }

        if (dataConsulta.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("A data da consulta não pode ser no passado");
        }

        if (horaConsulta.isBefore(LocalTime.of(8, 0)) || horaConsulta.isAfter(LocalTime.of(18, 0))) {
            throw new IllegalArgumentException("O horário da consulta deve estar entre 8h e 18h");
        }
    }
}

