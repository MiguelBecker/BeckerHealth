package com.br.beckerhealth.bdd_projeto.repository;

import com.br.beckerhealth.bdd_projeto.models.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Optional<Medico> findByCrm(String crm);
}
