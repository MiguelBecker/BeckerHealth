package dev.beckerhealth.infraestrutura.persistencia.jpa;

import jakarta.persistence.*;
import dev.beckerhealth.dominio.consultas.ConsultaId;

import java.time.LocalDate;

@Entity
@Table(name = "exames")
public class ExameJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "consulta_id", nullable = false)
    public Long consultaId;

    @Column(nullable = false, length = 100)
    public String tipo;

    @Column(columnDefinition = "TEXT")
    public String resultado;

    @Column(name = "data_exame")
    public LocalDate dataExame;

    @Column(nullable = false)
    public Boolean liberado = false;
}

