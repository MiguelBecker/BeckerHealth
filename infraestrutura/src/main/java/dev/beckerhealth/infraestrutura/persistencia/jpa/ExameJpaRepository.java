package dev.beckerhealth.infraestrutura.persistencia.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExameJpaRepository extends JpaRepository<ExameJpa, Long> {
    List<ExameJpa> findByConsultaId(Long consultaId);
    
    @Query("SELECT e FROM ExameJpa e, ConsultaJpa c WHERE e.consultaId = c.id AND c.paciente.id = :pacienteId")
    List<ExameJpa> findByPacienteId(@Param("pacienteId") Long pacienteId);
    
    @Query("SELECT e FROM ExameJpa e, ConsultaJpa c WHERE e.consultaId = c.id AND c.paciente.id = :pacienteId AND e.liberado = true")
    List<ExameJpa> findLiberadosByPacienteId(@Param("pacienteId") Long pacienteId);
}

