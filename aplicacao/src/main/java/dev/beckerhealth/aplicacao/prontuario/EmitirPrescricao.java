package dev.beckerhealth.aplicacao.prontuario;

import dev.beckerhealth.dominio.prontuario.Prescricao;
import dev.beckerhealth.dominio.prontuario.PrescricaoRepository;
import dev.beckerhealth.dominio.prontuario.Prontuario;
import dev.beckerhealth.dominio.prontuario.ProntuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EmitirPrescricao {
    private final PrescricaoRepository prescricaoRepository;
    private final ProntuarioRepository prontuarioRepository;

    public EmitirPrescricao(PrescricaoRepository prescricaoRepository,
                           ProntuarioRepository prontuarioRepository) {
        this.prescricaoRepository = prescricaoRepository;
        this.prontuarioRepository = prontuarioRepository;
    }

    public Prescricao executar(Long prontuarioId, String conteudo, LocalDate validade, String assinaturaMedica) {
        // Validar que o prontuário existe
        Prontuario prontuario = prontuarioRepository.buscarPorId(prontuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Prontuário não encontrado"));

        // Validar validade
        if (validade.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("A validade da prescrição não pode ser anterior à data atual");
        }

        // Criar prescrição
        Prescricao prescricao = new Prescricao();
        prescricao.setProntuarioId(prontuarioId);
        prescricao.setConteudo(conteudo);
        prescricao.setValidade(validade);
        prescricao.setAssinaturaMedica(assinaturaMedica);

        return prescricaoRepository.salvar(prescricao);
    }
}

