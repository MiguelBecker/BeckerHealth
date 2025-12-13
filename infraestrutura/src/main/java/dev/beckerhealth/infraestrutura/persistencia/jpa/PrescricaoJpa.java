package dev.beckerhealth.infraestrutura.persistencia.jpa;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "prescricoes")
public class PrescricaoJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "prontuario_id", nullable = false)
    public Long prontuarioId;

    @Column(nullable = false, columnDefinition = "TEXT")
    public String conteudo;

    @Column(nullable = false)
    public LocalDate validade;

    @Column(name = "assinatura_medica", nullable = false, length = 255)
    public String assinaturaMedica;
}

