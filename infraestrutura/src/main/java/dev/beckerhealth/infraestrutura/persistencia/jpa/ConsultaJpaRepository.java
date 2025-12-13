package dev.beckerhealth.infraestrutura.persistencia.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ConsultaJpaRepository extends JpaRepository<ConsultaJpa, Long> {
    List<ConsultaJpa> findByDataConsulta(LocalDate data);
    List<ConsultaJpa> findByStatus(ConsultaJpa.StatusConsulta status);
    List<ConsultaJpa> findByPacienteId(Long pacienteId);
    List<ConsultaJpa> findByMedicoId(Long medicoId);
    
    @Query("SELECT c FROM ConsultaJpa c JOIN FETCH c.paciente JOIN FETCH c.medico WHERE c.id = :id")
    Optional<ConsultaJpa> findByIdWithRelations(@Param("id") Long id);
}
