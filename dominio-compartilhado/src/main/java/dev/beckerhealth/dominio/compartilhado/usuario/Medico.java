package dev.beckerhealth.dominio.compartilhado.usuario;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "medicos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Medico extends Usuario {

    @Column(unique = true, nullable = false, length = 20)
    private String crm;

    @Column(nullable = false, length = 100)
    private String especialidade;
}

