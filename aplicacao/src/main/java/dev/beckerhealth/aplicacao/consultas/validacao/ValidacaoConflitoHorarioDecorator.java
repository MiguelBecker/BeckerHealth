package dev.beckerhealth.aplicacao.consultas.validacao;

import dev.beckerhealth.dominio.consultas.Consulta;
import dev.beckerhealth.dominio.consultas.ConsultaRepository;

import java.time.LocalDate;
import java.time.LocalTime;

public class ValidacaoConflitoHorarioDecorator extends ValidacaoConsultaDecorator {
    private final ConsultaRepository consultaRepository;
    private final Long medicoId;

    public ValidacaoConflitoHorarioDecorator(ValidacaoConsultaStrategy validacaoBase, 
                                           ConsultaRepository consultaRepository, 
                                           Long medicoId) {
        super(validacaoBase);
        this.consultaRepository = consultaRepository;
        this.medicoId = medicoId;
    }

    @Override
    protected void validarAdicional(LocalDate dataConsulta, LocalTime horaConsulta, Consulta.TipoConsulta tipo) {
        var consultasMedico = consultaRepository.buscarPorMedico(medicoId);
        boolean conflito = consultasMedico.stream()
                .anyMatch(c -> c.getDataConsulta().equals(dataConsulta) 
                        && c.getHoraConsulta().equals(horaConsulta)
                        && c.getStatus() == Consulta.StatusConsulta.AGENDADA);
        
        if (conflito) {
            throw new IllegalArgumentException("O médico já possui uma consulta agendada neste horário");
        }
    }
}

