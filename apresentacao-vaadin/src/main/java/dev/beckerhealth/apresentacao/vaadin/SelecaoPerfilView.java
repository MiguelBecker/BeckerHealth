package dev.beckerhealth.apresentacao.vaadin;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.dependency.CssImport;
import dev.beckerhealth.apresentacao.vaadin.perfil.PerfilCard;
import dev.beckerhealth.apresentacao.vaadin.perfil.PerfilUsuario;

@PageTitle("BeckerHealth - Seleção de Perfil")
@Route("")
public class SelecaoPerfilView extends VerticalLayout {

    public SelecaoPerfilView() {
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.CENTER);
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        getStyle().setBackground("white");
        getStyle().setPadding("40px");
        
        add(criarLogoContainer(), criarCardsLayout());
    }
    
    private Div criarLogoContainer() {
        Div container = new Div();
        container.addClassNames("logo-container");
        container.getStyle().set("display", "flex");
        container.getStyle().set("flex-direction", "column");
        container.getStyle().set("align-items", "center");
        container.getStyle().set("width", "100%");
        
        Image logo = new Image("/images/logo-becker-health.jpeg", "Becker Health");
        logo.setWidth("180px");
        logo.setHeight("auto");
        logo.getStyle().set("object-fit", "contain");
        logo.getStyle().set("margin-bottom", "24px");
        logo.getStyle().set("display", "block");
        logo.getStyle().set("margin-left", "auto");
        logo.getStyle().set("margin-right", "auto");
        
        Paragraph subtitulo1 = new Paragraph("Sistema de Gestão de Saúde em Clínicas e Consultórios");
        subtitulo1.getStyle().set("color", "#374151");
        subtitulo1.getStyle().set("font-size", "16px");
        subtitulo1.getStyle().set("margin", "0 0 8px");
        subtitulo1.getStyle().set("text-align", "center");
        
        Paragraph subtitulo2 = new Paragraph("Selecione seu perfil para acessar o sistema");
        subtitulo2.getStyle().set("color", "#6B7280");
        subtitulo2.getStyle().set("font-size", "14px");
        subtitulo2.getStyle().set("margin", "0");
        subtitulo2.getStyle().set("text-align", "center");
        
        container.add(logo, subtitulo1, subtitulo2);
        return container;
    }
    
    private HorizontalLayout criarCardsLayout() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.addClassNames("cards-layout");
        layout.setSpacing(true);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        
        for (PerfilUsuario perfil : PerfilUsuario.values()) {
            layout.add(new PerfilCard(perfil, () -> navegarParaPerfil(perfil)));
        }
        return layout;
    }
    
    private void navegarParaPerfil(PerfilUsuario perfil) {
        getUI().ifPresent(ui -> {
            switch (perfil) {
                case PACIENTE:
                    ui.navigate("paciente/dashboard");
                    break;
                case MEDICO:
                    ui.navigate("medico/dashboard");
                    break;
                case ADMINISTRADOR:
                    ui.navigate("admin/dashboard");
                    break;
                default:
                    ui.navigate(MainView.class);
                    break;
            }
        });
    }
}

