package dev.beckerhealth.apresentacao.vaadin.medico;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import dev.beckerhealth.aplicacao.consultas.dto.ConsultaResumo;

import java.time.format.DateTimeFormatter;

public class ConsultaMedicoCard extends Div {
    
    public ConsultaMedicoCard(ConsultaResumo consulta) {
        addClassNames("consulta-medico-card");
        getStyle().set("background", consulta.getStatus().equals("URGENCIA") ? "#FEF2F2" : "white");
        getStyle().set("border-radius", "12px");
        getStyle().set("padding", "20px");
        getStyle().set("margin-bottom", "16px");
        getStyle().set("box-shadow", "0 1px 3px rgba(0,0,0,0.1)");
        getStyle().set("border", "1px solid #E5E7EB");
        
        HorizontalLayout mainLayout = new HorizontalLayout();
        mainLayout.setWidthFull();
        mainLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        mainLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        mainLayout.setSpacing(true);
        
        // Informações da consulta
        HorizontalLayout infoLayout = new HorizontalLayout();
        infoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        infoLayout.setSpacing(true);
        
        // Horário
        Div horarioDiv = criarHorario(consulta.getHoraConsulta().format(DateTimeFormatter.ofPattern("HH:mm")));
        
        // Informações do paciente
        VerticalLayout pacienteInfo = new VerticalLayout();
        pacienteInfo.setPadding(false);
        pacienteInfo.setSpacing(false);
        
        Span nomePaciente = new Span(consulta.getPacienteNome());
        nomePaciente.getStyle().set("font-size", "16px");
        nomePaciente.getStyle().set("font-weight", "600");
        nomePaciente.getStyle().set("color", "#111827");
        nomePaciente.getStyle().set("margin-bottom", "4px");
        
        HorizontalLayout tagsLayout = new HorizontalLayout();
        tagsLayout.setPadding(false);
        tagsLayout.setSpacing(true);
        
        // Tag de tipo
        Div tipoTag = criarTag(consulta.getTipo(), getCorTipo(consulta.getTipo()));
        tagsLayout.add(tipoTag);
        
        // Tag de urgência se aplicável
        if (consulta.getStatus() != null && consulta.getStatus().equals("URGENCIA")) {
            Div urgenciaTag = criarTag("Urgência", "#FEE2E2", "#DC2626");
            tagsLayout.add(urgenciaTag);
        }
        
        // Convenio (placeholder - pode ser adicionado ao DTO depois)
        Span convenio = new Span("Particular");
        convenio.getStyle().set("font-size", "14px");
        convenio.getStyle().set("color", "#6B7280");
        convenio.getStyle().set("margin-top", "4px");
        
        pacienteInfo.add(nomePaciente, tagsLayout, convenio);
        
        infoLayout.add(horarioDiv, pacienteInfo);
        
        // Botões de ação
        HorizontalLayout botoesLayout = new HorizontalLayout();
        botoesLayout.setSpacing(true);
        botoesLayout.setPadding(false);
        
        Button iniciarAtendimento = new Button("Iniciar Atendimento");
        iniciarAtendimento.getStyle().set("background", "#3B82F6");
        iniciarAtendimento.getStyle().set("color", "white");
        iniciarAtendimento.getStyle().set("border", "none");
        iniciarAtendimento.getStyle().set("border-radius", "8px");
        iniciarAtendimento.getStyle().set("padding", "10px 20px");
        iniciarAtendimento.getStyle().set("font-size", "14px");
        iniciarAtendimento.getStyle().set("font-weight", "600");
        iniciarAtendimento.getStyle().set("cursor", "pointer");
        
        Button verHistorico = new Button("Ver Histórico");
        verHistorico.getStyle().set("background", "white");
        verHistorico.getStyle().set("color", "#3B82F6");
        verHistorico.getStyle().set("border", "1px solid #3B82F6");
        verHistorico.getStyle().set("border-radius", "8px");
        verHistorico.getStyle().set("padding", "10px 20px");
        verHistorico.getStyle().set("font-size", "14px");
        verHistorico.getStyle().set("font-weight", "600");
        verHistorico.getStyle().set("cursor", "pointer");
        
        botoesLayout.add(iniciarAtendimento, verHistorico);
        
        mainLayout.add(infoLayout, botoesLayout);
        add(mainLayout);
    }
    
    private Div criarHorario(String horario) {
        Div horarioDiv = new Div();
        horarioDiv.getStyle().set("display", "flex");
        horarioDiv.getStyle().set("align-items", "center");
        horarioDiv.getStyle().set("gap", "8px");
        horarioDiv.getStyle().set("min-width", "80px");
        
        String svgContent = "<svg width=\"16\" height=\"16\" viewBox=\"0 0 24 24\" fill=\"none\" " +
                "style=\"stroke: #6B7280; stroke-width: 2; stroke-linecap: round; stroke-linejoin: round;\">" +
                "<circle cx=\"12\" cy=\"12\" r=\"10\"></circle>" +
                "<polyline points=\"12 6 12 12 16 14\"></polyline>" +
                "</svg>";
        
        Div iconDiv = new Div();
        iconDiv.getElement().setProperty("innerHTML", svgContent);
        iconDiv.getStyle().set("display", "inline-block");
        
        Span horarioSpan = new Span(horario);
        horarioSpan.getStyle().set("font-size", "16px");
        horarioSpan.getStyle().set("font-weight", "600");
        horarioSpan.getStyle().set("color", "#111827");
        
        horarioDiv.add(iconDiv, horarioSpan);
        return horarioDiv;
    }
    
    private Div criarTag(String texto, String corFundo) {
        return criarTag(texto, corFundo, "#111827");
    }
    
    private Div criarTag(String texto, String corFundo, String corTexto) {
        Div tag = new Div();
        tag.getStyle().set("background", corFundo);
        tag.getStyle().set("color", corTexto);
        tag.getStyle().set("padding", "4px 12px");
        tag.getStyle().set("border-radius", "12px");
        tag.getStyle().set("font-size", "12px");
        tag.getStyle().set("font-weight", "500");
        tag.setText(texto);
        return tag;
    }
    
    private String getCorTipo(String tipo) {
        return switch (tipo != null ? tipo.toUpperCase() : "") {
            case "ROTINA" -> "#D1FAE5";
            case "RETORNO" -> "#DBEAFE";
            case "URGENCIA" -> "#FEE2E2";
            default -> "#F3F4F6";
        };
    }
}

