package dev.beckerhealth.aplicacao.prontuario;

import dev.beckerhealth.dominio.prontuario.Exame;
import dev.beckerhealth.dominio.prontuario.ExameRepository;
import org.springframework.stereotype.Service;

@Service
public class LiberarExame {
    private final ExameRepository exameRepository;

    public LiberarExame(ExameRepository exameRepository) {
        this.exameRepository = exameRepository;
    }

    public Exame executar(Long exameId, String resultado) {
        Exame exame = exameRepository.buscarPorId(exameId)
                .orElseThrow(() -> new IllegalArgumentException("Exame não encontrado"));

        exame.setResultado(resultado);
        exame.setLiberado(true);

        return exameRepository.salvar(exame);
    }
}

