package dev.beckerhealth.infraestrutura.persistencia.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface ConsultaJpaRepository extends JpaRepository<ConsultaJpa, Long> {
    List<ConsultaJpa> findByDataConsulta(LocalDate data);
    List<ConsultaJpa> findByStatus(ConsultaJpa.StatusConsulta status);
    List<ConsultaJpa> findByPacienteId(Long pacienteId);
    List<ConsultaJpa> findByMedicoId(Long medicoId);
}
