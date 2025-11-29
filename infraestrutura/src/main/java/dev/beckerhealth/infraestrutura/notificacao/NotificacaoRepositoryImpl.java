package dev.beckerhealth.infraestrutura.notificacao;

import dev.beckerhealth.dominio.notificacao.Notificacao;
import dev.beckerhealth.dominio.notificacao.NotificacaoRepository;
import dev.beckerhealth.infraestrutura.persistencia.jpa.JpaMapeador;
import dev.beckerhealth.infraestrutura.persistencia.jpa.NotificacaoJpa;
import dev.beckerhealth.infraestrutura.persistencia.jpa.NotificacaoJpaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
class NotificacaoRepositoryImpl implements NotificacaoRepository {
    @Autowired
    NotificacaoJpaRepository repositorio;

    @Autowired
    JpaMapeador mapeador;

    @Override
    public Notificacao salvar(Notificacao notificacao) {
        var notificacaoJpa = mapeador.map(notificacao, NotificacaoJpa.class);
        var notificacaoSalva = repositorio.save(notificacaoJpa);
        return mapeador.map(notificacaoSalva, Notificacao.class);
    }

    @Transactional
    @Override
    public Optional<Notificacao> buscarPorId(Long id) {
        return repositorio.findById(id)
                .map(notificacaoJpa -> mapeador.map(notificacaoJpa, Notificacao.class));
    }

    @Override
    public List<Notificacao> listarTodas() {
        return repositorio.findAll().stream()
                .map(notificacaoJpa -> mapeador.map(notificacaoJpa, Notificacao.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Notificacao> buscarPorDestinatario(Long usuarioId) {
        return repositorio.findByDestinatarioId(usuarioId).stream()
                .map(notificacaoJpa -> mapeador.map(notificacaoJpa, Notificacao.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Notificacao> buscarNaoLidasPorDestinatario(Long usuarioId) {
        return repositorio.findByDestinatarioIdAndStatus(usuarioId, NotificacaoJpa.StatusNotificacao.NAO_LIDA).stream()
                .map(notificacaoJpa -> mapeador.map(notificacaoJpa, Notificacao.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Notificacao> buscarPorTipo(Notificacao.TipoNotificacao tipo) {
        return repositorio.findByTipo(mapTipo(tipo)).stream()
                .map(notificacaoJpa -> mapeador.map(notificacaoJpa, Notificacao.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Notificacao> buscarPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return repositorio.findByDataEnvioBetween(dataInicio, dataFim).stream()
                .map(notificacaoJpa -> mapeador.map(notificacaoJpa, Notificacao.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deletar(Long id) {
        repositorio.deleteById(id);
    }

    private NotificacaoJpa.TipoNotificacao mapTipo(Notificacao.TipoNotificacao tipo) {
        return tipo != null ? NotificacaoJpa.TipoNotificacao.valueOf(tipo.name()) : null;
    }
}

