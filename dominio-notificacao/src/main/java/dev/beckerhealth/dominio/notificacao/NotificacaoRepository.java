package dev.beckerhealth.dominio.notificacao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NotificacaoRepository {
    Notificacao salvar(Notificacao notificacao);

    Optional<Notificacao> buscarPorId(Long id);

    List<Notificacao> listarTodas();

    List<Notificacao> buscarPorDestinatario(Long destinatarioId);

    List<Notificacao> buscarNaoLidasPorDestinatario(Long destinatarioId);

    List<Notificacao> buscarPorTipo(Notificacao.TipoNotificacao tipo);

    List<Notificacao> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim);

    void deletar(Long id);
}
