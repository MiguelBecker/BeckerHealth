package com.br.beckerhealth.bdd_projeto.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "consultas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Paciente paciente;

    @ManyToOne(optional = false)
    private Medico medico;

    @Column(nullable = false)
    private LocalDate dataConsulta;

    @Column(nullable = false)
    private LocalTime horaConsulta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoConsulta tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusConsulta status = StatusConsulta.AGENDADA;

    public enum TipoConsulta {
        ROTINA, RETORNO, URGENCIA
    }

    public enum StatusConsulta {
        AGENDADA, CONCLUIDA, CANCELADA
    }
}
