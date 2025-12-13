package dev.beckerhealth.dominio.prontuario;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ProntuarioService {

    private final ProntuarioRepository repository;

    public ProntuarioService(ProntuarioRepository repository) {
        this.repository = repository;
    }

    public Prontuario criarProntuario(Prontuario prontuario) {
        if (prontuario.getMedicoResponsavel() == null) {
            throw new IllegalArgumentException("Apenas médicos podem criar/editar prontuários");
        }
        prontuario.setDataAtendimento(LocalDateTime.now());
        return repository.salvar(prontuario);
    }

    public Optional<Prontuario> buscarPorId(Long id) {
        return repository.buscarPorId(id);
    }

    public List<Prontuario> listarTodos() {
        return repository.listarTodos();
    }

    public List<Prontuario> buscarPorPaciente(Long pacienteId) {
        return repository.buscarPorPaciente(pacienteId);
    }

    public List<Prontuario> buscarPorMedico(Long medicoId) {
        return repository.buscarPorMedico(medicoId);
    }

    public void deletar(Long id) {
        repository.deletar(id);
    }
}
