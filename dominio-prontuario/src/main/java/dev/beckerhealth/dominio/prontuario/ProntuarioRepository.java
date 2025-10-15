package dev.beckerhealth.dominio.prontuario;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProntuarioRepository {
    Prontuario salvar(Prontuario prontuario);

    Optional<Prontuario> buscarPorId(Long id);

    List<Prontuario> listarTodos();

    List<Prontuario> buscarPorPaciente(Long pacienteId);

    List<Prontuario> buscarPorMedico(Long medicoId);

    List<Prontuario> buscarPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim);

    void deletar(Long id);
}

