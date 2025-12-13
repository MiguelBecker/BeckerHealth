package dev.beckerhealth.aplicacao.prontuario;

import dev.beckerhealth.dominio.compartilhado.usuario.Medico;
import dev.beckerhealth.dominio.compartilhado.usuario.Paciente;
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

    public Prontuario executar(Long consultaId, Long medicoId, String anamnese, 
                               String diagnostico, String observacoes) {
        // Buscar consulta
        Consulta consulta = consultaRepository.buscarPorId(new ConsultaId(consultaId))
                .orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada"));

        // Validar que o médico é o responsável pela consulta
        if (!consulta.getMedico().getId().equals(medicoId)) {
            throw new IllegalStateException("Apenas o médico responsável pela consulta pode registrar o prontuário");
        }

        // Criar prontuário
        Prontuario prontuario = new Prontuario();
        prontuario.setPaciente(consulta.getPaciente());
        prontuario.setMedicoResponsavel(consulta.getMedico());
        prontuario.setAnamnese(anamnese);
        prontuario.setDiagnostico(diagnostico);
        prontuario.setObservacoes(observacoes);
        prontuario.setDataAtendimento(LocalDateTime.now());
        prontuario.setTipoAtendimento(mapTipoConsulta(consulta.getTipo()));

        return prontuarioRepository.salvar(prontuario);
    }

    private TipoAtendimento mapTipoConsulta(Consulta.TipoConsulta tipo) {
        return switch (tipo) {
            case ROTINA -> TipoAtendimento.CONSULTA;
            case RETORNO -> TipoAtendimento.RETORNO;
            case URGENCIA -> TipoAtendimento.URGENCIA;
        };
    }
}

