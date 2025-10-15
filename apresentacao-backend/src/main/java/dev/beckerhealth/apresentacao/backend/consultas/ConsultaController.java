package dev.beckerhealth.apresentacao.backend.consultas;

import dev.beckerhealth.aplicacao.consultas.AgendarConsulta;
import dev.beckerhealth.aplicacao.consultas.dto.ConsultaResumo;
import dev.beckerhealth.dominio.consultas.Consulta;
import dev.beckerhealth.dominio.consultas.ConsultaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/consultas")
@RequiredArgsConstructor
public class ConsultaController {

    private final ConsultaRepository consultaRepository;
    private final AgendarConsulta agendarConsulta;

    @GetMapping
    public List<Consulta> listarTodas() {
        return consultaRepository.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consulta> buscarPorId(@PathVariable Long id) {
        return consultaRepository.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/data/{data}")
    public List<Consulta> buscarPorData(@PathVariable String data) {
        return consultaRepository.buscarPorData(LocalDate.parse(data));
    }

    @PostMapping
    public ResponseEntity<ConsultaResumo> agendar(@RequestBody AgendarConsultaForm form) {
        try {
            ConsultaResumo consulta = agendarConsulta.executar(
                    form.getPacienteId(),
                    form.getMedicoId(),
                    form.getDataConsulta(),
                    form.getHoraConsulta(),
                    form.getTipo()
            );
            return ResponseEntity.ok(consulta);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        consultaRepository.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

