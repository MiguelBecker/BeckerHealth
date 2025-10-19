package dev.beckerhealth.infraestrutura.consultas;

import dev.beckerhealth.dominio.consultas.Consulta;
import dev.beckerhealth.dominio.consultas.ConsultaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConsultaRepositoryJpa extends JpaRepository<Consulta, Long>, ConsultaRepository {

    @Override
    default Consulta salvar(Consulta consulta) {
        return save(consulta);
    }

    @Override
    default Optional<Consulta> buscarPorId(Long id) {
        return findById(id);
    }

    @Override
    default List<Consulta> listarTodas() {
        return findAll();
    }

    @Override
    List<Consulta> findByDataConsulta(LocalDate data);

    @Override
    default List<Consulta> buscarPorData(LocalDate data) {
        return findByDataConsulta(data);
    }

    @Override
    List<Consulta> findByStatus(Consulta.StatusConsulta status);

    @Override
    default List<Consulta> buscarPorStatus(Consulta.StatusConsulta status) {
        return findByStatus(status);
    }

    List<Consulta> findByPacienteId(Long pacienteId);

    @Override
    default List<Consulta> buscarPorPaciente(Long pacienteId) {
        return findByPacienteId(pacienteId);
    }

    List<Consulta> findByMedicoId(Long medicoId);

    @Override
    default List<Consulta> buscarPorMedico(Long medicoId) {
        return findByMedicoId(medicoId);
    }

    @Override
    default void deletar(Long id) {
        deleteById(id);
    }
}

