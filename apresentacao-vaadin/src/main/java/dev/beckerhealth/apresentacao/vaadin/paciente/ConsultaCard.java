package dev.beckerhealth.apresentacao.vaadin.paciente;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import dev.beckerhealth.aplicacao.consultas.dto.ConsultaResumo;

import java.time.format.DateTimeFormatter;

public class ConsultaCard extends VerticalLayout {
    
    public ConsultaCard(ConsultaResumo consulta) {
        addClassNames("consulta-card");
        setPadding(false);
        setSpacing(false);
        getStyle().set("background", "white");
        getStyle().set("border-radius", "12px");
        getStyle().set("padding", "20px");
        getStyle().set("box-shadow", "0 1px 3px rgba(0,0,0,0.1)");
        getStyle().set("margin-bottom", "16px");
        
        H3 medicoNome = new H3(consulta.getMedicoNome());
        medicoNome.getStyle().set("margin", "0 0 12px");
        medicoNome.getStyle().set("font-size", "18px");
        medicoNome.getStyle().set("font-weight", "600");
        medicoNome.getStyle().set("color", "#111827");
        
        HorizontalLayout tagsLayout = new HorizontalLayout();
        tagsLayout.setSpacing(true);
        tagsLayout.setPadding(false);
        
        Span especialidade = criarTag(consulta.getMedicoEspecialidade() != null ? consulta.getMedicoEspecialidade() : "Geral", "#6B7280", "#F3F4F6");
        Span tipo = criarTag(consulta.getTipo(), getCorTipo(consulta.getTipo()), getCorFundoTipo(consulta.getTipo()));
        tagsLayout.add(especialidade, tipo);
        
        HorizontalLayout infoLayout = new HorizontalLayout();
        infoLayout.setSpacing(true);
        infoLayout.setPadding(false);
        infoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        
        Div dataInfo = criarInfoComIcone("calendar", formatarData(consulta.getDataConsulta()));
        Div horaInfo = criarInfoComIcone("clock", formatarHora(consulta.getHoraConsulta()));
        infoLayout.add(dataInfo, horaInfo);
        
        Span convenio = new Span("Convênio: Particular");
        convenio.getStyle().set("font-size", "14px");
        convenio.getStyle().set("color", "#6B7280");
        convenio.getStyle().set("margin-top", "12px");
        
        HorizontalLayout botoesLayout = new HorizontalLayout();
        botoesLayout.setSpacing(true);
        botoesLayout.setPadding(false);
        botoesLayout.getStyle().set("margin-top", "16px");
        
        Button reagendar = criarBotaoSecundario("Reagendar");
        Button cancelar = criarBotaoCancelar("Cancelar");
        botoesLayout.add(reagendar, cancelar);
        
        add(medicoNome, tagsLayout, infoLayout, convenio, botoesLayout);
    }
    
    private Span criarTag(String texto, String corTexto, String corFundo) {
        Span tag = new Span(texto);
        tag.getStyle().set("font-size", "12px");
        tag.getStyle().set("padding", "4px 12px");
        tag.getStyle().set("border-radius", "16px");
        tag.getStyle().set("background", corFundo);
        tag.getStyle().set("color", corTexto);
        tag.getStyle().set("font-weight", "500");
        return tag;
    }
    
    private Div criarInfoComIcone(String tipo, String texto) {
        Div container = new Div();
        container.getStyle().set("display", "flex");
        container.getStyle().set("align-items", "center");
        container.getStyle().set("gap", "8px");
        
        com.vaadin.flow.dom.Element svg = new com.vaadin.flow.dom.Element("svg");
        svg.setAttribute("width", "16");
        svg.setAttribute("height", "16");
        svg.setAttribute("viewBox", "0 0 24 24");
        svg.setAttribute("fill", "none");
        svg.getStyle().set("stroke", "#6B7280");
        svg.getStyle().set("stroke-width", "2");
        svg.getStyle().set("stroke-linecap", "round");
        svg.getStyle().set("stroke-linejoin", "round");
        
        String pathData = tipo.equals("calendar") 
            ? "M8 2v4M16 2v4M3 10h18M5 4h14a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V6a2 2 0 0 1 2-2z"
            : "M12 8v4l3 3";
        
        com.vaadin.flow.dom.Element path = new com.vaadin.flow.dom.Element("path");
        path.setAttribute("d", pathData);
        svg.appendChild(path);
        container.getElement().appendChild(svg);
        
        Span span = new Span(texto);
        span.getStyle().set("font-size", "14px");
        span.getStyle().set("color", "#374151");
        container.add(span);
        
        return container;
    }
    
    private Button criarBotaoSecundario(String texto) {
        Button btn = new Button(texto);
        btn.getStyle().set("background", "white");
        btn.getStyle().set("border", "1px solid #E5E7EB");
        btn.getStyle().set("border-radius", "6px");
        btn.getStyle().set("padding", "8px 16px");
        btn.getStyle().set("color", "#374151");
        btn.getStyle().set("font-size", "14px");
        btn.getStyle().set("cursor", "pointer");
        return btn;
    }
    
    private Button criarBotaoCancelar(String texto) {
        Button btn = new Button(texto);
        btn.getStyle().set("background", "white");
        btn.getStyle().set("border", "1px solid #EF4444");
        btn.getStyle().set("border-radius", "6px");
        btn.getStyle().set("padding", "8px 16px");
        btn.getStyle().set("color", "#EF4444");
        btn.getStyle().set("font-size", "14px");
        btn.getStyle().set("cursor", "pointer");
        return btn;
    }
    
    private String getCorTipo(String tipo) {
        return switch (tipo != null ? tipo.toUpperCase() : "") {
            case "ROTINA" -> "#10B981";
            case "RETORNO" -> "#3B82F6";
            case "URGENCIA" -> "#EF4444";
            default -> "#6B7280";
        };
    }
    
    private String getCorFundoTipo(String tipo) {
        return switch (tipo != null ? tipo.toUpperCase() : "") {
            case "ROTINA" -> "#D1FAE5";
            case "RETORNO" -> "#DBEAFE";
            case "URGENCIA" -> "#FEE2E2";
            default -> "#F3F4F6";
        };
    }
    
    private String formatarData(java.time.LocalDate data) {
        if (data == null) return "";
        return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
    
    private String formatarHora(java.time.LocalTime hora) {
        if (hora == null) return "";
        return hora.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}

