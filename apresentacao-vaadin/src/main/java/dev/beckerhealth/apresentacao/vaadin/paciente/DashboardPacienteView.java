package dev.beckerhealth.apresentacao.vaadin.paciente;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.dependency.CssImport;
import dev.beckerhealth.aplicacao.consultas.AgendarConsulta;
import dev.beckerhealth.aplicacao.consultas.ConsultaServicoAplicacao;
import dev.beckerhealth.aplicacao.consultas.dto.ConsultaResumo;
import dev.beckerhealth.dominio.consultas.ConsultaRepository;

import java.util.List;

@PageTitle("Dashboard - Paciente")
@Route("paciente/dashboard")
@CssImport("./styles/dashboard-paciente.css")
public class DashboardPacienteView extends VerticalLayout {
    
    private final ConsultaServicoAplicacao consultaServicoAplicacao;
    private final ConsultaRepository consultaRepository;
    private final AgendarConsulta agendarConsulta;
    private VerticalLayout conteudoLayout;
    
    public DashboardPacienteView(ConsultaServicoAplicacao consultaServicoAplicacao,
                                 ConsultaRepository consultaRepository,
                                 AgendarConsulta agendarConsulta) {
        this.consultaServicoAplicacao = consultaServicoAplicacao;
        this.consultaRepository = consultaRepository;
        this.agendarConsulta = agendarConsulta;
        setPadding(false);
        setSpacing(false);
        setSizeFull();
        getStyle().set("background", "#F9FAFB");
        
        add(criarHeader(), criarResumoCards(), criarAbas(), criarConteudo());
    }
    
    private HeaderPaciente criarHeader() {
        return new HeaderPaciente();
    }
    
    private HorizontalLayout criarResumoCards() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidthFull();
        layout.setPadding(true);
        layout.setSpacing(true);
        layout.getStyle().set("padding", "24px 32px");
        layout.getStyle().set("background", "#F9FAFB");
        
        layout.add(
            new ResumoCard("Próximas Consultas", "2", "Agendadas este mês", "#3B82F6", "calendar"),
            new ResumoCard("Exames Pendentes", "1", "Aguardando realização", "#F59E0B", "exclamation"),
            new ResumoCard("Resultados Disponíveis", "1", "Liberados pelo médico", "#10B981", "document"),
            new ResumoCard("Notificações", "3", "Não lidas", "#8B5CF6", "bell")
        );
        
        return layout;
    }
    
    private Tabs criarAbas() {
        Tabs tabs = new Tabs();
        tabs.addClassNames("dashboard-tabs");
        tabs.setWidthFull();
        tabs.getStyle().set("background", "white");
        tabs.getStyle().set("padding", "0");
        tabs.getStyle().set("border-bottom", "1px solid #E5E7EB");
        tabs.getStyle().set("box-shadow", "none");
        
        Tab minhasConsultas = new Tab("Minhas Consultas");
        Tab exames = new Tab("Exames");
        Tab prontuario = new Tab("Prontuário");
        Tab medicos = new Tab("Médicos");
        Tab historico = new Tab("Histórico");
        Tab notificacoes = new Tab("Notificações");
        
        tabs.add(minhasConsultas, exames, prontuario, medicos, historico, notificacoes);
        tabs.setSelectedTab(minhasConsultas);
        
        tabs.addSelectedChangeListener(e -> atualizarConteudo(e.getSelectedTab()));
        
        return tabs;
    }
    
    private VerticalLayout criarConteudo() {
        conteudoLayout = new VerticalLayout();
        conteudoLayout.setWidthFull();
        conteudoLayout.setPadding(true);
        conteudoLayout.getStyle().set("padding", "24px 32px");
        conteudoLayout.getStyle().set("max-width", "1200px");
        conteudoLayout.getStyle().set("margin", "0 auto");
        
        atualizarConteudoConsultas();
        return conteudoLayout;
    }
    
    private void atualizarConteudo(Tab tab) {
        conteudoLayout.removeAll();
        
        if (tab.getLabel().equals("Minhas Consultas")) {
            atualizarConteudoConsultas();
        } else {
            Div placeholder = new Div();
            placeholder.setText("Conteúdo de " + tab.getLabel() + " em desenvolvimento");
            placeholder.getStyle().set("padding", "40px");
            placeholder.getStyle().set("text-align", "center");
            placeholder.getStyle().set("color", "#6B7280");
            conteudoLayout.add(placeholder);
        }
    }
    
    private void atualizarConteudoConsultas() {
        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.setWidthFull();
        headerLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        headerLayout.setAlignItems(Alignment.CENTER);
        headerLayout.getStyle().set("margin-bottom", "24px");
        
        H2 titulo = new H2("Próximas Consultas");
        titulo.getStyle().set("margin", "0");
        titulo.getStyle().set("font-size", "24px");
        titulo.getStyle().set("font-weight", "600");
        titulo.getStyle().set("color", "#111827");
        
        Div agendar = new Div();
        agendar.getStyle().set("background", "#3B82F6");
        agendar.getStyle().set("color", "white");
        agendar.getStyle().set("border", "none");
        agendar.getStyle().set("border-radius", "8px");
        agendar.getStyle().set("padding", "10px 20px");
        agendar.getStyle().set("font-size", "14px");
        agendar.getStyle().set("font-weight", "600");
        agendar.getStyle().set("cursor", "pointer");
        agendar.getStyle().set("display", "inline-flex");
        agendar.getStyle().set("align-items", "center");
        agendar.getStyle().set("gap", "8px");
        agendar.getStyle().set("user-select", "none");
        
        // Criar ícone SVG
        Div iconDiv = new Div();
        String svgContent = "<svg width=\"16\" height=\"16\" viewBox=\"0 0 24 24\" fill=\"none\" " +
                "style=\"stroke: white; stroke-width: 2.5; stroke-linecap: round; stroke-linejoin: round;\">" +
                "<path d=\"M12 5v14m7-7H5\"></path>" +
                "</svg>";
        iconDiv.getElement().setProperty("innerHTML", svgContent);
        iconDiv.getStyle().set("display", "inline-block");
        
        // Criar texto
        Span texto = new Span("Agendar Consulta");
        texto.getStyle().set("color", "white");
        
        agendar.add(iconDiv, texto);
        
        agendar.addClickListener(e -> {
            getUI().ifPresent(ui -> {
                AgendarConsultaDialog dialog = new AgendarConsultaDialog(
                    agendarConsulta,
                    4L, // ID do paciente do teste (Carlos Oliveira)
                    "Carlos Oliveira" // Nome do paciente do teste
                );
                dialog.open();
            });
        });
        
        headerLayout.add(titulo, agendar);
        
        VerticalLayout consultasLayout = new VerticalLayout();
        consultasLayout.setPadding(false);
        consultasLayout.setSpacing(false);
        consultasLayout.setWidthFull();
        
        List<ConsultaResumo> consultas = consultaServicoAplicacao.pesquisarResumos();
        if (consultas.isEmpty()) {
            Div empty = new Div();
            empty.setText("Nenhuma consulta agendada");
            empty.getStyle().set("padding", "40px");
            empty.getStyle().set("text-align", "center");
            empty.getStyle().set("color", "#6B7280");
            consultasLayout.add(empty);
        } else {
            consultas.forEach(consulta -> {
                consultasLayout.add(new ConsultaCard(
                    consulta,
                    consultaRepository,
                    () -> atualizarConteudoConsultas()
                ));
            });
        }
        
        conteudoLayout.add(headerLayout, consultasLayout);
    }
}

