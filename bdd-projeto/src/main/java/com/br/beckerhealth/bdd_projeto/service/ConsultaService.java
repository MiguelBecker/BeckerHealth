package com.br.beckerhealth.bdd_projeto.service;

import com.br.beckerhealth.bdd_projeto.models.Consulta;
import com.br.beckerhealth.bdd_projeto.repository.ConsultaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ConsultaService {

    private final ConsultaRepository consultaRepository;

    public ConsultaService(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    public List<Consulta> listarTodas() {
        return consultaRepository.findAll();
    }

    public Optional<Consulta> buscarPorId(Long id) {
        return consultaRepository.findById(id);
    }

    public List<Consulta> buscarPorData(LocalDate data) {
        return consultaRepository.findByDataConsulta(data);
    }

    public List<Consulta> buscarPorStatus(Consulta.StatusConsulta status) {
        return consultaRepository.findByStatus(status);
    }

    public Consulta salvar(Consulta consulta) {
        return consultaRepository.save(consulta);
    }

    public void deletar(Long id) {
        consultaRepository.deleteById(id);
    }
}
