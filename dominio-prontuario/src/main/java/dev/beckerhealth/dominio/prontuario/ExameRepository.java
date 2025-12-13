package dev.beckerhealth.dominio.prontuario;

import java.util.List;
import java.util.Optional;

public interface ExameRepository {
    Exame salvar(Exame exame);
    Optional<Exame> buscarPorId(Long id);
    List<Exame> buscarPorProntuario(Long prontuarioId);
    List<Exame> buscarPorConsulta(Long consultaId);
    List<Exame> listarTodos();
    void deletar(Long id);
}
