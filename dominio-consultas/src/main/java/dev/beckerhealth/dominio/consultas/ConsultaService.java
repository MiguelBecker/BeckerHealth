package dev.beckerhealth.dominio.consultas;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ConsultaService {

    private final ConsultaRepository consultaRepository;

    public ConsultaService(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    public Consulta agendarConsulta(Consulta consulta) {
        List<Consulta> consultasNoDia = consultaRepository.buscarPorData(consulta.getDataConsulta());

        boolean ocupado = consultasNoDia.stream().anyMatch(c ->
            c.getMedico().getId().equals(consulta.getMedico().getId()) &&
            c.getHoraConsulta().equals(consulta.getHoraConsulta()) &&
            c.getStatus() == Consulta.StatusConsulta.AGENDADA
        );

        if (ocupado && consulta.getTipo() != Consulta.TipoConsulta.URGENCIA) {
            throw new IllegalStateException("Horário já ocupado para este médico.");
        }

        consulta.setStatus(Consulta.StatusConsulta.AGENDADA);
        return consultaRepository.salvar(consulta);
    }

    public void cancelarConsulta(Consulta consulta, LocalDate dataAtual, LocalTime horaAtual) {
        Duration diferenca = Duration.between(
            horaAtual.atDate(dataAtual),
            consulta.getHoraConsulta().atDate(consulta.getDataConsulta())
        );

        long horas = diferenca.toHours();

        if (horas >= 2) {
            consulta.setStatus(Consulta.StatusConsulta.CANCELADA);
            consultaRepository.salvar(consulta);
        } else {
            consulta.setStatus(Consulta.StatusConsulta.CANCELADA);
            consultaRepository.salvar(consulta);
            throw new IllegalStateException("Cancelamento com menos de 2h aplica penalidade.");
        }
    }

    public Consulta reagendarConsulta(Consulta consulta, LocalTime novoHorario) {
        List<Consulta> consultasNoDia = consultaRepository.buscarPorData(consulta.getDataConsulta());

        boolean ocupado = consultasNoDia.stream().anyMatch(c ->
            c.getMedico().getId().equals(consulta.getMedico().getId()) &&
            c.getHoraConsulta().equals(novoHorario) &&
            c.getStatus() == Consulta.StatusConsulta.AGENDADA
        );

        if (ocupado) {
            throw new IllegalStateException("Horário indisponível para reagendamento.");
        }

        consulta.setHoraConsulta(novoHorario);
        consulta.setStatus(Consulta.StatusConsulta.AGENDADA);
        return consultaRepository.salvar(consulta);
    }
}
