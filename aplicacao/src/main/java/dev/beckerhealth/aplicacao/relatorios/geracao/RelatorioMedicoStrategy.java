package dev.beckerhealth.aplicacao.relatorios.geracao;

import dev.beckerhealth.dominio.relatorios.Relatorio;
import dev.beckerhealth.dominio.relatorios.RelatorioRepository;

import java.time.LocalDateTime;
import java.util.Map;

public class RelatorioMedicoStrategy implements RelatorioStrategy {
    private final RelatorioRepository relatorioRepository;

    public RelatorioMedicoStrategy(RelatorioRepository relatorioRepository) {
        this.relatorioRepository = relatorioRepository;
    }

    @Override
    public Relatorio gerar(Map<String, Object> parametros, Long usuarioId) {
        Long medicoId = (Long) parametros.get("medicoId");
        
        String conteudo = String.format(
            "Relatório de Consultas por Médico\n" +
            "Médico ID: %d\n" +
            "Total de consultas: [a ser calculado]",
            medicoId
        );

        Relatorio relatorio = new Relatorio(
            null,
            "Relatório de Consultas por Médico",
            conteudo,
            Relatorio.TipoRelatorio.CONSULTAS_POR_MEDICO,
            usuarioId,
            LocalDateTime.now(),
            Relatorio.StatusRelatorio.CONCLUIDO,
            null
        );

        return relatorioRepository.salvar(relatorio);
    }

    @Override
    public boolean suporta(Relatorio.TipoRelatorio tipo) {
        return tipo == Relatorio.TipoRelatorio.CONSULTAS_POR_MEDICO;
    }
}

