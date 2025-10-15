package dev.beckerhealth.apresentacao.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import dev.beckerhealth.dominio.consultas.Consulta;
import dev.beckerhealth.dominio.consultas.ConsultaRepository;

@PageTitle("Consultas")
@Route(value = "consultas", layout = MainView.class)
public class ConsultasView extends VerticalLayout {

    private final ConsultaRepository consultaRepository;
    private final Grid<Consulta> grid;

    public ConsultasView(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
        this.grid = new Grid<>(Consulta.class, false);

        configureGrid();
        add(grid);
    }

    private void configureGrid() {
        grid.addColumn(consulta -> consulta.getPaciente().getNome()).setHeader("Paciente");
        grid.addColumn(consulta -> consulta.getMedico().getNome()).setHeader("Médico");
        grid.addColumn(Consulta::getDataConsulta).setHeader("Data");
        grid.addColumn(Consulta::getHoraConsulta).setHeader("Hora");
        grid.addColumn(consulta -> consulta.getTipo().name()).setHeader("Tipo");
        grid.addColumn(consulta -> consulta.getStatus().name()).setHeader("Status");

        updateList();
    }

    private void updateList() {
        grid.setItems(consultaRepository.listarTodas());
    }
}

