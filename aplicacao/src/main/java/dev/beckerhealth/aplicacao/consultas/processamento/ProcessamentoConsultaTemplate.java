package dev.beckerhealth.aplicacao.consultas.processamento;

import dev.beckerhealth.dominio.consultas.Consulta;

public abstract class ProcessamentoConsultaTemplate {
    
    // Template Method
    public final void processar(Consulta consulta) {
        validarPreProcessamento(consulta);
        executarProcessamento(consulta);
        validarPosProcessamento(consulta);
        finalizarProcessamento(consulta);
    }

    // Hook methods - podem ser sobrescritos pelas subclasses
    protected void validarPreProcessamento(Consulta consulta) {
        // Validação padrão
        if (consulta == null) {
            throw new IllegalArgumentException("Consulta não pode ser nula");
        }
    }

    // Método abstrato - deve ser implementado pelas subclasses
    protected abstract void executarProcessamento(Consulta consulta);

    // Hook method opcional
    protected void validarPosProcessamento(Consulta consulta) {
        // Validação padrão vazia
    }

    // Hook method opcional
    protected void finalizarProcessamento(Consulta consulta) {
        // Finalização padrão vazia
    }
}

