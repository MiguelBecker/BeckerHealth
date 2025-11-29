package dev.beckerhealth.aplicacao.consultas.validacao;

import dev.beckerhealth.dominio.consultas.Consulta;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class ValidacaoFimDeSemanaDecorator extends ValidacaoConsultaDecorator {
    public ValidacaoFimDeSemanaDecorator(ValidacaoConsultaStrategy validacaoBase) {
        super(validacaoBase);
    }

    @Override
    protected void validarAdicional(LocalDate dataConsulta, LocalTime horaConsulta, Consulta.TipoConsulta tipo) {
        DayOfWeek diaSemana = dataConsulta.getDayOfWeek();
        if (diaSemana == DayOfWeek.SATURDAY || diaSemana == DayOfWeek.SUNDAY) {
            if (tipo != Consulta.TipoConsulta.URGENCIA) {
                throw new IllegalArgumentException("Consultas de rotina e retorno não podem ser agendadas em fins de semana");
            }
        }
    }
}

