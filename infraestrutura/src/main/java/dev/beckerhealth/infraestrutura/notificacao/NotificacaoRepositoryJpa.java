package dev.beckerhealth.infraestrutura.notificacao;

import dev.beckerhealth.dominio.notificacao.Notificacao;
import dev.beckerhealth.dominio.notificacao.NotificacaoRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface NotificacaoRepositoryJpa extends JpaRepository<Notificacao, Long>, NotificacaoRepository {

    @Override
    default Notificacao salvar(Notificacao notificacao) {
        return save(notificacao);
    }

    @Override
    default Optional<Notificacao> buscarPorId(Long id) {
        return findById(id);
    }

    @Override
    default List<Notificacao> listarTodas() {
        return findAll();
    }

    @Override
    List<Notificacao> findByDestinatarioId(Long usuarioId);

    @Override
    default List<Notificacao> buscarPorDestinatario(Long usuarioId) {
        return findByDestinatarioId(usuarioId);
    }

    @Override
    List<Notificacao> findByDestinatarioIdAndStatus(Long usuarioId, Notificacao.StatusNotificacao status);

    @Override
    default List<Notificacao> buscarNaoLidasPorDestinatario(Long usuarioId) {
        return findByDestinatarioIdAndStatus(usuarioId, Notificacao.StatusNotificacao.NAO_LIDA);
    }

    @Override
    List<Notificacao> findByTipo(Notificacao.TipoNotificacao tipo);

    @Override
    default List<Notificacao> buscarPorTipo(Notificacao.TipoNotificacao tipo) {
        return findByTipo(tipo);
    }

    @Override
    List<Notificacao> findByDataEnvioBetween(LocalDateTime dataInicio, LocalDateTime dataFim);

    @Override
    default List<Notificacao> buscarPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return findByDataEnvioBetween(dataInicio, dataFim);
    }

    @Override
    default void deletar(Long id) {
        deleteById(id);
    }
}

