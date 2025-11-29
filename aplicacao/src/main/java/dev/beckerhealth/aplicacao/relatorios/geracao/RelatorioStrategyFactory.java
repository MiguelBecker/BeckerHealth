package dev.beckerhealth.aplicacao.relatorios.geracao;

import dev.beckerhealth.dominio.relatorios.Relatorio;
import dev.beckerhealth.dominio.relatorios.RelatorioRepository;

import java.util.ArrayList;
import java.util.List;

public class RelatorioStrategyFactory {
    private final List<RelatorioStrategy> estrategias;

    public RelatorioStrategyFactory(RelatorioRepository relatorioRepository) {
        this.estrategias = new ArrayList<>();
        this.estrategias.add(new RelatorioConsultasStrategy(relatorioRepository));
        this.estrategias.add(new RelatorioMedicoStrategy(relatorioRepository));
    }

    public RelatorioStrategy obterEstrategia(Relatorio.TipoRelatorio tipo) {
        return estrategias.stream()
                .filter(estrategia -> estrategia.suporta(tipo))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Estratégia não encontrada para o tipo: " + tipo));
    }
}

