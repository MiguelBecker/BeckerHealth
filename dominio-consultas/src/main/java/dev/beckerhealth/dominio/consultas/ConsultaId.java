package dev.beckerhealth.dominio.consultas;

import static org.apache.commons.lang3.Validate.notNull;

public class ConsultaId {
    private final Long id;

    public ConsultaId(Long id) {
        notNull(id, "O id não pode ser nulo");
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConsultaId that = (ConsultaId) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return id.toString();
    }
}

