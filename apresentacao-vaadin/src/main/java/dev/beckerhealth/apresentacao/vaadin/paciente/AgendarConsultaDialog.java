package dev.beckerhealth.apresentacao.vaadin.paciente;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import dev.beckerhealth.aplicacao.consultas.AgendarConsulta;
import dev.beckerhealth.dominio.consultas.Consulta;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AgendarConsultaDialog extends Dialog {
    
    private final AgendarConsulta agendarConsulta;
    private final Long pacienteId;
    private final String pacienteNome;
    
    private TextField nomeField;
    private DatePicker dataField;
    private Select<MedicoDTO> medicoSelect;
    private Select<Procedimento> procedimentoSelect;
    private Span valorSpan;
    private TimePicker horaField;
    private Button confirmarButton;
    
    public AgendarConsultaDialog(AgendarConsulta agendarConsulta, 
                                 Long pacienteId,
                                 String pacienteNome) {
        this.agendarConsulta = agendarConsulta;
        this.pacienteId = pacienteId;
        this.pacienteNome = pacienteNome;
        
        setWidth("700px");
        setMaxWidth("90vw");
        setMaxHeight("90vh");
        getStyle().set("border-radius", "12px");
        setHeaderTitle("Agendar Consulta");
        
        criarConteudo();
    }
    
    private void criarConteudo() {
        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(true);
        layout.setSpacing(true);
        layout.setWidthFull();
        layout.getStyle().set("padding", "32px");
        
        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        formLayout.setResponsiveSteps(
            new FormLayout.ResponsiveStep("0", 1),
            new FormLayout.ResponsiveStep("600px", 2)
        );
        formLayout.getStyle().set("margin-bottom", "16px");
        
        nomeField = new TextField("Nome do Paciente");
        nomeField.setValue(pacienteNome);
        nomeField.setReadOnly(true);
        nomeField.setWidthFull();
        nomeField.getStyle().set("margin-bottom", "8px");
        
        dataField = new DatePicker("Data da Consulta");
        dataField.setWidthFull();
        dataField.setMin(LocalDate.now());
        dataField.getStyle().set("margin-bottom", "8px");
        
        horaField = new TimePicker("Hora da Consulta");
        horaField.setWidthFull();
        horaField.getStyle().set("margin-bottom", "8px");
        
        List<MedicoDTO> medicos = List.of(
            new MedicoDTO(1L, "Dr. João Santos", "Cardiologia"),
            new MedicoDTO(2L, "Dra. Ana Paula", "Dermatologia"),
            new MedicoDTO(3L, "Dr. Carlos Silva", "Ortopedia"),
            new MedicoDTO(4L, "Dra. Maria Costa", "Pediatria"),
            new MedicoDTO(5L, "Dr. Roberto Lima", "Clínico Geral")
        );
        medicoSelect = new Select<>();
        medicoSelect.setLabel("Médico");
        medicoSelect.setItems(medicos);
        medicoSelect.setItemLabelGenerator(medico -> 
            medico.getNome() + (medico.getEspecialidade() != null ? " - " + medico.getEspecialidade() : "")
        );
        medicoSelect.setWidthFull();
        medicoSelect.getStyle().set("margin-bottom", "8px");
        
        procedimentoSelect = new Select<>();
        procedimentoSelect.setLabel("Procedimento");
        procedimentoSelect.setItems(Procedimento.values());
        procedimentoSelect.setItemLabelGenerator(Procedimento::getDescricao);
        procedimentoSelect.setWidthFull();
        procedimentoSelect.addValueChangeListener(e -> atualizarValor());
        procedimentoSelect.getStyle().set("margin-bottom", "8px");
        
        valorSpan = new Span();
        valorSpan.getStyle().set("font-size", "18px");
        valorSpan.getStyle().set("font-weight", "600");
        valorSpan.getStyle().set("color", "#10B981");
        valorSpan.getStyle().set("margin-top", "8px");
        
        formLayout.add(nomeField, 2);
        formLayout.add(dataField, 1);
        formLayout.add(horaField, 1);
        formLayout.add(medicoSelect, 2);
        formLayout.add(procedimentoSelect, 2);
        
        Div valorContainer = new Div();
        valorContainer.getStyle().set("margin-top", "16px");
        valorContainer.getStyle().set("padding", "16px");
        valorContainer.getStyle().set("background", "#F0FDF4");
        valorContainer.getStyle().set("border-radius", "8px");
        valorContainer.getStyle().set("border", "1px solid #D1FAE5");
        
        Span valorLabel = new Span("Valor Total: ");
        valorLabel.getStyle().set("font-size", "14px");
        valorLabel.getStyle().set("color", "#6B7280");
        valorLabel.getStyle().set("margin-right", "8px");
        
        valorContainer.add(valorLabel, valorSpan);
        
        HorizontalLayout botoesLayout = new HorizontalLayout();
        botoesLayout.setWidthFull();
        botoesLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        botoesLayout.setSpacing(true);
        botoesLayout.getStyle().set("margin-top", "24px");
        
        Button cancelarButton = new Button("Cancelar");
        cancelarButton.getStyle().set("background", "transparent");
        cancelarButton.getStyle().set("border", "1px solid #E5E7EB");
        cancelarButton.getStyle().set("color", "#6B7280");
        cancelarButton.getStyle().set("border-radius", "8px");
        cancelarButton.getStyle().set("padding", "12px 24px");
        cancelarButton.addClickListener(e -> close());
        
        confirmarButton = new Button("Agendar");
        confirmarButton.getStyle().set("background", "#3B82F6");
        confirmarButton.getStyle().set("color", "white");
        confirmarButton.getStyle().set("border", "none");
        confirmarButton.getStyle().set("border-radius", "8px");
        confirmarButton.getStyle().set("padding", "12px 24px");
        confirmarButton.getStyle().set("font-weight", "600");
        confirmarButton.addClickListener(e -> confirmarAgendamento());
        
        botoesLayout.add(cancelarButton, confirmarButton);
        
        layout.add(formLayout, valorContainer, botoesLayout);
        add(layout);
    }
    
    private void atualizarValor() {
        Procedimento procedimento = procedimentoSelect.getValue();
        if (procedimento != null) {
            String valorFormatado = String.format("R$ %.2f", procedimento.getValor().doubleValue())
                .replace(".", ",");
            valorSpan.setText(valorFormatado);
        } else {
            valorSpan.setText("R$ 0,00");
        }
    }
    
    private void confirmarAgendamento() {
        if (validarCampos()) {
            try {
                MedicoDTO medico = medicoSelect.getValue();
                LocalDate data = dataField.getValue();
                LocalTime hora = horaField.getValue();
                
                agendarConsulta.executar(
                    pacienteId,
                    medico.getId(),
                    data,
                    hora,
                    Consulta.TipoConsulta.ROTINA
                );
                
                close();
                getUI().ifPresent(ui -> {
                    ui.getPage().executeJs("setTimeout(() => window.location.reload(), 300);");
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private boolean validarCampos() {
        boolean valido = true;
        
        if (dataField.getValue() == null) {
            dataField.setInvalid(true);
            dataField.setErrorMessage("Data é obrigatória");
            valido = false;
        }
        
        if (horaField.getValue() == null) {
            horaField.setInvalid(true);
            horaField.setErrorMessage("Hora é obrigatória");
            valido = false;
        }
        
        if (medicoSelect.getValue() == null) {
            medicoSelect.setInvalid(true);
            medicoSelect.setErrorMessage("Médico é obrigatório");
            valido = false;
        }
        
        if (procedimentoSelect.getValue() == null) {
            procedimentoSelect.setInvalid(true);
            procedimentoSelect.setErrorMessage("Procedimento é obrigatório");
            valido = false;
        }
        
        return valido;
    }
}
