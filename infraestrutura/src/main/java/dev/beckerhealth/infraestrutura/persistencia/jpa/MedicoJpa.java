package dev.beckerhealth.infraestrutura.persistencia.jpa;

import jakarta.persistence.*;

@Entity
@Table(name = "medicos")
@PrimaryKeyJoinColumn(name = "usuario_id")
class MedicoJpa extends UsuarioJpa {
    @Column(unique = true, nullable = false, length = 20)
    String crm;

    @Column(nullable = false, length = 100)
    String especialidade;
}

