package dev.beckerhealth.apresentacao.vaadin.medico;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import dev.beckerhealth.apresentacao.vaadin.SelecaoPerfilView;
import dev.beckerhealth.aplicacao.notificacao.NotificacaoServicoAplicacao;

public class HeaderMedico extends HorizontalLayout {
    private final NotificacaoServicoAplicacao notificacaoServico;
    private NotificacaoComponent notificacaoComponent;
    
    public HeaderMedico(NotificacaoServicoAplicacao notificacaoServico) {
        this.notificacaoServico = notificacaoServico;
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
        
        // ID 2 = Dr. João Silva (médico), ID 4 = Carlos Oliveira (paciente)
        // TEMPORÁRIO: Usando ID 4 (paciente) para testar, já que notificações são criadas para pacientes
        // TODO: Quando houver notificações para médicos, usar ID do médico logado
        notificacaoComponent = new NotificacaoComponent(notificacaoServico, 4L); // ID do paciente para teste
        VerticalLayout userInfo = criarUserInfo();
        Div avatar = criarAvatar();
        Div sair = criarBotaoSair();
        
        infoLayout.add(notificacaoComponent, userInfo, avatar, sair);
        return infoLayout;
    }
    
    private Div criarAvatar() {
        Div avatar = new Div();
        avatar.getStyle().set("width", "40px");
        avatar.getStyle().set("height", "40px");
        avatar.getStyle().set("background", "linear-gradient(135deg, #60A5FA 0%, #3B82F6 100%)");
        avatar.getStyle().set("border-radius", "50%");
        avatar.getStyle().set("display", "flex");
        avatar.getStyle().set("align-items", "center");
        avatar.getStyle().set("justify-content", "center");
        avatar.getStyle().set("color", "white");
        avatar.getStyle().set("font-weight", "600");
        avatar.getStyle().set("font-size", "14px");
        avatar.getStyle().set("flex-shrink", "0");
        avatar.setText("JS");
        return avatar;
    }
    
    public void atualizarNotificacoes() {
        if (notificacaoComponent != null) {
            notificacaoComponent.atualizar();
        }
    }
    
    private VerticalLayout criarUserInfo() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(false);
        layout.setPadding(false);
        layout.getStyle().set("text-align", "right");
        
        Span nome = new Span("Dr. João Santos");
        nome.getStyle().set("font-size", "14px");
        nome.getStyle().set("font-weight", "600");
        nome.getStyle().set("color", "#111827");
        
        Span perfil = new Span("Médico - Cardiologia");
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

