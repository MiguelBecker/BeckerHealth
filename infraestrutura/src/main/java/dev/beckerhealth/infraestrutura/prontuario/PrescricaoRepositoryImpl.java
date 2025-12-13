package dev.beckerhealth.infraestrutura.prontuario;

import dev.beckerhealth.dominio.prontuario.Prescricao;
import dev.beckerhealth.dominio.prontuario.PrescricaoRepository;
import dev.beckerhealth.infraestrutura.persistencia.jpa.PrescricaoJpa;
import dev.beckerhealth.infraestrutura.persistencia.jpa.PrescricaoJpaRepository;
import dev.beckerhealth.infraestrutura.persistencia.jpa.JpaMapeador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PrescricaoRepositoryImpl implements PrescricaoRepository {
    @Autowired
    PrescricaoJpaRepository repositorio;

    @Autowired
    JpaMapeador mapeador;

    @Override
    public Prescricao salvar(Prescricao prescricao) {
        var prescricaoJpa = mapeador.map(prescricao, PrescricaoJpa.class);
        var prescricaoSalva = repositorio.save(prescricaoJpa);
        return mapeador.map(prescricaoSalva, Prescricao.class);
    }

    @Override
    public Optional<Prescricao> buscarPorId(Long id) {
        return repositorio.findById(id)
                .map(prescricaoJpa -> mapeador.map(prescricaoJpa, Prescricao.class));
    }

    @Override
    public List<Prescricao> buscarPorProntuario(Long prontuarioId) {
        return repositorio.findByProntuarioId(prontuarioId).stream()
                .map(prescricaoJpa -> mapeador.map(prescricaoJpa, Prescricao.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Prescricao> buscarPorPaciente(Long pacienteId) {
        return repositorio.findByPacienteId(pacienteId).stream()
                .map(prescricaoJpa -> mapeador.map(prescricaoJpa, Prescricao.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Prescricao> buscarValidas(Long pacienteId) {
        return repositorio.findValidasByPacienteId(pacienteId, LocalDate.now()).stream()
                .map(prescricaoJpa -> mapeador.map(prescricaoJpa, Prescricao.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deletar(Long id) {
        if (id != null) {
            repositorio.deleteById(id);
        }
    }
}

