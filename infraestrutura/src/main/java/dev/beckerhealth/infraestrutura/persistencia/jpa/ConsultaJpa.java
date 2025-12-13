package dev.beckerhealth.infraestrutura.persistencia.jpa;

import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "consultas")
public class ConsultaJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(optional = false)
    public PacienteJpa paciente;

    @ManyToOne(optional = false)
    public MedicoJpa medico;

    @Column(nullable = false)
    public LocalDate dataConsulta;

    @Column(nullable = false)
    public LocalTime horaConsulta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public TipoConsulta tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public StatusConsulta status;

    public enum TipoConsulta {
        ROTINA, RETORNO, URGENCIA
    }

    public enum StatusConsulta {
        AGENDADA, CONCLUIDA, CANCELADA
    }
}



