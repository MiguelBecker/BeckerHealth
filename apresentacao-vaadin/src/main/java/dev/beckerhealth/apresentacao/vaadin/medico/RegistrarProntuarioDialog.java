package dev.beckerhealth.apresentacao.vaadin.medico;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import dev.beckerhealth.aplicacao.consultas.ConsultaServicoAplicacao;
import dev.beckerhealth.aplicacao.consultas.dto.ConsultaResumo;
import dev.beckerhealth.aplicacao.prontuario.RegistrarProntuario;

import java.util.List;

public class RegistrarProntuarioDialog extends Dialog {
    
    private final RegistrarProntuario registrarProntuario;
    private final ConsultaServicoAplicacao consultaServicoAplicacao;
    private final Long medicoId;
    private final Runnable onRegistrado;
    
    private Select<ConsultaResumo> consultaSelect;
    private TextArea anamneseField;
    private TextArea diagnosticoField;
    private TextArea prescricaoField;
    private TextArea observacoesField;
    
    public RegistrarProntuarioDialog(RegistrarProntuario registrarProntuario,
                                    ConsultaServicoAplicacao consultaServicoAplicacao,
                                    Long medicoId,
                                    Runnable onRegistrado) {
        this.registrarProntuario = registrarProntuario;
        this.consultaServicoAplicacao = consultaServicoAplicacao;
        this.medicoId = medicoId;
        this.onRegistrado = onRegistrado;
        
        setWidth("800px");
        setMaxWidth("90vw");
        setMaxHeight("90vh");
        setHeaderTitle("Registrar Prontuário");
        
        criarConteudo();
    }
    
    private void criarConteudo() {
        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(false);
        layout.setSpacing(true);
        layout.setWidthFull();
        layout.getStyle().set("padding", "24px");
        
        Span infoLabel = new Span("Preencha os dados do prontuário para a consulta selecionada:");
        infoLabel.getStyle().set("font-size", "14px");
        infoLabel.getStyle().set("color", "#6B7280");
        infoLabel.getStyle().set("margin-bottom", "16px");
        
        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        formLayout.setResponsiveSteps(
            new FormLayout.ResponsiveStep("0", 1),
            new FormLayout.ResponsiveStep("600px", 2)
        );
        
        // Buscar TODAS as consultas agendadas para garantir que apareçam
        List<ConsultaResumo> consultas = List.of();
        try {
            // Buscar todas as consultas agendadas (mais simples e garantido)
            consultas = consultaServicoAplicacao.pesquisarResumos();
            if (consultas == null) {
                consultas = List.of();
            }
        } catch (Exception e) {
            e.printStackTrace();
            consultas = List.of();
        }
        
        consultaSelect = new Select<>();
        consultaSelect.setLabel("Consulta");
        consultaSelect.setItems(consultas);
        consultaSelect.setItemLabelGenerator(consulta -> {
            StringBuilder label = new StringBuilder();
            if (consulta.getPacienteNome() != null) {
                label.append(consulta.getPacienteNome());
            }
            if (consulta.getDataConsulta() != null) {
                if (label.length() > 0) label.append(" - ");
                label.append(consulta.getDataConsulta());
            }
            if (consulta.getHoraConsulta() != null) {
                if (label.length() > 0) label.append(" ");
                label.append(consulta.getHoraConsulta());
            }
            return label.length() > 0 ? label.toString() : "Consulta #" + consulta.getId();
        });
        consultaSelect.setWidthFull();
        consultaSelect.setRequiredIndicatorVisible(true);
        
        anamneseField = new TextArea("Anamnese");
        anamneseField.setWidthFull();
        anamneseField.setHeight("100px");
        anamneseField.setRequired(true);
        anamneseField.setHelperText("Descreva os sintomas e histórico relatados pelo paciente");
        
        diagnosticoField = new TextArea("Diagnóstico");
        diagnosticoField.setWidthFull();
        diagnosticoField.setHeight("100px");
        diagnosticoField.setHelperText("Informe o diagnóstico da consulta");
        
        prescricaoField = new TextArea("Prescrição");
        prescricaoField.setWidthFull();
        prescricaoField.setHeight("100px");
        prescricaoField.setHelperText("Informe os medicamentos e tratamentos prescritos");
        
        observacoesField = new TextArea("Observações");
        observacoesField.setWidthFull();
        observacoesField.setHeight("100px");
        observacoesField.setHelperText("Observações adicionais sobre o atendimento");
        
        formLayout.add(consultaSelect, 2);
        formLayout.add(anamneseField, 2);
        formLayout.add(diagnosticoField, 2);
        formLayout.add(prescricaoField, 2);
        formLayout.add(observacoesField, 2);
        
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
        
        Button confirmarButton = new Button("Registrar Prontuário");
        confirmarButton.getStyle().set("background", "#3B82F6");
        confirmarButton.getStyle().set("color", "white");
        confirmarButton.getStyle().set("border", "none");
        confirmarButton.getStyle().set("border-radius", "8px");
        confirmarButton.getStyle().set("padding", "12px 24px");
        confirmarButton.getStyle().set("font-weight", "600");
        confirmarButton.addClickListener(e -> confirmarRegistro());
        
        botoesLayout.add(cancelarButton, confirmarButton);
        
        layout.add(infoLabel, formLayout, botoesLayout);
        add(layout);
    }
    
    private void confirmarRegistro() {
        if (validarCampos()) {
            try {
                ConsultaResumo consulta = consultaSelect.getValue();
                String anamnese = anamneseField.getValue();
                String diagnostico = diagnosticoField.getValue();
                String prescricao = prescricaoField.getValue();
                String observacoes = observacoesField.getValue();
                
                registrarProntuario.executar(
                    consulta.getId(),
                    medicoId,
                    anamnese != null ? anamnese : "",
                    diagnostico != null ? diagnostico : "",
                    prescricao != null ? prescricao : "",
                    observacoes != null ? observacoes : ""
                );
                
                Notification.show("Prontuário registrado com sucesso!", 3000, Notification.Position.TOP_CENTER);
                close();
                onRegistrado.run();
                getUI().ifPresent(ui -> {
                    ui.getPage().executeJs("setTimeout(() => window.location.reload(), 300);");
                });
            } catch (Exception e) {
                e.printStackTrace();
                Notification.show("Erro ao registrar prontuário: " + e.getMessage(), 5000, Notification.Position.TOP_CENTER);
            }
        }
    }
    
    private boolean validarCampos() {
        boolean valido = true;
        
        if (consultaSelect.getValue() == null) {
            consultaSelect.setInvalid(true);
            consultaSelect.setErrorMessage("Consulta é obrigatória");
            valido = false;
        }
        
        if (anamneseField.getValue() == null || anamneseField.getValue().trim().isEmpty()) {
            anamneseField.setInvalid(true);
            anamneseField.setErrorMessage("Anamnese é obrigatória");
            valido = false;
        }
        
        return valido;
    }
    
}

