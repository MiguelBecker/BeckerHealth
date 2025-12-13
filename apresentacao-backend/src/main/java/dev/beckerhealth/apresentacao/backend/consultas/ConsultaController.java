package dev.beckerhealth.apresentacao.backend.consultas;

import dev.beckerhealth.aplicacao.consultas.AgendarConsulta;
import dev.beckerhealth.aplicacao.consultas.ConsultaServicoAplicacao;
import dev.beckerhealth.aplicacao.consultas.dto.ConsultaResumo;
import dev.beckerhealth.aplicacao.consultas.dto.ConsultaResumoExpandido;
import dev.beckerhealth.dominio.consultas.Consulta;
import dev.beckerhealth.dominio.consultas.ConsultaId;
import dev.beckerhealth.dominio.consultas.ConsultaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/consultas")
@CrossOrigin(origins = "*")
public class ConsultaController {

    private final ConsultaRepository consultaRepository;
    private final ConsultaServicoAplicacao consultaServicoAplicacao;
    private final AgendarConsulta agendarConsulta;

    public ConsultaController(ConsultaRepository consultaRepository, ConsultaServicoAplicacao consultaServicoAplicacao, AgendarConsulta agendarConsulta) {
        this.consultaRepository = consultaRepository;
        this.consultaServicoAplicacao = consultaServicoAplicacao;
        this.agendarConsulta = agendarConsulta;
    }

    @GetMapping
    public List<ConsultaResumo> listarTodas() {
        return consultaServicoAplicacao.pesquisarResumos();
    }

    @GetMapping("/expandido")
    public List<ConsultaResumoExpandido> listarTodasExpandidas() {
        return consultaServicoAplicacao.pesquisarResumosExpandidos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consulta> buscarPorId(@PathVariable Long id) {
        return consultaRepository.buscarPorId(new ConsultaId(id))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/data/{data}")
    public List<Consulta> buscarPorData(@PathVariable String data) {
        return consultaRepository.buscarPorData(LocalDate.parse(data));
    }

    @PostMapping
    public ResponseEntity<ConsultaResumo> agendar(@RequestBody AgendarConsultaForm.ConsultaDto dto) {
        try {
            if (dto == null) {
                return ResponseEntity.badRequest().build();
            }
            ConsultaResumo consulta = agendarConsulta.executar(
                    dto.pacienteId,
                    dto.medicoId,
                    dto.dataConsulta,
                    dto.horaConsulta,
                    dto.tipo
            );
            return ResponseEntity.ok(consulta);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        consultaRepository.deletar(new ConsultaId(id));
        return ResponseEntity.noContent().build();
    }
}

