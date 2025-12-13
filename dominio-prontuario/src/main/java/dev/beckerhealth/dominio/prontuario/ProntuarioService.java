package dev.beckerhealth.dominio.prontuario;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ProntuarioService {

    private final ProntuarioRepository prontuarioRepository;
    private final ExameRepository exameRepository;
    private final PrescricaoRepository prescricaoRepository;

    public ProntuarioService(ProntuarioRepository prontuarioRepository,
                           ExameRepository exameRepository,
                           PrescricaoRepository prescricaoRepository) {
        this.prontuarioRepository = prontuarioRepository;
        this.exameRepository = exameRepository;
        this.prescricaoRepository = prescricaoRepository;
    }

    public Prontuario criarProntuario(Prontuario prontuario) {
        prontuario.setDataAtendimento(LocalDateTime.now());
        return prontuarioRepository.salvar(prontuario);
    }

    public Prontuario editarProntuario(Prontuario prontuario) {
        return prontuarioRepository.salvar(prontuario);
    }

    public Exame solicitarExame(Exame exame) {
        exame.setDataSolicitacao(LocalDateTime.now());
        exame.setStatus(Exame.StatusExame.PENDENTE);
        return exameRepository.salvar(exame);
    }

    public Exame liberarResultadoExame(Long exameId, String resultado, String observacoes) {
        Optional<Exame> exameOpt = exameRepository.buscarPorId(exameId);
        if (exameOpt.isEmpty()) {
            throw new IllegalArgumentException("Exame não encontrado");
        }

        Exame exame = exameOpt.get();
        exame.liberarResultado(resultado, observacoes);
        return exameRepository.salvar(exame);
    }

    public String visualizarResultadoExame(Long exameId) {
        Optional<Exame> exameOpt = exameRepository.buscarPorId(exameId);
        if (exameOpt.isEmpty()) {
            throw new IllegalArgumentException("Exame não encontrado");
        }

        Exame exame = exameOpt.get();
        return exame.getResultado();
    }

    public Prescricao criarPrescricao(Prescricao prescricao) {
        if (prescricao.getDataValidade() == null) {
            throw new IllegalArgumentException("Prescrições devem conter validade obrigatória");
        }

        prescricao.setDataEmissao(LocalDate.now());
        return prescricaoRepository.salvar(prescricao);
    }

    // Métodos de busca para Prontuário
    public Optional<Prontuario> buscarPorId(Long id) {
        return prontuarioRepository.buscarPorId(id);
    }

    public List<Prontuario> listarTodos() {
        return prontuarioRepository.listarTodos();
    }

    public List<Prontuario> buscarPorPaciente(Long pacienteId) {
        return prontuarioRepository.buscarPorPaciente(pacienteId);
    }

    public List<Prontuario> buscarPorMedico(Long medicoId) {
        return prontuarioRepository.buscarPorMedico(medicoId);
    }

    public void deletar(Long id) {
        prontuarioRepository.deletar(id);
    }

    // Métodos de busca para Exame
    public List<Exame> listarExames() {
        return exameRepository.listarTodos();
    }

    public List<Exame> buscarExamesPorProntuario(Long prontuarioId) {
        return exameRepository.buscarPorProntuario(prontuarioId);
    }

    public List<Exame> buscarExamesPorConsulta(Long consultaId) {
        return exameRepository.buscarPorConsulta(consultaId);
    }

    // Métodos de busca para Prescricao
    public List<Prescricao> listarPrescricoes() {
        return prescricaoRepository.listarTodos();
    }

    public List<Prescricao> buscarPrescricoesPorProntuario(Long prontuarioId) {
        return prescricaoRepository.buscarPorProntuario(prontuarioId);
    }
}
