package dev.beckerhealth.infraestrutura.persistencia.jpa;

import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "consultas")
class ConsultaJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(optional = false)
    PacienteJpa paciente;

    @ManyToOne(optional = false)
    MedicoJpa medico;

    @Column(nullable = false)
    LocalDate dataConsulta;

    @Column(nullable = false)
    LocalTime horaConsulta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    TipoConsulta tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    StatusConsulta status;

    enum TipoConsulta {
        ROTINA, RETORNO, URGENCIA
    }

    enum StatusConsulta {
        AGENDADA, CONCLUIDA, CANCELADA
    }
}

interface ConsultaJpaRepository extends JpaRepository<ConsultaJpa, Long> {
    List<ConsultaJpa> findByDataConsulta(LocalDate data);
    List<ConsultaJpa> findByStatus(StatusConsulta status);
    List<ConsultaJpa> findByPacienteId(Long pacienteId);
    List<ConsultaJpa> findByMedicoId(Long medicoId);
}

