package dev.beckerhealth.infraestrutura.persistencia.jpa;

import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

@Entity
@Table(name = "prontuarios")
class ProntuarioJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(optional = false)
    PacienteJpa paciente;

    @ManyToOne
    MedicoJpa medicoResponsavel;

    @Column(nullable = false, columnDefinition = "TEXT")
    String anamnese;

    @Column(columnDefinition = "TEXT")
    String diagnostico;

    @Column(columnDefinition = "TEXT")
    String prescricao;

    @Column(nullable = false)
    LocalDateTime dataAtendimento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    TipoAtendimento tipoAtendimento;

    @Column(length = 500)
    String observacoes;

    enum TipoAtendimento {
        CONSULTA, RETORNO, URGENCIA, EMERGENCIA
    }
}

interface ProntuarioJpaRepository extends JpaRepository<ProntuarioJpa, Long> {
    List<ProntuarioJpa> findByPacienteId(Long pacienteId);
    List<ProntuarioJpa> findByMedicoResponsavelId(Long medicoId);
    List<ProntuarioJpa> findByDataAtendimentoBetween(java.time.LocalDateTime dataInicio, java.time.LocalDateTime dataFim);
}

