package dev.beckerhealth.apresentacao.backend.prontuario;

import dev.beckerhealth.aplicacao.prontuario.ProntuarioServicoAplicacao;
import dev.beckerhealth.aplicacao.prontuario.RegistrarProntuario;
import dev.beckerhealth.aplicacao.prontuario.dto.ProntuarioResumo;
import dev.beckerhealth.dominio.prontuario.Prontuario;
import dev.beckerhealth.dominio.prontuario.ProntuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prontuarios")
@CrossOrigin(origins = "*")
public class ProntuarioController {

    private final ProntuarioRepository prontuarioRepository;
    private final ProntuarioServicoAplicacao prontuarioServicoAplicacao;
    private final RegistrarProntuario registrarProntuario;

    public ProntuarioController(ProntuarioRepository prontuarioRepository, 
                               ProntuarioServicoAplicacao prontuarioServicoAplicacao,
                               RegistrarProntuario registrarProntuario) {
        this.prontuarioRepository = prontuarioRepository;
        this.prontuarioServicoAplicacao = prontuarioServicoAplicacao;
        this.registrarProntuario = registrarProntuario;
    }

    @GetMapping
    public List<ProntuarioResumo> listarTodos() {
        return prontuarioServicoAplicacao.pesquisarResumos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prontuario> buscarPorId(@PathVariable Long id) {
        return prontuarioRepository.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/paciente/{pacienteId}")
    public List<ProntuarioResumo> buscarPorPaciente(@PathVariable Long pacienteId) {
        return prontuarioServicoAplicacao.pesquisarResumosPorPaciente(pacienteId);
    }

    @GetMapping("/medico/{medicoId}")
    public List<ProntuarioResumo> buscarPorMedico(@PathVariable Long medicoId) {
        return prontuarioServicoAplicacao.pesquisarResumosPorMedico(medicoId);
    }

    @PostMapping
    public ResponseEntity<ProntuarioResumo> registrar(@RequestBody RegistrarProntuarioForm.ProntuarioDto dto) {
        try {
            if (dto == null) {
                return ResponseEntity.badRequest().build();
            }
            ProntuarioResumo prontuario = registrarProntuario.executar(
                    dto.consultaId,
                    dto.medicoId,
                    dto.anamnese,
                    dto.diagnostico,
                    dto.prescricao,
                    dto.observacoes
            );
            return ResponseEntity.ok(prontuario);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        prontuarioRepository.deletar(id);
        return ResponseEntity.noContent().build();
    }
}


