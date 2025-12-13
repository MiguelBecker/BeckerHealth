package dev.beckerhealth.apresentacao.backend.prontuario;

import dev.beckerhealth.aplicacao.prontuario.dto.ProntuarioResumo;

import java.util.List;

public class RegistrarProntuarioForm {
    public ProntuarioDto prontuario;
    public List<ProntuarioResumo> prontuariosExistentes;

    public RegistrarProntuarioForm(ProntuarioDto prontuario, List<ProntuarioResumo> prontuariosExistentes) {
        this.prontuario = prontuario;
        this.prontuariosExistentes = prontuariosExistentes;
    }

    public RegistrarProntuarioForm() {
        this.prontuario = new ProntuarioDto();
    }

    public static class ProntuarioDto {
        public Long consultaId;
        public Long medicoId;
        public String anamnese;
        public String diagnostico;
        public String prescricao;
        public String observacoes;
    }
}


