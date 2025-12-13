package dev.beckerhealth.aplicacao.prontuario;

import static org.apache.commons.lang3.Validate.notNull;

import dev.beckerhealth.aplicacao.prontuario.dto.ProntuarioResumo;

import java.util.List;

public class ProntuarioServicoAplicacao {
    private ProntuarioRepositorioAplicacao repositorio;

    public ProntuarioServicoAplicacao(ProntuarioRepositorioAplicacao repositorio) {
        notNull(repositorio, "O repositório não pode ser nulo");
        this.repositorio = repositorio;
    }

    public List<ProntuarioResumo> pesquisarResumos() {
        return repositorio.pesquisarResumos();
    }
    
    public List<ProntuarioResumo> pesquisarResumosPorPaciente(Long pacienteId) {
        return repositorio.pesquisarResumosPorPaciente(pacienteId);
    }
    
    public List<ProntuarioResumo> pesquisarResumosPorMedico(Long medicoId) {
        return repositorio.pesquisarResumosPorMedico(medicoId);
    }
}
