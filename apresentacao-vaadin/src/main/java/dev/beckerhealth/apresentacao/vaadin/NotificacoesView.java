package dev.beckerhealth.apresentacao.vaadin;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import dev.beckerhealth.dominio.notificacao.Notificacao;
import dev.beckerhealth.dominio.notificacao.NotificacaoRepository;

@PageTitle("Notificações")
@Route(value = "notificacoes", layout = MainView.class)
public class NotificacoesView extends VerticalLayout {

    private final NotificacaoRepository notificacaoRepository;
    private final Grid<Notificacao> grid;

    public NotificacoesView(NotificacaoRepository notificacaoRepository) {
        this.notificacaoRepository = notificacaoRepository;
        this.grid = new Grid<>(Notificacao.class, false);

        configureGrid();
        add(grid);
    }

    private void configureGrid() {
        grid.addColumn(notificacao -> notificacao.getDestinatario().getNome()).setHeader("Destinatário");
        grid.addColumn(Notificacao::getTitulo).setHeader("Título");
        grid.addColumn(Notificacao::getDataEnvio).setHeader("Data Envio");
        grid.addColumn(notificacao -> notificacao.getTipo().name()).setHeader("Tipo");
        grid.addColumn(notificacao -> notificacao.getStatus().name()).setHeader("Status");

        updateList();
    }

    private void updateList() {
        grid.setItems(notificacaoRepository.listarTodas());
    }
}

