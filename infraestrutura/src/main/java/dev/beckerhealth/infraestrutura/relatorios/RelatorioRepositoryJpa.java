package dev.beckerhealth.infraestrutura.relatorios;

import dev.beckerhealth.dominio.relatorios.Relatorio;
import dev.beckerhealth.dominio.relatorios.RelatorioRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RelatorioRepositoryJpa extends JpaRepository<Relatorio, Long>, RelatorioRepository {

    @Override
    default Relatorio salvar(Relatorio relatorio) {
        return save(relatorio);
    }

    @Override
    default Optional<Relatorio> buscarPorId(Long id) {
        return findById(id);
    }

    @Override
    default List<Relatorio> listarTodos() {
        return findAll();
    }

    @Override
    List<Relatorio> findByTipo(Relatorio.TipoRelatorio tipo);

    @Override
    default List<Relatorio> buscarPorTipo(Relatorio.TipoRelatorio tipo) {
        return findByTipo(tipo);
    }

    @Override
    List<Relatorio> findByDataGeracaoBetween(LocalDateTime dataInicio, LocalDateTime dataFim);

    @Override
    default List<Relatorio> buscarPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return findByDataGeracaoBetween(dataInicio, dataFim);
    }

    @Override
    List<Relatorio> findByGeradoPorId(Long usuarioId);

    @Override
    default List<Relatorio> buscarPorUsuario(Long usuarioId) {
        return findByGeradoPorId(usuarioId);
    }

    @Override
    default void deletar(Long id) {
        deleteById(id);
    }
}

