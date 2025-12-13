package dev.beckerhealth.dominio.prontuario;

import dev.beckerhealth.dominio.consultas.ConsultaId;

import java.util.List;
import java.util.Optional;

public interface ExameRepository {
    Exame salvar(Exame exame);
    
    Optional<Exame> buscarPorId(Long id);
    
    List<Exame> buscarPorConsulta(ConsultaId consultaId);
    
    List<Exame> buscarPorPaciente(Long pacienteId);
    
    List<Exame> buscarLiberados(Long pacienteId);
    
    void deletar(Long id);
}


