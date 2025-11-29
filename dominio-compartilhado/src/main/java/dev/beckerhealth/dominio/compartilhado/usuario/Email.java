package dev.beckerhealth.dominio.compartilhado.usuario;

import static org.apache.commons.lang3.Validate.notNull;

public class Email {
    private final String endereco;

    public Email(String endereco) {
        notNull(endereco, "O email não pode ser nulo");
        this.endereco = endereco;
    }

    public String getEndereco() {
        return endereco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return endereco.equals(email.endereco);
    }

    @Override
    public int hashCode() {
        return endereco.hashCode();
    }

    @Override
    public String toString() {
        return endereco;
    }
}

