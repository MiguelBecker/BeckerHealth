package dev.beckerhealth.aplicacao.consultas.validacao;

import dev.beckerhealth.dominio.consultas.Consulta;

import java.time.LocalDate;
import java.time.LocalTime;

public abstract class ValidacaoConsultaDecorator implements ValidacaoConsultaStrategy {
    protected final ValidacaoConsultaStrategy validacaoBase;

    public ValidacaoConsultaDecorator(ValidacaoConsultaStrategy validacaoBase) {
        this.validacaoBase = validacaoBase;
    }

    @Override
    public void validar(LocalDate dataConsulta, LocalTime horaConsulta, Consulta.TipoConsulta tipo) {
        validacaoBase.validar(dataConsulta, horaConsulta, tipo);
        validarAdicional(dataConsulta, horaConsulta, tipo);
    }

    protected abstract void validarAdicional(LocalDate dataConsulta, LocalTime horaConsulta, Consulta.TipoConsulta tipo);
}

