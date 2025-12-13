package dev.beckerhealth.apresentacao.vaadin.admin;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import dev.beckerhealth.apresentacao.vaadin.SelecaoPerfilView;

public class HeaderAdmin extends HorizontalLayout {
    
    public HeaderAdmin() {
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
        logo.setWidth("120px");
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
        Div sair = criarBotaoSair();
        
        infoLayout.add(notificacao, userInfo, avatar, sair);
        return infoLayout;
    }
    
    private Div criarAvatar() {
        Div avatar = new Div();
        avatar.getStyle().set("width", "40px");
        avatar.getStyle().set("height", "40px");
        avatar.getStyle().set("background", "linear-gradient(135deg, #8B5CF6 0%, #7C3AED 100%)");
        avatar.getStyle().set("border-radius", "50%");
        avatar.getStyle().set("display", "flex");
        avatar.getStyle().set("align-items", "center");
        avatar.getStyle().set("justify-content", "center");
        avatar.getStyle().set("color", "white");
        avatar.getStyle().set("font-weight", "600");
        avatar.getStyle().set("font-size", "14px");
        avatar.getStyle().set("flex-shrink", "0");
        avatar.setText("AS");
        return avatar;
    }
    
    private Div criarNotificacao() {
        Div container = new Div();
        container.getStyle().set("position", "relative");
        container.getStyle().set("cursor", "pointer");
        container.getStyle().set("width", "24px");
        container.getStyle().set("height", "24px");
        
        String svgContent = "<svg width=\"24\" height=\"24\" viewBox=\"0 0 24 24\" fill=\"none\" " +
                "style=\"stroke: #6B7280; stroke-width: 2; stroke-linecap: round; stroke-linejoin: round;\">" +
                "<path d=\"M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9\"></path>" +
                "<path d=\"M13.73 21a2 2 0 0 1-3.46 0\"></path>" +
                "</svg>";
        
        container.getElement().setProperty("innerHTML", svgContent);
        
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
        
        Span nome = new Span("Admin Sistema");
        nome.getStyle().set("font-size", "14px");
        nome.getStyle().set("font-weight", "600");
        nome.getStyle().set("color", "#111827");
        
        Span perfil = new Span("Administrador");
        perfil.getStyle().set("font-size", "12px");
        perfil.getStyle().set("color", "#6B7280");
        
        layout.add(nome, perfil);
        return layout;
    }
    
    private Div criarBotaoSair() {
        Div buttonContainer = new Div();
        buttonContainer.getStyle().set("background", "transparent");
        buttonContainer.getStyle().set("border", "1px solid #E5E7EB");
        buttonContainer.getStyle().set("border-radius", "6px");
        buttonContainer.getStyle().set("padding", "8px 16px");
        buttonContainer.getStyle().set("color", "#6B7280");
        buttonContainer.getStyle().set("font-size", "14px");
        buttonContainer.getStyle().set("cursor", "pointer");
        buttonContainer.getStyle().set("display", "flex");
        buttonContainer.getStyle().set("align-items", "center");
        buttonContainer.getStyle().set("gap", "8px");
        buttonContainer.getStyle().set("user-select", "none");
        
        Span texto = new Span("Sair");
        texto.getStyle().set("font-size", "14px");
        texto.getStyle().set("color", "#6B7280");
        
        String svgContent = "<svg width=\"16\" height=\"16\" viewBox=\"0 0 24 24\" fill=\"none\" " +
                "style=\"stroke: currentColor; stroke-width: 2; stroke-linecap: round; stroke-linejoin: round;\">" +
                "<path d=\"M9 18l6-6-6-6\"></path>" +
                "</svg>";
        
        Div svgDiv = new Div();
        svgDiv.getElement().setProperty("innerHTML", svgContent);
        svgDiv.getStyle().set("display", "inline-block");
        svgDiv.getStyle().set("color", "#6B7280");
        
        buttonContainer.add(texto, svgDiv);
        buttonContainer.addClickListener(e -> 
            buttonContainer.getUI().ifPresent(ui -> ui.navigate(SelecaoPerfilView.class))
        );
        
        return buttonContainer;
    }
}


