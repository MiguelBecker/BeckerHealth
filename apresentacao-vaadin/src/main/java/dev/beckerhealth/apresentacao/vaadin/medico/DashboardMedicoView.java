package dev.beckerhealth.apresentacao.vaadin.medico;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.dependency.CssImport;
import dev.beckerhealth.aplicacao.consultas.ConsultaServicoAplicacao;
import dev.beckerhealth.aplicacao.consultas.dto.ConsultaResumo;
import dev.beckerhealth.aplicacao.notificacao.NotificacaoServicoAplicacao;
import dev.beckerhealth.aplicacao.prontuario.ProntuarioServicoAplicacao;
import dev.beckerhealth.aplicacao.prontuario.RegistrarProntuario;
import dev.beckerhealth.aplicacao.prontuario.dto.ProntuarioResumo;
import dev.beckerhealth.dominio.consultas.ConsultaRepository;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@PageTitle("Dashboard - Médico")
@Route("medico/dashboard")
@CssImport("./styles/dashboard-medico.css")
public class DashboardMedicoView extends VerticalLayout {
    
    private final ConsultaServicoAplicacao consultaServicoAplicacao;
    private final ConsultaRepository consultaRepository;
    private final ProntuarioServicoAplicacao prontuarioServicoAplicacao;
    private final RegistrarProntuario registrarProntuario;
    private final NotificacaoServicoAplicacao notificacaoServicoAplicacao;
    private HeaderMedico headerMedico;
    private VerticalLayout conteudoLayout;
    
    public DashboardMedicoView(ConsultaServicoAplicacao consultaServicoAplicacao,
                              ConsultaRepository consultaRepository,
                              ProntuarioServicoAplicacao prontuarioServicoAplicacao,
                              RegistrarProntuario registrarProntuario,
                              NotificacaoServicoAplicacao notificacaoServicoAplicacao) {
        this.consultaServicoAplicacao = consultaServicoAplicacao;
        this.consultaRepository = consultaRepository;
        this.prontuarioServicoAplicacao = prontuarioServicoAplicacao;
        this.registrarProntuario = registrarProntuario;
        this.notificacaoServicoAplicacao = notificacaoServicoAplicacao;
        setPadding(false);
        setSpacing(false);
        setSizeFull();
        getStyle().set("background", "#F9FAFB");
        
        add(criarHeader(), criarResumoCards(), criarAbas(), criarConteudo());
    }
    
    private HeaderMedico criarHeader() {
        headerMedico = new HeaderMedico(notificacaoServicoAplicacao);
        return headerMedico;
    }
    
    private HorizontalLayout criarResumoCards() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidthFull();
        layout.setPadding(true);
        layout.setSpacing(true);
        layout.getStyle().set("padding", "24px 32px");
        layout.getStyle().set("background", "#F9FAFB");
        
        layout.add(
            criarResumoCard("Consultas Hoje", "5", "Agendadas para hoje", "#3B82F6", "calendar"),
            criarResumoCard("Pacientes", "142", "Em acompanhamento", "#10B981", "users"),
            criarResumoCard("Exames p/ Revisar", "8", "Aguardando liberação", "#F59E0B", "document"),
            criarResumoCard("Notificações", "4", "Não lidas", "#8B5CF6", "bell")
        );
        
