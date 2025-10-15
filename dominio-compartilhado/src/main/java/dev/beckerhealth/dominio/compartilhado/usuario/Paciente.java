package dev.beckerhealth.dominio.compartilhado.usuario;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "pacientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Paciente extends Usuario {

    @Column(unique = true, nullable = false, length = 14)
    private String cpf;

    private LocalDate dataNascimento;

    private String convenio;
}

