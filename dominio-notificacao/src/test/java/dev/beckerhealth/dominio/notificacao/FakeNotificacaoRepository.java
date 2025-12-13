package dev.beckerhealth.dominio.notificacao;

import java.time.LocalDateTime;
import java.util.*;

public class FakeNotificacaoRepository implements NotificacaoRepository {

    private final Map<Long, Notificacao> banco = new HashMap<>();
    private long seq = 1L;

    @Override
    public Notificacao salvar(Notificacao notificacao) {
        if (notificacao.getId() == null) {
            notificacao.setId(seq++);
        }
        banco.put(notificacao.getId(), notificacao);
        return notificacao;
    }

    @Override
    public Optional<Notificacao> buscarPorId(Long id) {
        return Optional.ofNullable(banco.get(id));
    }

    @Override
    public List<Notificacao> listarTodas() {
        return new ArrayList<>(banco.values());
    }

    @Override
    public List<Notificacao> buscarPorDestinatario(Long destinatarioId) {
        return banco.values().stream()
                .filter(n -> n.getDestinatario().getId().equals(destinatarioId))
                .toList();
    }

    @Override
    public List<Notificacao> buscarNaoLidasPorDestinatario(Long destinatarioId) {
        return banco.values().stream()
                .filter(n -> n.getDestinatario().getId().equals(destinatarioId))
                .filter(n -> n.getStatus() == Notificacao.StatusNotificacao.NAO_LIDA)
                .toList();
    }

    @Override
    public List<Notificacao> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return banco.values().stream()
                .filter(n -> !n.getDataEnvio().isBefore(inicio) && !n.getDataEnvio().isAfter(fim))
                .toList();
    }

    @Override
    public List<Notificacao> buscarPorTipo(Notificacao.TipoNotificacao tipo) {
        return banco.values().stream()
                .filter(n -> n.getTipo() == tipo)
                .toList();
    }

    @Override
    public void deletar(Long id) {
        banco.remove(id);
    }
}
