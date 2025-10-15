package dev.beckerhealth.infraestrutura.prontuario;

import dev.beckerhealth.dominio.prontuario.Prontuario;
import dev.beckerhealth.dominio.prontuario.ProntuarioRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProntuarioRepositoryJpa extends JpaRepository<Prontuario, Long>, ProntuarioRepository {

    @Override
    default Prontuario salvar(Prontuario prontuario) {
        return save(prontuario);
    }

    @Override
    default Optional<Prontuario> buscarPorId(Long id) {
        return findById(id);
    }

    @Override
    default List<Prontuario> listarTodos() {
        return findAll();
    }

    @Override
    List<Prontuario> findByPacienteId(Long pacienteId);

    @Override
    default List<Prontuario> buscarPorPaciente(Long pacienteId) {
        return findByPacienteId(pacienteId);
    }

    @Override
    List<Prontuario> findByMedicoResponsavelId(Long medicoId);

    @Override
    default List<Prontuario> buscarPorMedico(Long medicoId) {
        return findByMedicoResponsavelId(medicoId);
    }

    @Override
    List<Prontuario> findByDataAtendimentoBetween(LocalDateTime dataInicio, LocalDateTime dataFim);

    @Override
    default List<Prontuario> buscarPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return findByDataAtendimentoBetween(dataInicio, dataFim);
    }

    @Override
    default void deletar(Long id) {
        deleteById(id);
    }
}

