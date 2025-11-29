package dev.beckerhealth.aplicacao.consultas;

import dev.beckerhealth.aplicacao.consultas.dto.ConsultaResumo;
import dev.beckerhealth.aplicacao.consultas.processamento.ProcessamentoConsultaAgendada;
import dev.beckerhealth.aplicacao.consultas.validacao.ValidacaoConsultaStrategy;
import dev.beckerhealth.aplicacao.consultas.validacao.ValidacaoHorarioStrategy;
import dev.beckerhealth.dominio.compartilhado.evento.EventoBarramento;
import dev.beckerhealth.dominio.consultas.Consulta;
import dev.beckerhealth.dominio.consultas.ConsultaRepository;
import dev.beckerhealth.dominio.compartilhado.usuario.Medico;
import dev.beckerhealth.dominio.compartilhado.usuario.Paciente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class AgendarConsulta {

    private final ConsultaRepository consultaRepository;
    private final EventoBarramento eventoBarramento;
    private final ProcessamentoConsultaAgendada processamentoConsultaAgendada;

    public ConsultaResumo executar(
            Long pacienteId,
            Long medicoId,
            LocalDate dataConsulta,
            LocalTime horaConsulta,
            Consulta.TipoConsulta tipo
    ) {
        ValidacaoConsultaStrategy validacaoStrategy = new ValidacaoHorarioStrategy();
        validacaoStrategy.validar(dataConsulta, horaConsulta, tipo);

        Paciente paciente = new Paciente();
        paciente.setId(pacienteId);
        
        Medico medico = new Medico();
        medico.setId(medicoId);
        
        Consulta consulta = new Consulta();
        consulta.setPaciente(paciente);
        consulta.setMedico(medico);
        consulta.setDataConsulta(dataConsulta);
        consulta.setHoraConsulta(horaConsulta);
        consulta.setTipo(tipo);
        consulta.setStatus(Consulta.StatusConsulta.AGENDADA);

        Consulta consultaSalva = consultaRepository.salvar(consulta);

        processamentoConsultaAgendada.processar(consultaSalva);

        return mapearParaResumo(consultaSalva);
    }

    private void validarHorario(LocalDate data, LocalTime hora) {
        if (data.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Não é possível agendar consultas em datas passadas");
        }

        if (data.equals(LocalDate.now()) && hora.isBefore(LocalTime.now())) {
            throw new IllegalArgumentException("Não é possível agendar consultas em horários passados");
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

