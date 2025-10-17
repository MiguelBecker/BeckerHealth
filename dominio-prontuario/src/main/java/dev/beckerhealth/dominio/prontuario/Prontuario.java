package dev.beckerhealth.dominio.prontuario;

import dev.beckerhealth.dominio.compartilhado.usuario.Medico;
import dev.beckerhealth.dominio.compartilhado.usuario.Paciente;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "prontuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prontuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Paciente paciente;

    @ManyToOne
    private Medico medicoResponsavel;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String anamnese;

    @Column(columnDefinition = "TEXT")
    private String diagnostico;

    @Column(columnDefinition = "TEXT")
    private String prescricao;

    @Column(nullable = false)
    private LocalDateTime dataAtendimento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoAtendimento tipoAtendimento;

    @Column(length = 500)
    private String observacoes;

    public enum TipoAtendimento {
        CONSULTA, RETORNO, URGENCIA, EMERGENCIA
    }
}
