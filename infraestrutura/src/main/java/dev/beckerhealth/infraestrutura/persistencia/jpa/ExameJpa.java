package dev.beckerhealth.infraestrutura.persistencia.jpa;

import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "exames")
public class ExameJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(optional = false)
    public ProntuarioJpa prontuario;

    @Column(nullable = false)
    public Long consultaId;

    @Column(nullable = false)
    public String nomeExame;

    @Column(columnDefinition = "TEXT")
    public String descricao;

    @Column(nullable = false)
    public LocalDateTime dataSolicitacao;

    @Column(columnDefinition = "TEXT")
    public String resultado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public StatusExame status = StatusExame.PENDENTE;

    public LocalDateTime dataLiberacao;

    @Column(length = 500)
    public String observacoesMedico;

    public enum StatusExame {
        PENDENTE, EM_ANDAMENTO, LIBERADO, CANCELADO
    }
}
