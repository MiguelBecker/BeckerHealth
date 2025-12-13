package dev.beckerhealth.infraestrutura.persistencia.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface ProntuarioJpaRepository extends JpaRepository<ProntuarioJpa, Long> {
    List<ProntuarioJpa> findByPacienteId(Long pacienteId);
    List<ProntuarioJpa> findByMedicoResponsavelId(Long medicoId);
    List<ProntuarioJpa> findByDataAtendimentoBetween(LocalDateTime inicio, LocalDateTime fim);
}
