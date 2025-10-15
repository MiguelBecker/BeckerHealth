package dev.beckerhealth.dominio.relatorios;

import dev.beckerhealth.dominio.compartilhado.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "relatorios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Relatorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String conteudo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoRelatorio tipo;

    @ManyToOne
    private Usuario geradoPor;

    @Column(nullable = false)
    private LocalDateTime dataGeracao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusRelatorio status = StatusRelatorio.GERADO;

    @Column(length = 500)
    private String observacoes;

    public enum TipoRelatorio {
        CONSULTAS_POR_PERIODO, CONSULTAS_POR_MEDICO, CONSULTAS_POR_PACIENTE,
        PRONTUARIOS_POR_PERIODO, NOTIFICACOES_POR_PERIODO,
        ESTATISTICAS_GERAIS, FATURAMENTO
    }

    public enum StatusRelatorio {
        GERADO, PROCESSANDO, CONCLUIDO, ERRO
    }
}

