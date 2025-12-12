package dev.beckerhealth.apresentacao.vaadin.paciente;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import dev.beckerhealth.apresentacao.vaadin.SelecaoPerfilView;

public class HeaderPaciente extends HorizontalLayout {
    
    public HeaderPaciente() {
        setWidthFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.BETWEEN);
        getStyle().set("padding", "16px 32px");
        getStyle().set("background", "white");
        getStyle().set("box-shadow", "0 2px 4px rgba(0,0,0,0.1)");
        
        add(criarLogo(), criarInfoUsuario());
    }
    
    private HorizontalLayout criarLogo() {
        HorizontalLayout logoLayout = new HorizontalLayout();
        logoLayout.setAlignItems(Alignment.CENTER);
        logoLayout.setSpacing(false);
        
        Image logo = new Image("/images/logo-becker-health.jpeg", "Becker Health");
        logo.setWidth("140px");
        logo.setHeight("auto");
        logo.getStyle().set("object-fit", "contain");
        
        logoLayout.add(logo);
        return logoLayout;
    }
    
    private HorizontalLayout criarInfoUsuario() {
        HorizontalLayout infoLayout = new HorizontalLayout();
        infoLayout.setAlignItems(Alignment.CENTER);
        infoLayout.setSpacing(true);
        
        Div notificacao = criarNotificacao();
        VerticalLayout userInfo = criarUserInfo();
        Div avatar = criarAvatar();
        Button sair = criarBotaoSair();
        
        infoLayout.add(notificacao, userInfo, avatar, sair);
        return infoLayout;
    }
    
    private Div criarAvatar() {
        Div avatar = new Div();
        avatar.getStyle().set("width", "40px");
        avatar.getStyle().set("height", "40px");
        avatar.getStyle().set("background", "#3B82F6");
        avatar.getStyle().set("border-radius", "50%");
        avatar.getStyle().set("display", "flex");
        avatar.getStyle().set("align-items", "center");
        avatar.getStyle().set("justify-content", "center");
        avatar.getStyle().set("color", "white");
        avatar.getStyle().set("font-weight", "600");
        avatar.getStyle().set("font-size", "14px");
        avatar.setText("MS");
        return avatar;
    }
    
    private Div criarNotificacao() {
        Div container = new Div();
        container.getStyle().set("position", "relative");
        container.getStyle().set("cursor", "pointer");
        
        com.vaadin.flow.dom.Element svg = new com.vaadin.flow.dom.Element("svg");
        svg.setAttribute("width", "24");
        svg.setAttribute("height", "24");
        svg.setAttribute("viewBox", "0 0 24 24");
        svg.setAttribute("fill", "none");
        svg.getStyle().set("stroke", "#6B7280");
        svg.getStyle().set("stroke-width", "2");
        svg.getStyle().set("stroke-linecap", "round");
        svg.getStyle().set("stroke-linejoin", "round");
        
        com.vaadin.flow.dom.Element path1 = new com.vaadin.flow.dom.Element("path");
        path1.setAttribute("d", "M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9");
        svg.appendChild(path1);
        
        com.vaadin.flow.dom.Element path2 = new com.vaadin.flow.dom.Element("path");
        path2.setAttribute("d", "M13.73 21a2 2 0 0 1-3.46 0");
        svg.appendChild(path2);
        
        container.getElement().appendChild(svg);
        
        Div badge = new Div();
        badge.getStyle().set("position", "absolute");
        badge.getStyle().set("top", "-4px");
        badge.getStyle().set("right", "-4px");
        badge.getStyle().set("width", "18px");
        badge.getStyle().set("height", "18px");
        badge.getStyle().set("background", "#EF4444");
        badge.getStyle().set("border-radius", "50%");
        badge.getStyle().set("display", "flex");
        badge.getStyle().set("align-items", "center");
        badge.getStyle().set("justify-content", "center");
        badge.getStyle().set("color", "white");
        badge.getStyle().set("font-size", "10px");
        badge.getStyle().set("font-weight", "600");
        badge.setText("3");
        
        container.add(badge);
        return container;
    }
    
    private VerticalLayout criarUserInfo() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(false);
        layout.setPadding(false);
        layout.getStyle().set("text-align", "right");
        
        Span nome = new Span("Maria Silva");
        nome.getStyle().set("font-size", "14px");
        nome.getStyle().set("font-weight", "600");
        nome.getStyle().set("color", "#111827");
        
        Span perfil = new Span("Paciente");
        perfil.getStyle().set("font-size", "12px");
        perfil.getStyle().set("color", "#6B7280");
        
        layout.add(nome, perfil);
        return layout;
    }
    
    private Button criarBotaoSair() {
        Button sair = new Button();
        sair.setText("Sair");
        sair.getStyle().set("background", "transparent");
        sair.getStyle().set("border", "1px solid #E5E7EB");
        sair.getStyle().set("border-radius", "6px");
        sair.getStyle().set("padding", "8px 16px");
        sair.getStyle().set("color", "#6B7280");
        sair.getStyle().set("font-size", "14px");
        sair.getStyle().set("cursor", "pointer");
        sair.getStyle().set("display", "flex");
        sair.getStyle().set("align-items", "center");
        sair.getStyle().set("gap", "8px");
        
        com.vaadin.flow.dom.Element svg = new com.vaadin.flow.dom.Element("svg");
        svg.setAttribute("width", "16");
        svg.setAttribute("height", "16");
        svg.setAttribute("viewBox", "0 0 24 24");
        svg.setAttribute("fill", "none");
        svg.getStyle().set("stroke", "currentColor");
        svg.getStyle().set("stroke-width", "2");
        svg.getStyle().set("stroke-linecap", "round");
        svg.getStyle().set("stroke-linejoin", "round");
        svg.getStyle().set("margin-left", "8px");
        
        com.vaadin.flow.dom.Element path = new com.vaadin.flow.dom.Element("path");
        path.setAttribute("d", "M9 18l6-6-6-6");
        svg.appendChild(path);
        
        sair.getElement().appendChild(svg);
        sair.addClickListener(e -> sair.getUI().ifPresent(ui -> ui.navigate(SelecaoPerfilView.class)));
        
        return sair;
    }
}

