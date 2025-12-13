package dev.beckerhealth.aplicacao.consultas;

import static org.apache.commons.lang3.Validate.notNull;

import dev.beckerhealth.aplicacao.consultas.dto.ConsultaResumo;
import dev.beckerhealth.aplicacao.consultas.dto.ConsultaResumoExpandido;

import java.util.List;

public class ConsultaServicoAplicacao {
    private ConsultaRepositorioAplicacao repositorio;

    public ConsultaServicoAplicacao(ConsultaRepositorioAplicacao repositorio) {
        notNull(repositorio, "O repositório não pode ser nulo");
        this.repositorio = repositorio;
    }

    public List<ConsultaResumo> pesquisarResumos() {
        return repositorio.pesquisarResumos();
    }

    public List<ConsultaResumo> pesquisarResumosPorMedico(Long medicoId) {
        notNull(medicoId, "O ID do médico não pode ser nulo");
        return repositorio.pesquisarResumosPorMedico(medicoId);
    }

    public List<ConsultaResumoExpandido> pesquisarResumosExpandidos() {
        return repositorio.pesquisarResumosExpandidos();
    }
}

