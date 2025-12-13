package dev.beckerhealth.infraestrutura.prontuario;

import dev.beckerhealth.dominio.prontuario.Exame;
import dev.beckerhealth.dominio.prontuario.ExameRepository;
import dev.beckerhealth.infraestrutura.persistencia.jpa.ExameJpa;
import dev.beckerhealth.infraestrutura.persistencia.jpa.ExameJpaRepository;
import dev.beckerhealth.infraestrutura.persistencia.jpa.JpaMapeador;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ExameRepositoryImpl implements ExameRepository {
    @Autowired
    ExameJpaRepository repositorio;

    @Autowired
    JpaMapeador mapeador;

    @Override
    public Exame salvar(Exame exame) {
        var exameJpa = mapeador.map(exame, ExameJpa.class);
        var exameSalvo = repositorio.save(exameJpa);
        return mapeador.map(exameSalvo, Exame.class);
    }

    @Transactional
    @Override
    public Optional<Exame> buscarPorId(Long id) {
        return repositorio.findById(id)
                .map(exameJpa -> mapeador.map(exameJpa, Exame.class));
    }

    @Override
    public List<Exame> buscarPorProntuario(Long prontuarioId) {
        return repositorio.findByProntuarioId(prontuarioId).stream()
                .map(exameJpa -> mapeador.map(exameJpa, Exame.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Exame> buscarPorConsulta(Long consultaId) {
        return repositorio.findByConsultaId(consultaId).stream()
                .map(exameJpa -> mapeador.map(exameJpa, Exame.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Exame> listarTodos() {
        return repositorio.findAll().stream()
                .map(exameJpa -> mapeador.map(exameJpa, Exame.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deletar(Long id) {
        repositorio.deleteById(id);
    }
}