        return layout;
    }
    
    private Div criarResumoCard(String titulo, String valor, String descricao, String cor, String tipoIcone) {
        Div card = new Div();
        card.getStyle().set("background", "white");
        card.getStyle().set("border-radius", "12px");
        card.getStyle().set("padding", "20px");
        card.getStyle().set("box-shadow", "0 1px 3px rgba(0,0,0,0.1)");
        card.getStyle().set("position", "relative");
        card.getStyle().set("flex", "1");
        card.getStyle().set("min-width", "200px");
        
        Span tituloSpan = new Span(titulo);
        tituloSpan.getStyle().set("font-size", "14px");
        tituloSpan.getStyle().set("font-weight", "500");
        tituloSpan.getStyle().set("color", "#111827");
        tituloSpan.getStyle().set("display", "block");
        tituloSpan.getStyle().set("margin-bottom", "16px");
        
        Span valorSpan = new Span(valor);
        valorSpan.getStyle().set("font-size", "32px");
        valorSpan.getStyle().set("font-weight", "700");
        valorSpan.getStyle().set("color", "#111827");
        valorSpan.getStyle().set("display", "block");
        valorSpan.getStyle().set("line-height", "1");
        valorSpan.getStyle().set("margin-bottom", "8px");
        
        Span descricaoSpan = new Span(descricao);
        descricaoSpan.getStyle().set("font-size", "14px");
        descricaoSpan.getStyle().set("color", "#6B7280");
        descricaoSpan.getStyle().set("display", "block");
        
        Div iconContainer = criarIconeResumo(cor, tipoIcone);
        iconContainer.getStyle().set("position", "absolute");
        iconContainer.getStyle().set("top", "20px");
        iconContainer.getStyle().set("right", "20px");
        
        card.add(tituloSpan, valorSpan, descricaoSpan, iconContainer);
        return card;
    }
    
    private Div criarIconeResumo(String cor, String tipoIcone) {
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
            case "users" -> "#D1FAE5";
            case "document" -> "#FEF3C7";
            case "bell" -> "#F3E8FF";
            default -> "#F3F4F6";
        };
    }
    
    private String getSvgPath(String tipoIcone) {
        return switch (tipoIcone) {
            case "calendar" -> "M8 2v4M16 2v4M3 10h18M5 4h14a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V6a2 2 0 0 1 2-2z";
            case "users" -> "M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2M23 21v-2a4 4 0 0 0-3-3.87M16 3.13a4 4 0 0 1 0 7.75M13 7a4 4 0 1 1-8 0 4 4 0 0 1 8 0z";
            case "document" -> "M9 12h6m-6 4h6m2 5H7a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h5.586a1 1 0 0 1 .707.293l5.414 5.414a1 1 0 0 1 .293.707V19a2 2 0 0 1-2 2z";
            case "bell" -> "M15 17h5l-1.405-1.405A2.032 2.032 0 0 1 18 14.158V11a6.002 6.002 0 0 0-4-5.659V5a2 2 0 1 0-4 0v.341C7.67 6.165 6 8.388 6 11v3.159c0 .538-.214 1.055-.595 1.436L4 17h5m6 0v1a3 3 0 1 1-6 0v-1m6 0H9";
            default -> "";
        };
    }
    
    private Tabs criarAbas() {
        Tabs tabs = new Tabs();
        tabs.addClassNames("dashboard-tabs");
        tabs.setWidthFull();
        tabs.getStyle().set("background", "white");
        tabs.getStyle().set("padding", "0");
        tabs.getStyle().set("border-bottom", "1px solid #E5E7EB");
        tabs.getStyle().set("box-shadow", "none");
        
        Tab agenda = new Tab("Agenda");
        Tab pacientes = new Tab("Pacientes");
        Tab prontuarios = new Tab("Prontuários");
        Tab colegas = new Tab("Colegas");
        Tab historico = new Tab("Histórico");
        Tab notificacoes = new Tab("Notificações");
        
        tabs.add(agenda, pacientes, prontuarios, colegas, historico, notificacoes);
        tabs.setSelectedTab(agenda);
        
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
        
        atualizarConteudoAgenda();
        return conteudoLayout;
    }
    
    private void atualizarConteudo(Tab tab) {
        conteudoLayout.removeAll();
        
        if (tab.getLabel().equals("Agenda")) {
            atualizarConteudoAgenda();
        } else if (tab.getLabel().equals("Prontuários")) {
            atualizarConteudoProntuarios();
        } else {
            Div placeholder = new Div();
            placeholder.setText("Conteúdo de " + tab.getLabel() + " em desenvolvimento");
            placeholder.getStyle().set("padding", "40px");
            placeholder.getStyle().set("text-align", "center");
            placeholder.getStyle().set("color", "#6B7280");
            conteudoLayout.add(placeholder);
        }
    }
    
    private void atualizarConteudoAgenda() {
        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.setWidthFull();
        headerLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        headerLayout.setAlignItems(Alignment.CENTER);
        headerLayout.getStyle().set("margin-bottom", "24px");
        
        VerticalLayout tituloLayout = new VerticalLayout();
        tituloLayout.setPadding(false);
        tituloLayout.setSpacing(false);
        
        H2 titulo = new H2("Agenda de Hoje");
        titulo.getStyle().set("margin", "0");
        titulo.getStyle().set("font-size", "24px");
        titulo.getStyle().set("font-weight", "600");
        titulo.getStyle().set("color", "#111827");
        
        LocalDate hoje = LocalDate.now();
        String dataFormatada = hoje.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("pt", "BR")) + 
                              ", " + hoje.getDayOfMonth() + " de " + 
                              hoje.getMonth().getDisplayName(TextStyle.FULL, new Locale("pt", "BR")) + 
                              " de " + hoje.getYear();
        
        Span dataSpan = new Span(dataFormatada);
        dataSpan.getStyle().set("font-size", "14px");
        dataSpan.getStyle().set("color", "#6B7280");
        dataSpan.getStyle().set("margin-top", "4px");
        
        tituloLayout.add(titulo, dataSpan);
        
        Div verCalendario = new Div();
        verCalendario.getStyle().set("background", "white");
        verCalendario.getStyle().set("color", "#3B82F6");
        verCalendario.getStyle().set("border", "1px solid #3B82F6");
        verCalendario.getStyle().set("border-radius", "8px");
        verCalendario.getStyle().set("padding", "10px 20px");
        verCalendario.getStyle().set("font-size", "14px");
        verCalendario.getStyle().set("font-weight", "600");
        verCalendario.getStyle().set("cursor", "pointer");
        verCalendario.getStyle().set("display", "inline-flex");
        verCalendario.getStyle().set("align-items", "center");
        verCalendario.getStyle().set("gap", "8px");
        
        String svgContent = "<svg width=\"16\" height=\"16\" viewBox=\"0 0 24 24\" fill=\"none\" " +
                "style=\"stroke: currentColor; stroke-width: 2; stroke-linecap: round; stroke-linejoin: round;\">" +
                "<rect x=\"3\" y=\"4\" width=\"18\" height=\"18\" rx=\"2\" ry=\"2\"></rect>" +
                "<line x1=\"16\" y1=\"2\" x2=\"16\" y2=\"6\"></line>" +
                "<line x1=\"8\" y1=\"2\" x2=\"8\" y2=\"6\"></line>" +
                "<line x1=\"3\" y1=\"10\" x2=\"21\" y2=\"10\"></line>" +
                "</svg>";
        
        Div iconDiv = new Div();
        iconDiv.getElement().setProperty("innerHTML", svgContent);
        iconDiv.getStyle().set("display", "inline-block");
        
        Span texto = new Span("Ver Calendário Completo");
        texto.getStyle().set("color", "#3B82F6");
        
        verCalendario.add(iconDiv, texto);
        
        headerLayout.add(tituloLayout, verCalendario);
        
        VerticalLayout consultasLayout = new VerticalLayout();
        consultasLayout.setPadding(false);
        consultasLayout.setSpacing(false);
        consultasLayout.setWidthFull();
        
        List<ConsultaResumo> consultas = consultaServicoAplicacao.pesquisarResumos();
        if (consultas.isEmpty()) {
            Div empty = new Div();
            empty.setText("Nenhuma consulta agendada para hoje");
            empty.getStyle().set("padding", "40px");
            empty.getStyle().set("text-align", "center");
            empty.getStyle().set("color", "#6B7280");
            consultasLayout.add(empty);
        } else {
            consultas.forEach(consulta -> {
                consultasLayout.add(new ConsultaMedicoCard(consulta));
            });
        }
        
        conteudoLayout.add(headerLayout, consultasLayout);
    }
    
    private void atualizarConteudoProntuarios() {
        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.setWidthFull();
        headerLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        headerLayout.setAlignItems(Alignment.CENTER);
        headerLayout.getStyle().set("margin-bottom", "24px");
        
        H2 titulo = new H2("Prontuários");
        titulo.getStyle().set("margin", "0");
        titulo.getStyle().set("font-size", "24px");
        titulo.getStyle().set("font-weight", "600");
        titulo.getStyle().set("color", "#111827");
        
        Button novoRegistro = new Button("+ Novo Registro");
        novoRegistro.getStyle().set("background", "#3B82F6");
        novoRegistro.getStyle().set("color", "white");
        novoRegistro.getStyle().set("border", "none");
        novoRegistro.getStyle().set("border-radius", "10px");
        novoRegistro.getStyle().set("padding", "12px 24px");
        novoRegistro.getStyle().set("font-size", "14px");
        novoRegistro.getStyle().set("font-weight", "600");
        novoRegistro.getStyle().set("cursor", "pointer");
        novoRegistro.getStyle().set("box-shadow", "0 2px 4px rgba(59, 130, 246, 0.3)");
        novoRegistro.getStyle().set("transition", "all 0.2s ease");
        
        novoRegistro.addClickListener(e -> {
            getUI().ifPresent(ui -> {
                RegistrarProntuarioDialog dialog = new RegistrarProntuarioDialog(
                    registrarProntuario,
                    consultaServicoAplicacao,
                    2L, // ID do médico (Dr. João Silva do banco de dados)
                    () -> {
                        atualizarConteudoProntuarios();
                        if (headerMedico != null) {
                            headerMedico.atualizarNotificacoes();
                        }
                    }
                );
                dialog.open();
            });
        });
        
        headerLayout.add(titulo, novoRegistro);
        
        VerticalLayout prontuariosLayout = new VerticalLayout();
        prontuariosLayout.setPadding(false);
        prontuariosLayout.setSpacing(false);
        prontuariosLayout.setWidthFull();
        
        List<ProntuarioResumo> prontuarios = prontuarioServicoAplicacao.pesquisarResumos();
        if (prontuarios.isEmpty()) {
            Div empty = new Div();
            empty.getStyle().set("padding", "60px 40px");
            empty.getStyle().set("text-align", "center");
            empty.getStyle().set("background", "#F9FAFB");
            empty.getStyle().set("border-radius", "12px");
            empty.getStyle().set("border", "2px dashed #D1D5DB");
            
            Span icon = new Span("📋");
            icon.getStyle().set("font-size", "48px");
            icon.getStyle().set("display", "block");
            icon.getStyle().set("margin-bottom", "16px");
            
            Span title = new Span("Nenhum prontuário registrado");
            title.getStyle().set("display", "block");
            title.getStyle().set("font-size", "18px");
            title.getStyle().set("font-weight", "600");
            title.getStyle().set("color", "#374151");
            title.getStyle().set("margin-bottom", "8px");
            
            Span subtitle = new Span("Comece registrando o prontuário de uma consulta");
            subtitle.getStyle().set("display", "block");
            subtitle.getStyle().set("font-size", "14px");
            subtitle.getStyle().set("color", "#6B7280");
            
            empty.add(icon, title, subtitle);
            prontuariosLayout.add(empty);
        } else {
            prontuarios.forEach(prontuario -> {
                prontuariosLayout.add(criarCardProntuario(prontuario));
            });
        }
        
        conteudoLayout.add(headerLayout, prontuariosLayout);
    }
    
    private Div criarCardProntuario(ProntuarioResumo prontuario) {
        Div card = new Div();
        card.getStyle().set("background", "white");
        card.getStyle().set("border-radius", "16px");
        card.getStyle().set("padding", "24px");
        card.getStyle().set("box-shadow", "0 2px 8px rgba(0,0,0,0.08)");
        card.getStyle().set("margin-bottom", "20px");
        card.getStyle().set("border", "1px solid #E5E7EB");
        card.getStyle().set("transition", "box-shadow 0.2s ease");
        card.addClassName("prontuario-card");
        
        // Header do card
        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.setWidthFull();
        headerLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        headerLayout.setAlignItems(Alignment.CENTER);
        headerLayout.getStyle().set("margin-bottom", "20px");
        headerLayout.getStyle().set("padding-bottom", "16px");
        headerLayout.getStyle().set("border-bottom", "2px solid #F3F4F6");
        
        VerticalLayout pacienteInfo = new VerticalLayout();
        pacienteInfo.setPadding(false);
        pacienteInfo.setSpacing(false);
        
        H3 pacienteNome = new H3(prontuario.getPacienteNome() != null ? prontuario.getPacienteNome() : "Paciente");
        pacienteNome.getStyle().set("margin", "0");
        pacienteNome.getStyle().set("font-size", "20px");
        pacienteNome.getStyle().set("font-weight", "700");
        pacienteNome.getStyle().set("color", "#111827");
        pacienteNome.getStyle().set("letter-spacing", "-0.02em");
        
        Span dataSpan = new Span();
        if (prontuario.getDataAtendimento() != null) {
            String dataFormatada = prontuario.getDataAtendimento().toLocalDate().format(
                java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")
            );
            String horaFormatada = prontuario.getDataAtendimento().toLocalTime().format(
                java.time.format.DateTimeFormatter.ofPattern("HH:mm")
            );
            dataSpan.setText(dataFormatada + " às " + horaFormatada);
        }
        dataSpan.getStyle().set("font-size", "14px");
        dataSpan.getStyle().set("color", "#6B7280");
        dataSpan.getStyle().set("margin-top", "4px");
        
        if (prontuario.getMedicoNome() != null) {
            Span medicoSpan = new Span("Dr(a). " + prontuario.getMedicoNome());
            medicoSpan.getStyle().set("font-size", "13px");
            medicoSpan.getStyle().set("color", "#9CA3AF");
            medicoSpan.getStyle().set("margin-top", "2px");
            pacienteInfo.add(pacienteNome, dataSpan, medicoSpan);
        } else {
            pacienteInfo.add(pacienteNome, dataSpan);
        }
        
        // Badge de tipo de atendimento
        if (prontuario.getTipoAtendimento() != null) {
            Span tipoBadge = new Span(prontuario.getTipoAtendimento());
            tipoBadge.getStyle().set("background", "#DBEAFE");
            tipoBadge.getStyle().set("color", "#1E40AF");
            tipoBadge.getStyle().set("padding", "6px 12px");
            tipoBadge.getStyle().set("border-radius", "20px");
            tipoBadge.getStyle().set("font-size", "12px");
            tipoBadge.getStyle().set("font-weight", "600");
            tipoBadge.getStyle().set("text-transform", "uppercase");
            headerLayout.add(pacienteInfo, tipoBadge);
        } else {
            headerLayout.add(pacienteInfo);
        }
        
        // Conteúdo principal
        VerticalLayout conteudoLayout = new VerticalLayout();
        conteudoLayout.setPadding(false);
        conteudoLayout.setSpacing(true);
        conteudoLayout.setWidthFull();
        
        // Seção de Diagnóstico
        if (prontuario.getDiagnostico() != null && !prontuario.getDiagnostico().trim().isEmpty()) {
            Div diagnosticoSection = new Div();
            diagnosticoSection.getStyle().set("background", "#FEF3C7");
            diagnosticoSection.getStyle().set("border-left", "4px solid #F59E0B");
            diagnosticoSection.getStyle().set("padding", "16px");
            diagnosticoSection.getStyle().set("border-radius", "8px");
            diagnosticoSection.getStyle().set("margin-bottom", "12px");
            
            Span diagnosticoLabel = new Span("📋 Diagnóstico");
            diagnosticoLabel.getStyle().set("font-weight", "700");
            diagnosticoLabel.getStyle().set("font-size", "14px");
            diagnosticoLabel.getStyle().set("color", "#92400E");
            diagnosticoLabel.getStyle().set("display", "block");
            diagnosticoLabel.getStyle().set("margin-bottom", "8px");
            
            Span diagnostico = new Span(prontuario.getDiagnostico());
            diagnostico.getStyle().set("display", "block");
            diagnostico.getStyle().set("color", "#78350F");
            diagnostico.getStyle().set("font-size", "14px");
            diagnostico.getStyle().set("line-height", "1.6");
            
            diagnosticoSection.add(diagnosticoLabel, diagnostico);
            conteudoLayout.add(diagnosticoSection);
        }
        
        // Seção de Prescrição
        if (prontuario.getPrescricao() != null && !prontuario.getPrescricao().trim().isEmpty()) {
            Div prescricaoSection = new Div();
            prescricaoSection.getStyle().set("background", "#D1FAE5");
            prescricaoSection.getStyle().set("border-left", "4px solid #10B981");
            prescricaoSection.getStyle().set("padding", "16px");
            prescricaoSection.getStyle().set("border-radius", "8px");
            prescricaoSection.getStyle().set("margin-bottom", "12px");
            
            Span prescricaoLabel = new Span("💊 Prescrição");
            prescricaoLabel.getStyle().set("font-weight", "700");
            prescricaoLabel.getStyle().set("font-size", "14px");
            prescricaoLabel.getStyle().set("color", "#065F46");
            prescricaoLabel.getStyle().set("display", "block");
            prescricaoLabel.getStyle().set("margin-bottom", "8px");
            
            Span prescricao = new Span(prontuario.getPrescricao());
            prescricao.getStyle().set("display", "block");
            prescricao.getStyle().set("color", "#047857");
            prescricao.getStyle().set("font-size", "14px");
            prescricao.getStyle().set("line-height", "1.6");
            prescricao.getStyle().set("white-space", "pre-wrap");
            
            prescricaoSection.add(prescricaoLabel, prescricao);
            conteudoLayout.add(prescricaoSection);
        }
        
        // Seção de Observações (se existir)
        if (prontuario.getObservacoes() != null && !prontuario.getObservacoes().trim().isEmpty()) {
            Div obsSection = new Div();
            obsSection.getStyle().set("background", "#E0E7FF");
            obsSection.getStyle().set("border-left", "4px solid #6366F1");
            obsSection.getStyle().set("padding", "16px");
            obsSection.getStyle().set("border-radius", "8px");
            
            Span obsLabel = new Span("📝 Observações");
            obsLabel.getStyle().set("font-weight", "700");
            obsLabel.getStyle().set("font-size", "14px");
            obsLabel.getStyle().set("color", "#3730A3");
            obsLabel.getStyle().set("display", "block");
            obsLabel.getStyle().set("margin-bottom", "8px");
            
            Span observacoes = new Span(prontuario.getObservacoes());
            observacoes.getStyle().set("display", "block");
            observacoes.getStyle().set("color", "#4338CA");
            observacoes.getStyle().set("font-size", "14px");
            observacoes.getStyle().set("line-height", "1.6");
            observacoes.getStyle().set("white-space", "pre-wrap");
            
            obsSection.add(obsLabel, observacoes);
            conteudoLayout.add(obsSection);
        }
        
        card.add(headerLayout, conteudoLayout);
        
        return card;
    }
}

