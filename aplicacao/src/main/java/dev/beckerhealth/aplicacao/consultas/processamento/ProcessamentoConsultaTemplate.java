package dev.beckerhealth.aplicacao.consultas.processamento;

import dev.beckerhealth.dominio.consultas.Consulta;

public abstract class ProcessamentoConsultaTemplate {
    public final void processar(Consulta consulta) {
        validarPreProcessamento(consulta);
        executarProcessamento(consulta);
        validarPosProcessamento(consulta);
        finalizarProcessamento(consulta);
    }

    protected void validarPreProcessamento(Consulta consulta) {
        if (consulta == null) {
            throw new IllegalArgumentException("Consulta não pode ser nula");
        }
    }

    protected abstract void executarProcessamento(Consulta consulta);

    protected void validarPosProcessamento(Consulta consulta) {
    }

    protected void finalizarProcessamento(Consulta consulta) {
    }
}
