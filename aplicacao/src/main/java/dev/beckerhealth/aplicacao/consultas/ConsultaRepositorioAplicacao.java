package dev.beckerhealth.aplicacao.consultas;

import dev.beckerhealth.aplicacao.consultas.dto.ConsultaResumo;
import dev.beckerhealth.aplicacao.consultas.dto.ConsultaResumoExpandido;

import java.util.List;

public interface ConsultaRepositorioAplicacao {
    List<ConsultaResumo> pesquisarResumos();

    List<ConsultaResumo> pesquisarResumosPorMedico(Long medicoId);

    List<ConsultaResumoExpandido> pesquisarResumosExpandidos();
}

