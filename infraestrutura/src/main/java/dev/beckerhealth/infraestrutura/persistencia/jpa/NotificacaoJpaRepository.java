package dev.beckerhealth.infraestrutura.persistencia.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface NotificacaoJpaRepository extends JpaRepository<NotificacaoJpa, Long> {
    List<NotificacaoJpa> findByDestinatarioId(Long usuarioId);
    List<NotificacaoJpa> findByDestinatarioIdAndStatus(Long usuarioId, NotificacaoJpa.StatusNotificacao status);
    List<NotificacaoJpa> findByTipo(NotificacaoJpa.TipoNotificacao tipo);
    List<NotificacaoJpa> findByDataEnvioBetween(LocalDateTime inicio, LocalDateTime fim);
}
