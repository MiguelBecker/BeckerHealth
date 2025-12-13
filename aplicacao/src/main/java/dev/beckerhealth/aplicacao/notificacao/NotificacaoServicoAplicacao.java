package dev.beckerhealth.aplicacao.notificacao;

import dev.beckerhealth.aplicacao.notificacao.dto.NotificacaoResumo;
import dev.beckerhealth.dominio.notificacao.Notificacao;
import dev.beckerhealth.dominio.notificacao.NotificacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificacaoServicoAplicacao {
    private final NotificacaoRepository repository;

    public NotificacaoServicoAplicacao(NotificacaoRepository repository) {
        this.repository = repository;
    }

    public List<NotificacaoResumo> buscarNotificacoesPorUsuario(Long usuarioId) {
        return repository.buscarPorDestinatario(usuarioId).stream()
                .map(this::mapearParaResumo)
                .collect(Collectors.toList());
    }

    public List<NotificacaoResumo> buscarNotificacoesNaoLidas(Long usuarioId) {
        return repository.buscarNaoLidasPorDestinatario(usuarioId).stream()
                .map(this::mapearParaResumo)
                .collect(Collectors.toList());
    }

    public int contarNotificacoesNaoLidas(Long usuarioId) {
        return repository.buscarNaoLidasPorDestinatario(usuarioId).size();
    }

    public void marcarComoLida(Long notificacaoId) {
        repository.buscarPorId(notificacaoId).ifPresent(notificacao -> {
            notificacao.marcarComoLida();
            repository.salvar(notificacao);
        });
    }

    private NotificacaoResumo mapearParaResumo(Notificacao notificacao) {
        return new NotificacaoResumo(
                notificacao.getId(),
                notificacao.getTitulo(),
                notificacao.getMensagem(),
                notificacao.getDataEnvio(),
                notificacao.getTipo() != null ? notificacao.getTipo().name() : null,
                notificacao.getStatus() != null ? notificacao.getStatus().name() : null,
                notificacao.getLink(),
                notificacao.getStatus() == Notificacao.StatusNotificacao.NAO_LIDA
        );
    }
}

