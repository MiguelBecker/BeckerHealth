package dev.beckerhealth.apresentacao.vaadin.paciente;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ResumoCard extends VerticalLayout {
    
    public ResumoCard(String titulo, String valor, String descricao, String cor, String tipoIcone) {
        addClassNames("resumo-card");
        setPadding(false);
        setSpacing(false);
        setWidth("100%");
        getStyle().set("background", "white");
        getStyle().set("border-radius", "12px");
        getStyle().set("padding", "20px");
        getStyle().set("box-shadow", "0 1px 3px rgba(0,0,0,0.1)");
        getStyle().set("position", "relative");
        
        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.setWidthFull();
        headerLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        headerLayout.setAlignItems(FlexComponent.Alignment.START);
        headerLayout.setPadding(false);
        headerLayout.setSpacing(false);
        
        H3 tituloH3 = new H3(titulo);
        tituloH3.getStyle().set("margin", "0");
        tituloH3.getStyle().set("font-size", "14px");
        tituloH3.getStyle().set("font-weight", "500");
        tituloH3.getStyle().set("color", "#111827");
        
        Div iconContainer = criarIcone(cor, tipoIcone);
        iconContainer.getStyle().set("position", "absolute");
        iconContainer.getStyle().set("top", "20px");
        iconContainer.getStyle().set("right", "20px");
        
        String[] partes = valor.split(" ", 2);
        String numero = partes[0];
        String textoDescricao = partes.length > 1 ? partes[1] : descricao;
        
        Span numeroSpan = new Span(numero);
        numeroSpan.getStyle().set("font-size", "32px");
        numeroSpan.getStyle().set("font-weight", "700");
        numeroSpan.getStyle().set("color", "#111827");
        numeroSpan.getStyle().set("line-height", "1");
        numeroSpan.getStyle().set("margin", "16px 0 8px");
        
        Span descricaoSpan = new Span(textoDescricao);
        descricaoSpan.getStyle().set("font-size", "14px");
        descricaoSpan.getStyle().set("color", "#6B7280");
        descricaoSpan.getStyle().set("margin", "0");
        
        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setPadding(false);
        contentLayout.setSpacing(false);
        contentLayout.add(tituloH3, numeroSpan, descricaoSpan);
        
        add(contentLayout, iconContainer);
        setAlignItems(FlexComponent.Alignment.START);
    }
    
    private Div criarIcone(String cor, String tipoIcone) {
        Div icon = new Div();
        icon.getStyle().set("width", "40px");
        icon.getStyle().set("height", "40px");
        icon.getStyle().set("background", getCorFundo(tipoIcone));
        icon.getStyle().set("border-radius", "8px");
        icon.getStyle().set("display", "flex");
        icon.getStyle().set("align-items", "center");
        icon.getStyle().set("justify-content", "center");
        
        String svgContent = criarSvgContent(cor, tipoIcone);
        icon.getElement().setProperty("innerHTML", svgContent);
        
        return icon;
    }
    
    private String criarSvgContent(String cor, String tipoIcone) {
        String pathData = getSvgPath(tipoIcone);
        if (pathData.isEmpty()) return "";
        
        return "<svg width=\"20\" height=\"20\" viewBox=\"0 0 24 24\" fill=\"none\" " +
                "style=\"stroke: " + cor + "; stroke-width: 2; stroke-linecap: round; stroke-linejoin: round;\">" +
                "<path d=\"" + pathData + "\"></path>" +
                "</svg>";
    }
    
    private String getCorFundo(String tipoIcone) {
        return switch (tipoIcone) {
            case "calendar" -> "#EFF6FF";
            case "exclamation" -> "#FEF3C7";
            case "document" -> "#D1FAE5";
            case "bell" -> "#F3E8FF";
            default -> "#F3F4F6";
        };
    }
    
    private String getSvgPath(String tipoIcone) {
        return switch (tipoIcone) {
            case "calendar" -> "M8 2v4M16 2v4M3 10h18M5 4h14a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V6a2 2 0 0 1 2-2z";
            case "exclamation" -> "M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z";
            case "document" -> "M9 12h6m-6 4h6m2 5H7a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h5.586a1 1 0 0 1 .707.293l5.414 5.414a1 1 0 0 1 .293.707V19a2 2 0 0 1-2 2z";
            case "bell" -> "M15 17h5l-1.405-1.405A2.032 2.032 0 0 1 18 14.158V11a6.002 6.002 0 0 0-4-5.659V5a2 2 0 1 0-4 0v.341C7.67 6.165 6 8.388 6 11v3.159c0 .538-.214 1.055-.595 1.436L4 17h5m6 0v1a3 3 0 1 1-6 0v-1m6 0H9";
            default -> "";
        };
    }
}

