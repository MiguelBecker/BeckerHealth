package dev.beckerhealth.infraestrutura.persistencia.jpa;

import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "prescricoes")
public class PrescricaoJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(optional = false)
    public ProntuarioJpa prontuario;

    @Column(nullable = false, columnDefinition = "TEXT")
    public String medicamentos;

    @Column(columnDefinition = "TEXT")
    public String posologia;

    @Column(nullable = false)
    public LocalDate dataEmissao;

    @Column(nullable = false)
    public LocalDate dataValidade;

    @Column(nullable = false)
    public String assinaturaMedica;

    @Column(length = 500)
    public String observacoes;
}
