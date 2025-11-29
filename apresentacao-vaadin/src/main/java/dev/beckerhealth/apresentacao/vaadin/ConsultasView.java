package dev.beckerhealth.apresentacao.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import dev.beckerhealth.aplicacao.consultas.ConsultaServicoAplicacao;
import dev.beckerhealth.aplicacao.consultas.dto.ConsultaResumo;

@PageTitle("Consultas")
@Route(value = "consultas", layout = MainView.class)
public class ConsultasView extends VerticalLayout {

    private final ConsultaServicoAplicacao consultaServicoAplicacao;
    private final Grid<ConsultaResumo> grid;

    public ConsultasView(ConsultaServicoAplicacao consultaServicoAplicacao) {
        this.consultaServicoAplicacao = consultaServicoAplicacao;
        this.grid = new Grid<>(ConsultaResumo.class, false);

        configureGrid();
        add(grid);
    }

    private void configureGrid() {
        grid.addColumn(ConsultaResumo::getPacienteNome).setHeader("Paciente");
        grid.addColumn(ConsultaResumo::getMedicoNome).setHeader("Médico");
        grid.addColumn(ConsultaResumo::getDataConsulta).setHeader("Data");
        grid.addColumn(ConsultaResumo::getHoraConsulta).setHeader("Hora");
        grid.addColumn(ConsultaResumo::getTipo).setHeader("Tipo");
        grid.addColumn(ConsultaResumo::getStatus).setHeader("Status");

        updateList();
    }

    private void updateList() {
        grid.setItems(consultaServicoAplicacao.pesquisarResumos());
    }
}

