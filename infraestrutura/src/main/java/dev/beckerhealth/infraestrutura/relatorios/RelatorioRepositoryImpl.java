package dev.beckerhealth.infraestrutura.relatorios;

import dev.beckerhealth.dominio.relatorios.Relatorio;
import dev.beckerhealth.dominio.relatorios.RelatorioRepository;
import dev.beckerhealth.infraestrutura.persistencia.jpa.JpaMapeador;
import dev.beckerhealth.infraestrutura.persistencia.jpa.RelatorioJpa;
import dev.beckerhealth.infraestrutura.persistencia.jpa.RelatorioJpaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class RelatorioRepositoryImpl implements RelatorioRepository {
    @Autowired
    RelatorioJpaRepository repositorio;

    @Autowired
    JpaMapeador mapeador;

    @Override
    public Relatorio salvar(Relatorio relatorio) {
        var relatorioJpa = mapeador.map(relatorio, RelatorioJpa.class);
        var relatorioSalvo = repositorio.save(relatorioJpa);
        return mapeador.map(relatorioSalvo, Relatorio.class);
    }

    @Transactional
    @Override
    public Optional<Relatorio> buscarPorId(Long id) {
        return repositorio.findById(id)
                .map(relatorioJpa -> mapeador.map(relatorioJpa, Relatorio.class));
    }

    @Override
    public List<Relatorio> listarTodos() {
        return repositorio.findAll().stream()
                .map(relatorioJpa -> mapeador.map(relatorioJpa, Relatorio.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Relatorio> buscarPorTipo(Relatorio.TipoRelatorio tipo) {
        return repositorio.findByTipo(mapTipo(tipo)).stream()
                .map(relatorioJpa -> mapeador.map(relatorioJpa, Relatorio.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Relatorio> buscarPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return repositorio.findByDataGeracaoBetween(dataInicio, dataFim).stream()
                .map(relatorioJpa -> mapeador.map(relatorioJpa, Relatorio.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Relatorio> buscarPorUsuario(Long usuarioId) {
        return repositorio.findByGeradoPorId(usuarioId).stream()
                .map(relatorioJpa -> mapeador.map(relatorioJpa, Relatorio.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deletar(Long id) {
        repositorio.deleteById(id);
    }

    private RelatorioJpa.TipoRelatorio mapTipo(Relatorio.TipoRelatorio tipo) {
        return tipo != null ? RelatorioJpa.TipoRelatorio.valueOf(tipo.name()) : null;
    }
}

