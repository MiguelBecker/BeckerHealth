package dev.beckerhealth.dominio.notificacao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NotificacaoRepository {

    Notificacao salvar(Notificacao notificacao);

    Optional<Notificacao> buscarPorId(Long id);

    List<Notificacao> listarTodas();

    List<Notificacao> buscarPorDestinatario(Long usuarioId);

    List<Notificacao> buscarNaoLidasPorDestinatario(Long usuarioId);

    List<Notificacao> buscarPorTipo(Notificacao.TipoNotificacao tipo);

    List<Notificacao> buscarPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim);

    void deletar(Long id);
}

