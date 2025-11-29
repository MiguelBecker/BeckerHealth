package dev.beckerhealth.dominio.compartilhado.usuario;

import static org.apache.commons.lang3.Validate.notNull;

public class Crm {
    private final String codigo;

    public Crm(String codigo) {
        notNull(codigo, "O CRM não pode ser nulo");
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Crm crm = (Crm) o;
        return codigo.equals(crm.codigo);
    }

    @Override
    public int hashCode() {
        return codigo.hashCode();
    }

    @Override
    public String toString() {
        return codigo;
    }
}

