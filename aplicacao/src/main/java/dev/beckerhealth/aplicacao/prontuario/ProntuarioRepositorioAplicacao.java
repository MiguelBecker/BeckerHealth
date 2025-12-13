package dev.beckerhealth.aplicacao.prontuario;

import dev.beckerhealth.aplicacao.prontuario.dto.ProntuarioResumo;

import java.util.List;

public interface ProntuarioRepositorioAplicacao {
    List<ProntuarioResumo> pesquisarResumos();
    
    List<ProntuarioResumo> pesquisarResumosPorPaciente(Long pacienteId);
    
    List<ProntuarioResumo> pesquisarResumosPorMedico(Long medicoId);
}


