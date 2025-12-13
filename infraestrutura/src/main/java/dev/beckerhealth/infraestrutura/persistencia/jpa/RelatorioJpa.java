package dev.beckerhealth.infraestrutura.persistencia.jpa;

import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "relatorios")
public class RelatorioJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false, length = 200)
    public String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    public String conteudo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public TipoRelatorio tipo;

    @ManyToOne
    public UsuarioJpa geradoPor;

    @Column(nullable = false)
    public LocalDateTime dataGeracao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public StatusRelatorio status;

    @Column(length = 500)
    public String observacoes;

    public enum TipoRelatorio {
        CONSULTAS_POR_PERIODO, CONSULTAS_POR_MEDICO, CONSULTAS_POR_PACIENTE,
        PRONTUARIOS_POR_PERIODO, NOTIFICACOES_POR_PERIODO,
        ESTATISTICAS_GERAIS, FATURAMENTO
    }

    public enum StatusRelatorio {
        GERADO, PROCESSANDO, CONCLUIDO, ERRO
    }

}

