package dev.beckerhealth.apresentacao.vaadin.perfil;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class PerfilCard extends VerticalLayout {
    
    public PerfilCard(PerfilUsuario perfil, Runnable onAcessar) {
        addClassNames("perfil-card");
        setWidth("320px");
        setPadding(false);
        setSpacing(false);
        
        Div iconContainer = criarIcone(perfil);
        H2 titulo = new H2(perfil.getTitulo());
        H3 subtitulo = new H3(perfil.getSubtitulo());
        UnorderedList lista = criarListaFuncionalidades(perfil);
        Button botao = criarBotao(perfil, onAcessar);
        
        add(iconContainer, titulo, subtitulo, lista, botao);
        setAlignItems(FlexComponent.Alignment.CENTER);
    }
    
    private Div criarIcone(PerfilUsuario perfil) {
        Div icon = new Div();
        icon.addClassNames("perfil-icon");
        icon.getStyle().set("background", perfil.getCor());
        icon.getStyle().set("border-radius", "50%");
        icon.getStyle().set("display", "flex");
        icon.getStyle().set("align-items", "center");
        icon.getStyle().set("justify-content", "center");
        icon.getStyle().set("width", "80px");
        icon.getStyle().set("height", "80px");
        icon.getStyle().set("flex-shrink", "0");
        
        String svgContent = criarSvgContent(perfil.getTipoIcone());
        icon.getElement().setProperty("innerHTML", svgContent);
        
        return icon;
    }
    
    private String criarSvgContent(String tipoIcone) {
        String[] paths = getSvgPaths(tipoIcone);
        if (paths.length == 0) return "";
        
        StringBuilder svg = new StringBuilder();
        svg.append("<svg width=\"48\" height=\"48\" viewBox=\"0 0 24 24\" fill=\"none\" ");
        svg.append("style=\"stroke: white; stroke-width: 2.5; stroke-linecap: round; stroke-linejoin: round;\">");
        
        for (String pathData : paths) {
            svg.append("<path d=\"").append(pathData).append("\"></path>");
        }
        
        svg.append("</svg>");
        return svg.toString();
    }
    
    private String[] getSvgPaths(String tipoIcone) {
        return switch (tipoIcone) {
            case "person" -> new String[]{
                "M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2",
                "M12 11a4 4 0 1 0 0-8 4 4 0 0 0 0 8z"
            };
            case "stethoscope" -> new String[]{
                "M9 5H7a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V7a2 2 0 0 0-2-2h-2",
                "M9 5a2 2 0 0 0 2 2h2a2 2 0 0 0 2-2",
                "M9 5a2 2 0 0 1 2-2h2a2 2 0 0 1 2 2",
                "M6 9h12",
                "M6 13h12"
            };
            case "shield" -> new String[]{
                "M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"
            };
            default -> new String[]{};
        };
    }
    
    private UnorderedList criarListaFuncionalidades(PerfilUsuario perfil) {
        UnorderedList lista = new UnorderedList();
        lista.getStyle().set("list-style", "none");
        lista.getStyle().set("padding", "0");
        lista.getStyle().set("margin", "0 0 24px");
        perfil.getFuncionalidades().forEach(func -> {
            ListItem item = new ListItem();
            item.getStyle().set("list-style", "none");
            item.getStyle().set("list-style-type", "none");
            item.getStyle().set("display", "flex");
            item.getStyle().set("align-items", "center");
            item.getStyle().set("margin-bottom", "8px");
            item.getStyle().set("font-size", "14px");
            item.getStyle().set("color", "#374151");
            
            Div bullet = new Div();
            bullet.getStyle().set("width", "6px");
            bullet.getStyle().set("height", "6px");
            bullet.getStyle().set("background", perfil.getCor());
            bullet.getStyle().set("border-radius", "50%");
            bullet.getStyle().set("margin-right", "8px");
            bullet.getStyle().set("flex-shrink", "0");
            
            Span texto = new Span(func);
            item.add(bullet, texto);
            lista.add(item);
        });
        return lista;
    }
    
    private Button criarBotao(PerfilUsuario perfil, Runnable onAcessar) {
        Button botao = new Button(perfil.getTextoBotao());
        botao.getStyle().set("background", perfil.getCor());
        botao.getStyle().set("color", "white");
        botao.getStyle().set("border", "none");
        botao.getStyle().set("border-radius", "8px");
        botao.getStyle().set("padding", "12px 24px");
        botao.getStyle().set("font-size", "16px");
        botao.getStyle().set("font-weight", "600");
        botao.getStyle().set("width", "100%");
        botao.getStyle().set("cursor", "pointer");
        botao.addClickListener(e -> onAcessar.run());
        return botao;
    }
}

