package dev.beckerhealth.apresentacao.backend.medicos;

import dev.beckerhealth.infraestrutura.persistencia.jpa.MedicoJpaRepository;
import dev.beckerhealth.infraestrutura.persistencia.jpa.MedicoJpa;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/medicos")
@CrossOrigin(origins = "*")
public class MedicoController {

    private final MedicoJpaRepository medicoRepository;

    public MedicoController(MedicoJpaRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    @GetMapping
    public ResponseEntity<List<MedicoResponse>> listarTodos() {
        List<MedicoResponse> medicos = medicoRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(medicos);
    }

    private MedicoResponse toResponse(MedicoJpa medico) {
        MedicoResponse response = new MedicoResponse();
        response.setId(medico.id);
        response.setNome(medico.nome);
        response.setEspecialidade(medico.especialidade);
        return response;
    }

    public static class MedicoResponse {
        private Long id;
        private String nome;
        private String especialidade;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getEspecialidade() {
            return especialidade;
        }

        public void setEspecialidade(String especialidade) {
            this.especialidade = especialidade;
        }
    }
}

