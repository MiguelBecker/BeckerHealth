package dev.beckerhealth.apresentacao.vaadin.paciente;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.timepicker.TimePicker;
import dev.beckerhealth.aplicacao.consultas.dto.ConsultaResumo;
import dev.beckerhealth.dominio.consultas.Consulta;
import dev.beckerhealth.dominio.consultas.ConsultaRepository;
import dev.beckerhealth.dominio.consultas.ConsultaId;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReagendarConsultaDialog extends Dialog {
    
    private final ConsultaResumo consulta;
    private final ConsultaRepository consultaRepository;
    private final Runnable onReagendado;
    
    private DatePicker dataField;
    private TimePicker horaField;
    
    public ReagendarConsultaDialog(ConsultaResumo consulta, 
                                   ConsultaRepository consultaRepository,
                                   Runnable onReagendado) {
        this.consulta = consulta;
        this.consultaRepository = consultaRepository;
        this.onReagendado = onReagendado;
        
        setWidth("600px");
        setMaxWidth("90vw");
        getStyle().set("border-radius", "12px");
        setHeaderTitle("Reagendar Consulta");
        
        criarConteudo();
    }
    
    private void criarConteudo() {
        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(false);
        layout.setSpacing(true);
        layout.setWidthFull();
        layout.getStyle().set("padding", "24px");
        
        Span infoLabel = new Span("Selecione a nova data e horário para a consulta:");
        infoLabel.getStyle().set("font-size", "14px");
        infoLabel.getStyle().set("color", "#6B7280");
        infoLabel.getStyle().set("margin-bottom", "16px");
        
        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        formLayout.setResponsiveSteps(
            new FormLayout.ResponsiveStep("0", 1),
            new FormLayout.ResponsiveStep("500px", 2)
        );
        
        dataField = new DatePicker("Nova Data");
        dataField.setWidthFull();
        dataField.setMin(LocalDate.now());
        if (consulta.getDataConsulta() != null) {
            dataField.setValue(consulta.getDataConsulta());
        }
        
        horaField = new TimePicker("Nova Hora");
        horaField.setWidthFull();
        if (consulta.getHoraConsulta() != null) {
            horaField.setValue(consulta.getHoraConsulta());
        }
        
        formLayout.add(dataField, 1);
        formLayout.add(horaField, 1);
        
        HorizontalLayout botoesLayout = new HorizontalLayout();
        botoesLayout.setWidthFull();
        botoesLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        botoesLayout.setSpacing(true);
        botoesLayout.getStyle().set("margin-top", "24px");
        
        Button cancelarButton = new Button("Cancelar");
        cancelarButton.getStyle().set("background", "transparent");
        cancelarButton.getStyle().set("border", "1px solid #E5E7EB");
        cancelarButton.getStyle().set("color", "#6B7280");
        cancelarButton.addClickListener(e -> close());
        
        Button confirmarButton = new Button("Confirmar Reagendamento");
        confirmarButton.getStyle().set("background", "#3B82F6");
        confirmarButton.getStyle().set("color", "white");
        confirmarButton.getStyle().set("border", "none");
        confirmarButton.addClickListener(e -> confirmarReagendamento());
        
        botoesLayout.add(cancelarButton, confirmarButton);
        
        layout.add(infoLabel, formLayout, botoesLayout);
        add(layout);
    }
    
    private void confirmarReagendamento() {
        if (validarCampos()) {
            try {
                Consulta consultaEntity = consultaRepository.buscarPorId(new ConsultaId(consulta.getId()))
                    .orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada"));
                
                LocalDate novaData = dataField.getValue();
                LocalTime novaHora = horaField.getValue();

                if (novaData != null && !novaData.equals(consultaEntity.getDataConsulta())) {
                    consultaEntity.setDataConsulta(novaData);
                }
                if (novaHora != null && !novaHora.equals(consultaEntity.getHoraConsulta())) {
                    consultaEntity.setHoraConsulta(novaHora);
                }
                
                consultaRepository.salvar(consultaEntity);
                
                close();
                onReagendado.run();
                getUI().ifPresent(ui -> ui.getPage().reload());
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
        
        return valido;
    }
}
