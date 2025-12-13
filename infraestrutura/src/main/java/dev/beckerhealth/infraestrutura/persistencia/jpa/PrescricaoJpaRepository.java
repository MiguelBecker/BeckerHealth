package dev.beckerhealth.infraestrutura.persistencia.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PrescricaoJpaRepository extends JpaRepository<PrescricaoJpa, Long> {
    List<PrescricaoJpa> findByProntuarioId(Long prontuarioId);
    
    @Query("SELECT p FROM PrescricaoJpa p, ProntuarioJpa pr WHERE p.prontuarioId = pr.id AND pr.paciente.id = :pacienteId")
    List<PrescricaoJpa> findByPacienteId(@Param("pacienteId") Long pacienteId);
    
    @Query("SELECT p FROM PrescricaoJpa p, ProntuarioJpa pr WHERE p.prontuarioId = pr.id AND pr.paciente.id = :pacienteId AND p.validade >= :hoje")
    List<PrescricaoJpa> findValidasByPacienteId(@Param("pacienteId") Long pacienteId, @Param("hoje") LocalDate hoje);
}

