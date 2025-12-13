package dev.beckerhealth.aplicacao.prontuario;

import dev.beckerhealth.dominio.consultas.Consulta;
import dev.beckerhealth.dominio.consultas.ConsultaId;
import dev.beckerhealth.dominio.consultas.ConsultaRepository;
import dev.beckerhealth.dominio.prontuario.Exame;
import dev.beckerhealth.dominio.prontuario.ExameRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SolicitarExame {
    private final ExameRepository exameRepository;
    private final ConsultaRepository consultaRepository;

    public SolicitarExame(ExameRepository exameRepository,
                         ConsultaRepository consultaRepository) {
        this.exameRepository = exameRepository;
        this.consultaRepository = consultaRepository;
    }

    public Exame executar(Long consultaId, String tipoExame, LocalDate dataExame) {
        // Validar que a consulta existe
        Consulta consulta = consultaRepository.buscarPorId(new ConsultaId(consultaId))
                .orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada"));

        // Validar que a consulta está concluída ou agendada
        if (consulta.getStatus() == Consulta.StatusConsulta.CANCELADA) {
            throw new IllegalStateException("Não é possível solicitar exame para consulta cancelada");
        }

        // Criar exame
        Exame exame = new Exame();
        exame.setConsultaId(consulta.getId());
        exame.setTipo(tipoExame);
        exame.setDataExame(dataExame);
        exame.setLiberado(false); // Inicialmente não liberado

        return exameRepository.salvar(exame);
    }
}

