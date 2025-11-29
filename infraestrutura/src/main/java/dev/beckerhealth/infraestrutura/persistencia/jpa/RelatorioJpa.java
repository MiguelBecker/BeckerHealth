package dev.beckerhealth.infraestrutura.persistencia.jpa;

import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

@Entity
@Table(name = "relatorios")
class RelatorioJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, length = 200)
    String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    String conteudo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    TipoRelatorio tipo;

    @ManyToOne
    UsuarioJpa geradoPor;

    @Column(nullable = false)
    LocalDateTime dataGeracao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    StatusRelatorio status;

    @Column(length = 500)
    String observacoes;

    enum TipoRelatorio {
        CONSULTAS_POR_PERIODO, CONSULTAS_POR_MEDICO, CONSULTAS_POR_PACIENTE,
        PRONTUARIOS_POR_PERIODO, NOTIFICACOES_POR_PERIODO,
        ESTATISTICAS_GERAIS, FATURAMENTO
    }

    enum StatusRelatorio {
        GERADO, PROCESSANDO, CONCLUIDO, ERRO
    }
}

interface RelatorioJpaRepository extends JpaRepository<RelatorioJpa, Long> {
    List<RelatorioJpa> findByTipo(TipoRelatorio tipo);
    List<RelatorioJpa> findByDataGeracaoBetween(java.time.LocalDateTime dataInicio, java.time.LocalDateTime dataFim);
    List<RelatorioJpa> findByGeradoPorId(Long usuarioId);
}

