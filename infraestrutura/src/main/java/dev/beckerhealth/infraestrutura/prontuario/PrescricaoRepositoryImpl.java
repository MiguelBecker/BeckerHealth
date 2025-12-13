package dev.beckerhealth.infraestrutura.prontuario;

import dev.beckerhealth.dominio.prontuario.Prescricao;
import dev.beckerhealth.dominio.prontuario.PrescricaoRepository;
import dev.beckerhealth.infraestrutura.persistencia.jpa.JpaMapeador;
import dev.beckerhealth.infraestrutura.persistencia.jpa.PrescricaoJpa;
import dev.beckerhealth.infraestrutura.persistencia.jpa.PrescricaoJpaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

    @Transactional
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
    public List<Prescricao> listarTodos() {
        return repositorio.findAll().stream()
                .map(prescricaoJpa -> mapeador.map(prescricaoJpa, Prescricao.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deletar(Long id) {
        repositorio.deleteById(id);
    }
}
