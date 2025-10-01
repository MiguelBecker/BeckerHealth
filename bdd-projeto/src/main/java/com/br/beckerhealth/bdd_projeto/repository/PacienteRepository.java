package com.br.beckerhealth.bdd_projeto.repository;

import com.br.beckerhealth.bdd_projeto.models.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByCpf(String cpf);
}
