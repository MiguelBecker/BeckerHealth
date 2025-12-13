package dev.beckerhealth.apresentacao.vaadin.admin;

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
import com.vaadin.flow.component.dependency.JavaScript;

@PageTitle("Dashboard - Administrador")
@Route("admin/dashboard")
@JavaScript("https://cdn.jsdelivr.net/npm/chart.js@4.4.0/dist/chart.umd.min.js")
public class DashboardAdminView extends VerticalLayout {
    
    public DashboardAdminView() {
        setPadding(false);
        setSpacing(false);
        setSizeFull();
        getStyle().set("background", "#F9FAFB");
        
        add(criarHeader(), criarResumoCards(), criarAbas(), criarConteudo());
    }
    
    private HeaderAdmin criarHeader() {
        return new HeaderAdmin();
    }
    
    private HorizontalLayout criarResumoCards() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidthFull();
        layout.setPadding(true);
        layout.setSpacing(true);
        layout.getStyle().set("padding", "24px 32px");
        layout.getStyle().set("background", "#F9FAFB");
        
        layout.add(
            criarResumoCard("Total de Pacientes", "1.247", "Cadastrados no sistema", "#3B82F6", "users"),
            criarResumoCard("Consultas no Mês", "342", "+12.5% vs mês anterior", "#10B981", "calendar"),
            criarResumoCard("Receita do Mês", "R$ 125k", "Faturamento total", "#F59E0B", "dollar"),
            criarResumoCard("Taxa de Crescimento", "12.5%", "Últimos 6 meses", "#8B5CF6", "trend")
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
        if (descricao.contains("+") || descricao.contains("%")) {
            descricaoSpan.getStyle().set("color", "#10B981");
        } else {
            descricaoSpan.getStyle().set("color", "#6B7280");
        }
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
            case "users" -> "#EFF6FF";
            case "calendar" -> "#D1FAE5";
            case "dollar" -> "#FEF3C7";
            case "trend" -> "#F3E8FF";
            default -> "#F3F4F6";
        };
    }
    
    private String getSvgPath(String tipoIcone) {
        return switch (tipoIcone) {
            case "users" -> "M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2M23 21v-2a4 4 0 0 0-3-3.87M16 3.13a4 4 0 0 1 0 7.75M13 7a4 4 0 1 1-8 0 4 4 0 0 1 8 0z";
            case "calendar" -> "M8 2v4M16 2v4M3 10h18M5 4h14a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V6a2 2 0 0 1 2-2z";
            case "dollar" -> "M12 2v20M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6";
            case "trend" -> "M23 6l-9.5 9.5-5-5L1 18M17 6h6v6";
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
        
        Tab visaoGeral = new Tab("Visão Geral");
        Tab relatorios = new Tab("Relatórios");
        Tab gestaoUsuarios = new Tab("Gestão de Usuários");
        Tab medicos = new Tab("Médicos");
        Tab historico = new Tab("Histórico");
        
        tabs.add(visaoGeral, relatorios, gestaoUsuarios, medicos, historico);
        tabs.setSelectedTab(visaoGeral);
        
        tabs.addSelectedChangeListener(e -> atualizarConteudo(e.getSelectedTab()));
        
        return tabs;
    }
    
    private VerticalLayout criarConteudo() {
        VerticalLayout conteudoLayout = new VerticalLayout();
        conteudoLayout.setWidthFull();
        conteudoLayout.setPadding(true);
        conteudoLayout.getStyle().set("padding", "24px 32px");
        conteudoLayout.getStyle().set("max-width", "1400px");
        conteudoLayout.getStyle().set("margin", "0 auto");
        
        atualizarConteudoVisaoGeral(conteudoLayout);
        return conteudoLayout;
    }
    
    private void atualizarConteudo(Tab tab) {
        VerticalLayout conteudoLayout = (VerticalLayout) getChildren()
            .filter(c -> c instanceof VerticalLayout && c.getClass().getSimpleName().equals("VerticalLayout"))
            .findFirst().orElse(null);
        
        if (conteudoLayout == null) return;
        
        conteudoLayout.removeAll();
        
        if (tab.getLabel().equals("Visão Geral")) {
            atualizarConteudoVisaoGeral(conteudoLayout);
        } else {
            Div placeholder = new Div();
            placeholder.setText("Conteúdo de " + tab.getLabel() + " em desenvolvimento");
            placeholder.getStyle().set("padding", "40px");
            placeholder.getStyle().set("text-align", "center");
            placeholder.getStyle().set("color", "#6B7280");
            conteudoLayout.add(placeholder);
        }
    }
    
    private void atualizarConteudoVisaoGeral(VerticalLayout layout) {
        // Primeira linha de gráficos
        HorizontalLayout primeiraLinha = new HorizontalLayout();
        primeiraLinha.setWidthFull();
        primeiraLinha.setSpacing(true);
        primeiraLinha.setPadding(false);
        
        Div chart1 = criarChartCard("Consultas por Dia da Semana", "chart1", "bar");
        Div chart2 = criarChartCard("Consultas por Tipo", "chart2", "pie");
        
        primeiraLinha.add(chart1, chart2);
        primeiraLinha.setFlexGrow(1, chart1, chart2);
        
        // Segunda linha de gráficos
        HorizontalLayout segundaLinha = new HorizontalLayout();
        segundaLinha.setWidthFull();
        segundaLinha.setSpacing(true);
        segundaLinha.setPadding(false);
        
        Div chart3 = criarChartCard("Distribuição por Convênio", "chart3", "pie");
        Div chart4 = criarChartCard("Evolução Mensal", "chart4", "line");
        
        segundaLinha.add(chart3, chart4);
        segundaLinha.setFlexGrow(1, chart3, chart4);
        
        layout.add(primeiraLinha, segundaLinha);
        
        // Criar gráficos após o layout ser renderizado
        getUI().ifPresent(ui -> {
            ui.getPage().executeJs(
                "setTimeout(function() {" +
                "  if (typeof Chart === 'undefined') {" +
                "    console.log('Aguardando Chart.js...');" +
                "    setTimeout(arguments.callee, 200);" +
                "    return;" +
                "  }" +
                "  " + createBarChartScript() + 
                "  " + createPieChartTipoScript() +
                "  " + createPieChartConvenioScript() +
                "  " + createLineChartScript() +
                "}, 1500);"
            );
        });
    }
    
    private Div criarChartCard(String titulo, String chartId, String tipo) {
        Div card = new Div();
        card.getStyle().set("background", "white");
        card.getStyle().set("border-radius", "12px");
        card.getStyle().set("padding", "20px");
        card.getStyle().set("box-shadow", "0 1px 3px rgba(0,0,0,0.1)");
        card.getStyle().set("flex", "1");
        card.getStyle().set("min-width", "400px");
        
        H2 tituloH2 = new H2(titulo);
        tituloH2.getStyle().set("margin", "0 0 16px");
        tituloH2.getStyle().set("font-size", "18px");
        tituloH2.getStyle().set("font-weight", "600");
        tituloH2.getStyle().set("color", "#111827");
        
        Div canvasContainer = new Div();
        canvasContainer.setId(chartId);
        canvasContainer.setWidth("100%");
        canvasContainer.getStyle().set("height", "300px");
        canvasContainer.getStyle().set("max-height", "300px");
        canvasContainer.getStyle().set("position", "relative");
        
        // Criar canvas HTML5 diretamente no HTML
        String canvasHtml = "<canvas id=\"" + chartId + "-canvas\" style=\"width: 100%; height: 300px;\"></canvas>";
        canvasContainer.getElement().setProperty("innerHTML", canvasHtml);
        
        card.add(tituloH2, canvasContainer);
        return card;
    }
    
    private String createBarChartScript() {
        return "var ctx1 = document.getElementById('chart1-canvas');" +
               "if (ctx1) {" +
               "  new Chart(ctx1.getContext('2d'), {" +
               "    type: 'bar'," +
               "    data: {" +
               "      labels: ['Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb']," +
               "      datasets: [{" +
               "        label: 'Consultas'," +
               "        data: [43, 50, 45, 55, 50, 28]," +
               "        backgroundColor: 'rgba(59, 130, 246, 0.8)'," +
               "        borderColor: 'rgba(59, 130, 246, 1)'," +
               "        borderWidth: 1" +
               "      }]" +
               "    }," +
               "    options: {" +
               "      responsive: true," +
               "      maintainAspectRatio: false," +
               "      plugins: {" +
               "        title: { display: true, text: 'Distribuição semanal de atendimentos' }," +
               "        legend: { display: false }" +
               "      }," +
               "      scales: {" +
               "        y: { beginAtZero: true, max: 80, ticks: { stepSize: 20 } }" +
               "      }" +
               "    }" +
               "  });" +
               "}";
    }
    
    private String createPieChartTipoScript() {
        return "var ctx2 = document.getElementById('chart2-canvas');" +
               "if (ctx2) {" +
               "  new Chart(ctx2.getContext('2d'), {" +
               "    type: 'pie'," +
               "    data: {" +
               "      labels: ['Rotina', 'Retorno', 'Urgência']," +
               "      datasets: [{" +
               "        data: [61, 29, 10]," +
               "        backgroundColor: ['#10B981', '#3B82F6', '#EF4444']" +
               "      }]" +
               "    }," +
               "    options: {" +
               "      responsive: true," +
               "      maintainAspectRatio: false," +
               "      plugins: {" +
               "        title: { display: true, text: 'Distribuição por categoria de atendimento' }," +
               "        legend: { position: 'bottom' }" +
               "      }" +
               "    }" +
               "  });" +
               "}";
    }
    
    private String createPieChartConvenioScript() {
        return "var ctx3 = document.getElementById('chart3-canvas');" +
               "if (ctx3) {" +
               "  new Chart(ctx3.getContext('2d'), {" +
               "    type: 'pie'," +
               "    data: {" +
               "      labels: ['Unimed', 'Bradesco Saúde', 'Particular', 'SulAmérica']," +
               "      datasets: [{" +
               "        data: [42, 29, 18, 11]," +
               "        backgroundColor: ['#10B981', '#8B5CF6', '#6366F1', '#EC4899']" +
               "      }]" +
               "    }," +
               "    options: {" +
               "      responsive: true," +
               "      maintainAspectRatio: false," +
               "      plugins: {" +
               "        title: { display: true, text: 'Consultas por plano de saúde' }," +
               "        legend: { position: 'bottom' }" +
               "      }" +
               "    }" +
               "  });" +
               "}";
    }
    
    private String createLineChartScript() {
        return "var ctx4 = document.getElementById('chart4-canvas');" +
               "if (ctx4) {" +
               "  new Chart(ctx4.getContext('2d'), {" +
               "    type: 'line'," +
               "    data: {" +
               "      labels: ['Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out']," +
               "      datasets: [" +
               "        {" +
               "          label: 'Consultas'," +
               "          data: [270, 280, 290, 300, 290, 310]," +
               "          borderColor: 'rgba(59, 130, 246, 1)'," +
               "          backgroundColor: 'rgba(59, 130, 246, 0.1)'," +
               "          yAxisID: 'y'," +
               "          tension: 0.4" +
               "        }," +
               "        {" +
               "          label: 'Receita'," +
               "          data: [90, 100, 110, 120, 115, 130]," +
               "          borderColor: 'rgba(16, 185, 129, 1)'," +
               "          backgroundColor: 'rgba(16, 185, 129, 0.1)'," +
               "          yAxisID: 'y1'," +
               "          tension: 0.4" +
               "        }" +
               "      ]" +
               "    }," +
               "    options: {" +
               "      responsive: true," +
               "      maintainAspectRatio: false," +
               "      interaction: { mode: 'index', intersect: false }," +
               "      plugins: {" +
               "        title: { display: true, text: 'Consultas e receita nos últimos 6 meses' }" +
               "      }," +
               "      scales: {" +
               "        y: { type: 'linear', display: true, position: 'left', beginAtZero: true, max: 360, ticks: { stepSize: 90 } }," +
               "        y1: { type: 'linear', display: true, position: 'right', beginAtZero: true, max: 140, ticks: { stepSize: 35 }, grid: { drawOnChartArea: false } }" +
               "      }" +
               "    }" +
               "  });" +
               "}";
    }
}

