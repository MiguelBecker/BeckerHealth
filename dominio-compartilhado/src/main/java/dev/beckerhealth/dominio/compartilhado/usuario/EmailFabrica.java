package dev.beckerhealth.dominio.compartilhado.usuario;

import static org.apache.commons.lang3.Validate.notNull;

public class EmailFabrica {
    public Email construir(String endereco) {
        notNull(endereco, "O endereço não pode ser nulo");
        if (endereco.trim().isEmpty()) {
            throw new IllegalArgumentException("Email não pode estar vazio");
        }
        if (!endereco.contains("@")) {
            throw new IllegalArgumentException("Email inválido");
        }
        return new Email(endereco);
    }
}

