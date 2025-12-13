package dev.beckerhealth.infraestrutura.persistencia.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExameJpaRepository extends JpaRepository<ExameJpa, Long> {
    List<ExameJpa> findByProntuarioId(Long prontuarioId);
    List<ExameJpa> findByConsultaId(Long consultaId);
}
