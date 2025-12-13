package dev.beckerhealth.aplicacao.consultas;

import dev.beckerhealth.dominio.consultas.Consulta;
import dev.beckerhealth.dominio.consultas.ConsultaId;
import dev.beckerhealth.dominio.consultas.ConsultaRepository;
import dev.beckerhealth.aplicacao.consultas.processamento.ProcessamentoConsultaCancelada;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class CancelarConsulta {

    private final ConsultaRepository consultaRepository;
    private final ProcessamentoConsultaCancelada processamentoConsultaCancelada;

    public CancelarConsulta(ConsultaRepository consultaRepository,
                          ProcessamentoConsultaCancelada processamentoConsultaCancelada) {
        this.consultaRepository = consultaRepository;
        this.processamentoConsultaCancelada = processamentoConsultaCancelada;
    }

    public void executar(Long consultaId) {
        Consulta consulta = consultaRepository.buscarPorId(new ConsultaId(consultaId))
                .orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada"));

        LocalDate dataAtual = LocalDate.now();
        LocalTime horaAtual = LocalTime.now();

        // Validar e cancelar
        validarCancelamento(consulta, dataAtual, horaAtual);
        
        consulta.setStatus(Consulta.StatusConsulta.CANCELADA);
        Consulta consultaCancelada = consultaRepository.salvar(consulta);

        processamentoConsultaCancelada.processar(consultaCancelada);
    }

    private void validarCancelamento(Consulta consulta, LocalDate dataAtual, LocalTime horaAtual) {
        if (consulta.getStatus() == Consulta.StatusConsulta.CANCELADA) {
            throw new IllegalStateException("Consulta já está cancelada");
        }

        if (consulta.getStatus() == Consulta.StatusConsulta.CONCLUIDA) {
            throw new IllegalStateException("Não é possível cancelar uma consulta já concluída");
        }

        // Verificar se a consulta já passou
        if (consulta.getDataConsulta().isBefore(dataAtual) ||
            (consulta.getDataConsulta().equals(dataAtual) && 
             consulta.getHoraConsulta().isBefore(horaAtual))) {
            throw new IllegalStateException("Não é possível cancelar uma consulta que já passou");
        }
    }
}

