package dev.beckerhealth.aplicacao.consultas;

import dev.beckerhealth.aplicacao.consultas.dto.ConsultaResumo;
import dev.beckerhealth.dominio.consultas.Consulta;
import dev.beckerhealth.dominio.consultas.ConsultaRepository;
import dev.beckerhealth.dominio.compartilhado.usuario.Medico;
import dev.beckerhealth.dominio.compartilhado.usuario.Paciente;
import dev.beckerhealth.dominio.compartilhado.usuario.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class AgendarConsulta {

    private final ConsultaRepository consultaRepository;

    public ConsultaResumo executar(
            Long pacienteId,
            Long medicoId,
            LocalDate dataConsulta,
            LocalTime horaConsulta,
            Consulta.TipoConsulta tipo
    ) {
        validarHorario(dataConsulta, horaConsulta);

        Consulta consulta = new Consulta();
        consulta.setPaciente(new Paciente());
        consulta.getPaciente().setId(pacienteId);
        consulta.setMedico(new Medico());
        consulta.getMedico().setId(medicoId);
        consulta.setDataConsulta(dataConsulta);
        consulta.setHoraConsulta(horaConsulta);
        consulta.setTipo(tipo);
        consulta.setStatus(Consulta.StatusConsulta.AGENDADA);

        Consulta consultaSalva = consultaRepository.salvar(consulta);

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
                .id(consulta.getId())
                .pacienteNome(consulta.getPaciente().getNome())
                .pacienteCpf(consulta.getPaciente().getCpf())
                .medicoNome(consulta.getMedico().getNome())
                .medicoEspecialidade(consulta.getMedico().getEspecialidade())
                .dataConsulta(consulta.getDataConsulta())
                .horaConsulta(consulta.getHoraConsulta())
                .tipo(consulta.getTipo().name())
                .status(consulta.getStatus().name())
                .build();
    }
}

