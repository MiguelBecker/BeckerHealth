package dev.beckerhealth.dominio.compartilhado.usuario;

import static org.apache.commons.lang3.Validate.notNull;

public class CpfFabrica {
    public Cpf construir(String codigo) {
        notNull(codigo, "O código não pode ser nulo");
        if (codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF não pode estar vazio");
        }
        return new Cpf(codigo);
    }
}

