package dev.beckerhealth.dominio.compartilhado.usuario;

import static org.apache.commons.lang3.Validate.notNull;

public class CrmFabrica {
    public Crm construir(String codigo) {
        notNull(codigo, "O código não pode ser nulo");
        if (codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("CRM não pode estar vazio");
        }
        return new Crm(codigo);
    }
}

