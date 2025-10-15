package dev.beckerhealth.apresentacao.vaadin;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import dev.beckerhealth.dominio.relatorios.Relatorio;
import dev.beckerhealth.dominio.relatorios.RelatorioRepository;

@PageTitle("Relatórios")
@Route(value = "relatorios", layout = MainView.class)
public class RelatoriosView extends VerticalLayout {

    private final RelatorioRepository relatorioRepository;
    private final Grid<Relatorio> grid;

    public RelatoriosView(RelatorioRepository relatorioRepository) {
        this.relatorioRepository = relatorioRepository;
        this.grid = new Grid<>(Relatorio.class, false);

        configureGrid();
        add(grid);
    }

    private void configureGrid() {
        grid.addColumn(Relatorio::getTitulo).setHeader("Título");
        grid.addColumn(relatorio -> relatorio.getTipo().name()).setHeader("Tipo");
        grid.addColumn(Relatorio::getDataGeracao).setHeader("Data Geração");
        grid.addColumn(relatorio -> relatorio.getStatus().name()).setHeader("Status");

        updateList();
    }

    private void updateList() {
        grid.setItems(relatorioRepository.listarTodos());
    }
}

