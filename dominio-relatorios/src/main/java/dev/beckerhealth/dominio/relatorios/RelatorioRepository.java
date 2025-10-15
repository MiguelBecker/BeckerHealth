package dev.beckerhealth.dominio.relatorios;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Interface de repositório para Relatório
 * Implementação será feita na camada de infraestrutura
 */
public interface RelatorioRepository {
    
    Relatorio salvar(Relatorio relatorio);
    
    Optional<Relatorio> buscarPorId(Long id);
    
    List<Relatorio> listarTodos();
    
    List<Relatorio> buscarPorTipo(Relatorio.TipoRelatorio tipo);
    
    List<Relatorio> buscarPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim);
    
    List<Relatorio> buscarPorUsuario(Long usuarioId);
    
    void deletar(Long id);
}

