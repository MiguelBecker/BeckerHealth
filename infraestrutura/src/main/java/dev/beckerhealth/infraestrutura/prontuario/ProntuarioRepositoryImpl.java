package dev.beckerhealth.infraestrutura.prontuario;

import dev.beckerhealth.dominio.prontuario.Prontuario;
import dev.beckerhealth.dominio.prontuario.ProntuarioRepository;
import dev.beckerhealth.infraestrutura.persistencia.jpa.JpaMapeador;
import dev.beckerhealth.infraestrutura.persistencia.jpa.ProntuarioJpa;
import dev.beckerhealth.infraestrutura.persistencia.jpa.ProntuarioJpaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
class ProntuarioRepositoryImpl implements ProntuarioRepository {
    @Autowired
    ProntuarioJpaRepository repositorio;

    @Autowired
    JpaMapeador mapeador;

    @Override
    public Prontuario salvar(Prontuario prontuario) {
        var prontuarioJpa = mapeador.map(prontuario, ProntuarioJpa.class);
        var prontuarioSalvo = repositorio.save(prontuarioJpa);
        return mapeador.map(prontuarioSalvo, Prontuario.class);
    }

    @Transactional
    @Override
    public Optional<Prontuario> buscarPorId(Long id) {
        return repositorio.findById(id)
                .map(prontuarioJpa -> mapeador.map(prontuarioJpa, Prontuario.class));
    }

    @Override
    public List<Prontuario> listarTodos() {
        return repositorio.findAll().stream()
                .map(prontuarioJpa -> mapeador.map(prontuarioJpa, Prontuario.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Prontuario> buscarPorPaciente(Long pacienteId) {
        return repositorio.findByPacienteId(pacienteId).stream()
                .map(prontuarioJpa -> mapeador.map(prontuarioJpa, Prontuario.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Prontuario> buscarPorMedico(Long medicoId) {
        return repositorio.findByMedicoResponsavelId(medicoId).stream()
                .map(prontuarioJpa -> mapeador.map(prontuarioJpa, Prontuario.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Prontuario> buscarPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return repositorio.findByDataAtendimentoBetween(dataInicio, dataFim).stream()
                .map(prontuarioJpa -> mapeador.map(prontuarioJpa, Prontuario.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deletar(Long id) {
        repositorio.deleteById(id);
    }
}

