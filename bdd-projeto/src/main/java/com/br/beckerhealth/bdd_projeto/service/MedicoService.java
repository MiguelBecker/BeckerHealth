package com.br.beckerhealth.bdd_projeto.service;

import com.br.beckerhealth.bdd_projeto.models.Medico;
import com.br.beckerhealth.bdd_projeto.repository.MedicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicoService {

    private final MedicoRepository medicoRepository;

    public MedicoService(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    public List<Medico> listarTodos() {
        return medicoRepository.findAll();
    }

    public Optional<Medico> buscarPorId(Long id) {
        return medicoRepository.findById(id);
    }

    public Optional<Medico> buscarPorCrm(String crm) {
        return medicoRepository.findByCrm(crm);
    }

    public Medico salvar(Medico medico) {
        return medicoRepository.save(medico);
    }

    public void deletar(Long id) {
        medicoRepository.deleteById(id);
    }
}
