package dev.beckerhealth.dominio.notificacao;

import java.time.LocalDateTime;
import java.util.List;

public class NotificacaoService {

    private final NotificacaoRepository repository;

    public NotificacaoService(NotificacaoRepository repository) {
        this.repository = repository;
    }

    public Notificacao enviarNotificacao(Notificacao notificacao) {
        notificacao.setDataEnvio(LocalDateTime.now());
        return repository.salvar(notificacao);
    }

    public List<Notificacao> listarPorDestinatario(Long destinatarioId) {
        return repository.buscarPorDestinatario(destinatarioId);
    }

    public List<Notificacao> listarNaoLidas(Long destinatarioId) {
        return repository.buscarNaoLidasPorDestinatario(destinatarioId);
    }

    public void marcarComoLida(Long notificacaoId) {
        repository.buscarPorId(notificacaoId).ifPresent(Notificacao::marcarComoLida);
    }

    public void excluir(Long notificacaoId) {
        repository.deletar(notificacaoId);
    }
}
