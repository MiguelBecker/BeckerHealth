package dev.beckerhealth.aplicacao.consultas;

import dev.beckerhealth.aplicacao.consultas.dto.ConsultaResumo;
import dev.beckerhealth.dominio.consultas.Consulta;
import dev.beckerhealth.dominio.consultas.ConsultaId;
import dev.beckerhealth.dominio.consultas.ConsultaRepository;
import dev.beckerhealth.aplicacao.consultas.processamento.ProcessamentoConsultaReagendada;
import dev.beckerhealth.aplicacao.consultas.validacao.ValidacaoConsultaStrategy;
import dev.beckerhealth.aplicacao.consultas.validacao.ValidacaoHorarioStrategy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReagendarConsulta {

    private final ConsultaRepository consultaRepository;
    private final ProcessamentoConsultaReagendada processamentoConsultaReagendada;

    public ReagendarConsulta(ConsultaRepository consultaRepository,
                          ProcessamentoConsultaReagendada processamentoConsultaReagendada) {
        this.consultaRepository = consultaRepository;
        this.processamentoConsultaReagendada = processamentoConsultaReagendada;
    }

    public ConsultaResumo executar(Long consultaId, LocalDate novaData, LocalTime novaHora) {
        Consulta consulta = consultaRepository.buscarPorId(new ConsultaId(consultaId))
                .orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada"));

        // Validar reagendamento
        validarReagendamento(consulta, novaData, novaHora);

        // Atualizar data e hora
        consulta.setDataConsulta(novaData);
        consulta.setHoraConsulta(novaHora);
        consulta.setStatus(Consulta.StatusConsulta.AGENDADA);

        Consulta consultaReagendada = consultaRepository.salvar(consulta);

        processamentoConsultaReagendada.processar(consultaReagendada);

        return mapearParaResumo(consultaReagendada);
    }

    private void validarReagendamento(Consulta consulta, LocalDate novaData, LocalTime novaHora) {
        if (consulta.getStatus() == Consulta.StatusConsulta.CONCLUIDA) {
            throw new IllegalStateException("Não é possível reagendar uma consulta já concluída");
        }

        if (consulta.getStatus() == Consulta.StatusConsulta.CANCELADA) {
            throw new IllegalStateException("Não é possível reagendar uma consulta cancelada");
        }

        // Validar novo horário
        ValidacaoConsultaStrategy validacaoStrategy = new ValidacaoHorarioStrategy();
        validacaoStrategy.validar(novaData, novaHora, consulta.getTipo());

        // Verificar conflito de horário
        List<Consulta> consultasNoDia = consultaRepository.buscarPorData(novaData);
        boolean ocupado = consultasNoDia.stream().anyMatch(c -> {
            // Excluir a própria consulta da verificação
            boolean naoEhAMesmaConsulta = c.getId() == null || consulta.getId() == null || 
                    !c.getId().getId().equals(consulta.getId().getId());
            
            // Verificar se há conflito de horário
            return naoEhAMesmaConsulta &&
                    c.getMedico() != null && consulta.getMedico() != null &&
                    c.getMedico().getId().equals(consulta.getMedico().getId()) &&
                    c.getHoraConsulta() != null && novaHora != null &&
                    c.getHoraConsulta().equals(novaHora) &&
                    c.getStatus() == Consulta.StatusConsulta.AGENDADA;
        });

        if (ocupado && consulta.getTipo() != Consulta.TipoConsulta.URGENCIA) {
            throw new IllegalStateException("Horário já ocupado para este médico. Escolha outro horário.");
        }
    }

    private ConsultaResumo mapearParaResumo(Consulta consulta) {
        return ConsultaResumo.builder()
                .id(consulta.getId() != null ? consulta.getId().getId() : null)
                .pacienteNome(consulta.getPaciente() != null ? consulta.getPaciente().getNome() : null)
                .pacienteCpf(consulta.getPaciente() != null && consulta.getPaciente().getCpf() != null 
                        ? consulta.getPaciente().getCpf().getCodigo() : null)
                .medicoNome(consulta.getMedico() != null ? consulta.getMedico().getNome() : null)
                .medicoEspecialidade(consulta.getMedico() != null ? consulta.getMedico().getEspecialidade() : null)
                .dataConsulta(consulta.getDataConsulta())
                .horaConsulta(consulta.getHoraConsulta())
                .tipo(consulta.getTipo() != null ? consulta.getTipo().name() : null)
                .status(consulta.getStatus() != null ? consulta.getStatus().name() : null)
                .build();
    }
}

