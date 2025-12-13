package dev.beckerhealth.dominio.prontuario;

import java.util.List;
import java.util.Optional;

public interface PrescricaoRepository {
    Prescricao salvar(Prescricao prescricao);
    
    Optional<Prescricao> buscarPorId(Long id);
    
    List<Prescricao> buscarPorProntuario(Long prontuarioId);
    
    List<Prescricao> buscarPorPaciente(Long pacienteId);
    
    List<Prescricao> buscarValidas(Long pacienteId);
    
    void deletar(Long id);
}

