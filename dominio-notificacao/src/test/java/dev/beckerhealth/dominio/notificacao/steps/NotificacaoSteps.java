package dev.beckerhealth.dominio.notificacao.steps;

import dev.beckerhealth.dominio.compartilhado.usuario.Usuario;
import dev.beckerhealth.dominio.notificacao.*;
import io.cucumber.java.pt.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

public class NotificacaoSteps {

    private NotificacaoService service;
    private FakeNotificacaoRepository fakeRepo;
    private List<Notificacao> notificacoes;
    private Usuario paciente;

    @Dado("um paciente existe para receber notificações")
    public void paciente_existe() {
        paciente = new Usuario();
        paciente.setId(1L);
        paciente.setNome("João da Silva");

        fakeRepo = new FakeNotificacaoRepository();
        service = new NotificacaoService(fakeRepo);
    }

    @Quando("o sistema envia uma notificação de exame com mensagem {string}")
    public void sistema_envia_notificacao(String mensagem) {
        Notificacao notif = new Notificacao();
        notif.setDestinatario(paciente);
        notif.setTitulo("Resultado disponível");
        notif.setMensagem(mensagem);
        notif.setTipo(Notificacao.TipoNotificacao.RESULTADO_EXAME);
        notif.setStatus(Notificacao.StatusNotificacao.NAO_LIDA);

        service.enviarNotificacao(notif);
    }

    @Entao("o paciente deve receber a notificação com mensagem {string}")
    public void paciente_recebe_notificacao(String msgEsperada) {
        notificacoes = service.listarPorDestinatario(paciente.getId());
        assertThat(notificacoes).isNotEmpty();
        assertThat(notificacoes.get(0).getMensagem()).isEqualTo(msgEsperada);
    }

    @Entao("a notificação deve estar marcada como não lida")
    public void notificacao_nao_lida() {
        assertThat(notificacoes.get(0).getStatus()).isEqualTo(Notificacao.StatusNotificacao.NAO_LIDA);
    }
}
