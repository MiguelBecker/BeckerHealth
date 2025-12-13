package dev.beckerhealth.infraestrutura.persistencia.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface RelatorioJpaRepository extends JpaRepository<RelatorioJpa, Long> {
    List<RelatorioJpa> findByTipo(RelatorioJpa.TipoRelatorio tipo);
    List<RelatorioJpa> findByDataGeracaoBetween(LocalDateTime inicio, LocalDateTime fim);
    List<RelatorioJpa> findByGeradoPorId(Long usuarioId);
}
