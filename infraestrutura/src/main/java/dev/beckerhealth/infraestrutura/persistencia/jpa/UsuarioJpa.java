package dev.beckerhealth.infraestrutura.persistencia.jpa;

import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.JOINED)
class UsuarioJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true, nullable = false, length = 50)
    String login;

    @Column(nullable = false)
    String senha;

    @Column(nullable = false)
    String nome;

    String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    TipoUsuario tipo;

    enum TipoUsuario {
        ADMIN, MEDICO, PACIENTE, RECEPCIONISTA
    }
}

interface UsuarioJpaRepository extends JpaRepository<UsuarioJpa, Long> {
}

interface MedicoJpaRepository extends JpaRepository<MedicoJpa, Long> {
}

interface PacienteJpaRepository extends JpaRepository<PacienteJpa, Long> {
}

