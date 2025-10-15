package dev.beckerhealth.dominio.consultas;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ConsultaRepository {
    Consulta salvar(Consulta consulta);

    Optional<Consulta> buscarPorId(Long id);

    List<Consulta> listarTodas();

    List<Consulta> buscarPorData(LocalDate data);

    List<Consulta> buscarPorStatus(Consulta.StatusConsulta status);

    List<Consulta> buscarPorPaciente(Long pacienteId);

    List<Consulta> buscarPorMedico(Long medicoId);

    void deletar(Long id);
}

