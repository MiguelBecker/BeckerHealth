package dev.beckerhealth.aplicacao.relatorios.geracao;

import dev.beckerhealth.dominio.relatorios.Relatorio;

import java.time.LocalDateTime;
import java.util.Map;

public interface RelatorioStrategy {
    Relatorio gerar(Map<String, Object> parametros, Long usuarioId);
    
    boolean suporta(Relatorio.TipoRelatorio tipo);
}

