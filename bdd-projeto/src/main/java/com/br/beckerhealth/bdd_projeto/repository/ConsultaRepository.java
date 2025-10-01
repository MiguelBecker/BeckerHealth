package com.br.beckerhealth.bdd_projeto.repository;

import com.br.beckerhealth.bdd_projeto.models.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    List<Consulta> findByDataConsulta(LocalDate data);
    List<Consulta> findByStatus(Consulta.StatusConsulta status);
}
