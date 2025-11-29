package dev.beckerhealth.aplicacao.relatorios.geracao;

import dev.beckerhealth.dominio.relatorios.Relatorio;
import dev.beckerhealth.dominio.relatorios.RelatorioRepository;

import java.time.LocalDateTime;
import java.util.Map;

public class RelatorioConsultasStrategy implements RelatorioStrategy {
    private final RelatorioRepository relatorioRepository;

    public RelatorioConsultasStrategy(RelatorioRepository relatorioRepository) {
        this.relatorioRepository = relatorioRepository;
    }

    @Override
    public Relatorio gerar(Map<String, Object> parametros, Long usuarioId) {
        String periodoInicio = (String) parametros.get("periodoInicio");
        String periodoFim = (String) parametros.get("periodoFim");
        
        String conteudo = String.format(
            "Relatório de Consultas\n" +
            "Período: %s a %s\n" +
            "Total de consultas: [a ser calculado]",
            periodoInicio, periodoFim
        );

        Relatorio relatorio = new Relatorio(
            null,
            "Relatório de Consultas por Período",
            conteudo,
            Relatorio.TipoRelatorio.CONSULTAS_POR_PERIODO,
            usuarioId,
            LocalDateTime.now(),
            Relatorio.StatusRelatorio.CONCLUIDO,
            null
        );

        return relatorioRepository.salvar(relatorio);
    }

    @Override
    public boolean suporta(Relatorio.TipoRelatorio tipo) {
        return tipo == Relatorio.TipoRelatorio.CONSULTAS_POR_PERIODO;
    }
}

