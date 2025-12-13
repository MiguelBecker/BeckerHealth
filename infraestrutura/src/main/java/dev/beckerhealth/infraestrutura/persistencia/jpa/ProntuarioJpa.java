package dev.beckerhealth.infraestrutura.persistencia.jpa;

import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "prontuarios")
public class ProntuarioJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(optional = false)
    public PacienteJpa paciente;

    @ManyToOne
    public MedicoJpa medicoResponsavel;

    @Column(nullable = false, columnDefinition = "TEXT")
    public String anamnese;

    @Column(columnDefinition = "TEXT")
    public String diagnostico;

    @Column(columnDefinition = "TEXT")
    public String prescricao;

    @Column(nullable = false)
    public LocalDateTime dataAtendimento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public TipoAtendimento tipoAtendimento;

    @Column(length = 500)
    public String observacoes;

    public enum TipoAtendimento {
        CONSULTA, RETORNO, URGENCIA, EMERGENCIA
    }

}

