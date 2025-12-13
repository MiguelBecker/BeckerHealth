package dev.beckerhealth.infraestrutura.persistencia.jpa;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "pacientes")
@PrimaryKeyJoinColumn(name = "usuario_id")
public class PacienteJpa extends UsuarioJpa {
    @Column(unique = true, nullable = false, length = 14)
    public String cpf;

    public LocalDate dataNascimento;

    public String convenio;
}

