package dev.beckerhealth.infraestrutura.persistencia.jpa;

import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.JOINED)
public class UsuarioJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(unique = true, nullable = false, length = 50)
    public String login;

    @Column(nullable = false)
    public String senha;

    @Column(nullable = false)
    public String nome;

    public String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public TipoUsuario tipo;

    public enum TipoUsuario {
        ADMIN, MEDICO, PACIENTE, RECEPCIONISTA
    }
}

