package dev.beckerhealth.aplicacao.prontuario;

import dev.beckerhealth.aplicacao.prontuario.dto.ProntuarioResumo;
import dev.beckerhealth.dominio.consultas.Consulta;
import dev.beckerhealth.dominio.consultas.ConsultaId;
import dev.beckerhealth.dominio.consultas.ConsultaRepository;
import dev.beckerhealth.dominio.prontuario.Prontuario;
import dev.beckerhealth.dominio.prontuario.ProntuarioRepository;
import dev.beckerhealth.dominio.prontuario.Prontuario.TipoAtendimento;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RegistrarProntuario {
    private final ProntuarioRepository prontuarioRepository;
    private final ConsultaRepository consultaRepository;

    public RegistrarProntuario(ProntuarioRepository prontuarioRepository,
                              ConsultaRepository consultaRepository) {
        this.prontuarioRepository = prontuarioRepository;
        this.consultaRepository = consultaRepository;
    }

    public ProntuarioResumo executar(Long consultaId, Long medicoId, String anamnese, 
                                     String diagnostico, String prescricao, String observacoes) {
        // Validar parâmetros
        if (consultaId == null) {
            throw new IllegalArgumentException("ID da consulta não pode ser nulo");
        }
        
        // Buscar consulta
        Consulta consulta = consultaRepository.buscarPorId(new ConsultaId(consultaId))
                .orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada com ID: " + consultaId));

        // Validar que o médico é o responsável pela consulta
        if (medicoId != null && consulta.getMedico() != null && !consulta.getMedico().getId().equals(medicoId)) {
            throw new IllegalStateException("Apenas o médico responsável pela consulta pode registrar o prontuário");
        }

        // Criar prontuário
        Prontuario prontuario = new Prontuario();
        prontuario.setPaciente(consulta.getPaciente());
        prontuario.setMedicoResponsavel(consulta.getMedico());
        prontuario.setAnamnese(anamnese);
        prontuario.setDiagnostico(diagnostico);
        prontuario.setPrescricao(prescricao);
        prontuario.setObservacoes(observacoes);
        prontuario.setDataAtendimento(LocalDateTime.now());
        prontuario.setTipoAtendimento(mapTipoConsulta(consulta.getTipo()));

        Prontuario prontuarioSalvo = prontuarioRepository.salvar(prontuario);

        return mapearParaResumo(prontuarioSalvo);
    }

    private TipoAtendimento mapTipoConsulta(Consulta.TipoConsulta tipo) {
        return switch (tipo) {
            case ROTINA -> TipoAtendimento.CONSULTA;
            case RETORNO -> TipoAtendimento.RETORNO;
            case URGENCIA -> TipoAtendimento.URGENCIA;
        };
    }

    private ProntuarioResumo mapearParaResumo(Prontuario prontuario) {
        return ProntuarioResumo.builder()
                .id(prontuario.getId())
                .pacienteNome(prontuario.getPaciente() != null ? prontuario.getPaciente().getNome() : null)
                .pacienteCpf(prontuario.getPaciente() != null && prontuario.getPaciente().getCpf() != null 
                        ? prontuario.getPaciente().getCpf().getCodigo() : null)
                .medicoNome(prontuario.getMedicoResponsavel() != null ? prontuario.getMedicoResponsavel().getNome() : null)
                .medicoEspecialidade(prontuario.getMedicoResponsavel() != null ? prontuario.getMedicoResponsavel().getEspecialidade() : null)
                .anamnese(prontuario.getAnamnese())
                .diagnostico(prontuario.getDiagnostico())
                .prescricao(prontuario.getPrescricao())
                .dataAtendimento(prontuario.getDataAtendimento())
                .tipoAtendimento(prontuario.getTipoAtendimento() != null ? prontuario.getTipoAtendimento().name() : null)
                .observacoes(prontuario.getObservacoes())
                .build();
    }
}

