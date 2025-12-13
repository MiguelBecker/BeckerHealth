package dev.beckerhealth.infraestrutura.persistencia.jpa;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "consultas")
public class ConsultaJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "paciente_id", nullable = false)
    public PacienteJpa paciente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "medico_id", nullable = false)
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



