package dev.beckerhealth.apresentacao.vaadin;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import dev.beckerhealth.dominio.prontuario.Prontuario;
import dev.beckerhealth.dominio.prontuario.ProntuarioRepository;

@PageTitle("Prontuários")
@Route(value = "prontuarios", layout = MainView.class)
public class ProntuariosView extends VerticalLayout {

    private final ProntuarioRepository prontuarioRepository;
    private final Grid<Prontuario> grid;

    public ProntuariosView(ProntuarioRepository prontuarioRepository) {
        this.prontuarioRepository = prontuarioRepository;
        this.grid = new Grid<>(Prontuario.class, false);

        configureGrid();
        add(grid);
    }

    private void configureGrid() {
        grid.addColumn(prontuario -> prontuario.getPaciente().getNome()).setHeader("Paciente");
        grid.addColumn(prontuario -> prontuario.getMedicoResponsavel().getNome()).setHeader("Médico");
        grid.addColumn(Prontuario::getDataAtendimento).setHeader("Data Atendimento");
        grid.addColumn(prontuario -> prontuario.getTipoAtendimento().name()).setHeader("Tipo");

        updateList();
    }

    private void updateList() {
        grid.setItems(prontuarioRepository.listarTodos());
    }
}

