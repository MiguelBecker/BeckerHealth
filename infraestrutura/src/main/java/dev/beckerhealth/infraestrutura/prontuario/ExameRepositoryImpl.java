package dev.beckerhealth.infraestrutura.prontuario;

import dev.beckerhealth.dominio.consultas.ConsultaId;
import dev.beckerhealth.dominio.prontuario.Exame;
import dev.beckerhealth.dominio.prontuario.ExameRepository;
import dev.beckerhealth.infraestrutura.persistencia.jpa.ExameJpa;
import dev.beckerhealth.infraestrutura.persistencia.jpa.ExameJpaRepository;
import dev.beckerhealth.infraestrutura.persistencia.jpa.JpaMapeador;
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

    @Override
    public Optional<Exame> buscarPorId(Long id) {
        return repositorio.findById(id)
                .map(exameJpa -> mapeador.map(exameJpa, Exame.class));
    }

    @Override
    public List<Exame> buscarPorConsulta(ConsultaId consultaId) {
        return repositorio.findByConsultaId(consultaId != null ? consultaId.getId() : null).stream()
                .map(exameJpa -> mapeador.map(exameJpa, Exame.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Exame> buscarPorPaciente(Long pacienteId) {
        return repositorio.findByPacienteId(pacienteId).stream()
                .map(exameJpa -> mapeador.map(exameJpa, Exame.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Exame> buscarLiberados(Long pacienteId) {
        return repositorio.findLiberadosByPacienteId(pacienteId).stream()
                .map(exameJpa -> mapeador.map(exameJpa, Exame.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deletar(Long id) {
        if (id != null) {
            repositorio.deleteById(id);
        }
    }
}

