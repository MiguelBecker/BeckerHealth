package dev.beckerhealth.apresentacao.vaadin.paciente;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import dev.beckerhealth.dominio.prontuario.*;

import java.util.List;

@PageTitle("Meu Prontuário")
@Route("paciente/prontuario")
public class ProntuarioPacienteView extends VerticalLayout {

    private final ProntuarioService prontuarioService;
    private VerticalLayout conteudoLayout;
    private Tabs tabs;
    private final Long pacienteId = 4L; // Paciente mockado (Carlos Oliveira)

    public ProntuarioPacienteView(ProntuarioService prontuarioService) {
        this.prontuarioService = prontuarioService;

        setPadding(false);
        setSpacing(false);
        setSizeFull();
        getStyle().set("background", "#F9FAFB");

        add(criarHeader(), criarAbas(), criarConteudo());
    }

    private HeaderPaciente criarHeader() {
        return new HeaderPaciente();
    }

    private Tabs criarAbas() {
        tabs = new Tabs();
        tabs.setWidthFull();
        tabs.getStyle().set("background", "white");
        tabs.getStyle().set("border-bottom", "1px solid #E5E7EB");

        Tab atendimentos = new Tab("Meus Atendimentos");
        Tab exames = new Tab("Meus Exames");
        Tab prescricoes = new Tab("Minhas Prescrições");
        Tab resumo = new Tab("Resumo de Saúde");

        tabs.add(atendimentos, exames, prescricoes, resumo);
        tabs.setSelectedTab(atendimentos);

        tabs.addSelectedChangeListener(e -> atualizarConteudo());

        return tabs;
    }

    private VerticalLayout criarConteudo() {
        conteudoLayout = new VerticalLayout();
        conteudoLayout.setWidthFull();
        conteudoLayout.setPadding(true);
        atualizarConteudoAtendimentos();
        return conteudoLayout;
    }

    private void atualizarConteudo() {
        conteudoLayout.removeAll();

        Tab abaSelecionada = tabs.getSelectedTab();
        if (abaSelecionada.getLabel().equals("Meus Atendimentos")) {
            atualizarConteudoAtendimentos();
        } else if (abaSelecionada.getLabel().equals("Meus Exames")) {
            atualizarConteudoExames();
        } else if (abaSelecionada.getLabel().equals("Minhas Prescrições")) {
            atualizarConteudoPrescricoes();
        } else if (abaSelecionada.getLabel().equals("Resumo de Saúde")) {
            atualizarConteudoResumo();
        }
    }

    private void atualizarConteudoAtendimentos() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setWidthFull();

        H3 titulo = new H3("Histórico de Atendimentos");
        titulo.getStyle().set("margin-bottom", "24px");

        // Lista de atendimentos
        Grid<Prontuario> grid = new Grid<>(Prontuario.class, false);
        grid.addColumn(prontuario -> prontuario.getDataAtendimento().toLocalDate())
            .setHeader("Data do Atendimento")
            .setSortable(true);
        grid.addColumn(prontuario -> prontuario.getMedicoResponsavel() != null ?
            prontuario.getMedicoResponsavel().getNome() : "Não informado")
            .setHeader("Médico");
        grid.addColumn(prontuario -> prontuario.getTipoAtendimento().name())
            .setHeader("Tipo");
        grid.addColumn(prontuario -> {
            String diagnostico = prontuario.getDiagnostico();
            return diagnostico != null && diagnostico.length() > 50 ?
                diagnostico.substring(0, 50) + "..." : diagnostico;
        }).setHeader("Diagnóstico");

        List<Prontuario> atendimentos = prontuarioService.buscarPorPaciente(pacienteId);
        grid.setItems(atendimentos);

        grid.addItemClickListener(event -> mostrarDetalhesAtendimento(event.getItem()));

        if (atendimentos.isEmpty()) {
            Div emptyState = new Div();
            emptyState.setText("Você ainda não possui atendimentos registrados.");
            emptyState.getStyle().set("text-align", "center");
            emptyState.getStyle().set("padding", "40px");
            emptyState.getStyle().set("color", "#6B7280");
            emptyState.getStyle().set("background", "white");
            emptyState.getStyle().set("border-radius", "8px");
            layout.add(titulo, emptyState);
        } else {
            layout.add(titulo, grid);
        }

        conteudoLayout.add(layout);
    }

    private void atualizarConteudoExames() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setWidthFull();

        H3 titulo = new H3("Meus Exames");
        titulo.getStyle().set("margin-bottom", "24px");

        // Aviso sobre resultados
        Div avisoDiv = new Div();
        avisoDiv.setText("Os resultados dos exames ficam ocultos até serem liberados pelo médico responsável.");
        avisoDiv.getStyle().set("background", "#FEF3C7");
        avisoDiv.getStyle().set("color", "#92400E");
        avisoDiv.getStyle().set("padding", "16px");
        avisoDiv.getStyle().set("border-radius", "8px");
        avisoDiv.getStyle().set("border", "1px solid #FCD34D");
        avisoDiv.getStyle().set("margin-bottom", "24px");

        // Lista de exames
        Grid<Exame> grid = new Grid<>(Exame.class, false);
        grid.addColumn(Exame::getNomeExame).setHeader("Exame Solicitado");
        grid.addColumn(exame -> exame.getDataSolicitacao().toLocalDate()).setHeader("Data da Solicitação");
        grid.addColumn(exame -> exame.getStatus().name()).setHeader("Status");
        grid.addColumn(exame -> {
            if (exame.getStatus() == Exame.StatusExame.LIBERADO) {
                return "Disponível";
            } else {
                return "Aguardando liberação";
            }
        }).setHeader("Resultado");

        List<Exame> exames = prontuarioService.listarExames();
        // Filtrar exames do paciente atual
        List<Exame> examesPaciente = exames.stream()
            .filter(exame -> exame.getProntuario() != null &&
                           exame.getProntuario().getPaciente().getId().equals(pacienteId))
            .toList();
        grid.setItems(examesPaciente);

        grid.addItemClickListener(event -> {
            if (event.getItem().getStatus() == Exame.StatusExame.LIBERADO) {
                mostrarResultadoExame(event.getItem());
            } else {
                Notification.show("Resultado ainda não liberado pelo médico", 3000, Notification.Position.TOP_CENTER);
            }
        });

        if (examesPaciente.isEmpty()) {
            Div emptyState = new Div();
            emptyState.setText("Você ainda não possui exames solicitados.");
            emptyState.getStyle().set("text-align", "center");
            emptyState.getStyle().set("padding", "40px");
            emptyState.getStyle().set("color", "#6B7280");
            emptyState.getStyle().set("background", "white");
            emptyState.getStyle().set("border-radius", "8px");
            layout.add(titulo, avisoDiv, emptyState);
        } else {
            layout.add(titulo, avisoDiv, grid);
        }

        conteudoLayout.add(layout);
    }

    private void atualizarConteudoPrescricoes() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setWidthFull();

        H3 titulo = new H3("Minhas Prescrições Médicas");
        titulo.getStyle().set("margin-bottom", "24px");

        // Lista de prescrições
        Grid<Prescricao> grid = new Grid<>(Prescricao.class, false);
        grid.addColumn(prescricao -> {
            String medicamentos = prescricao.getMedicamentos();
            return medicamentos != null && medicamentos.length() > 50 ?
                medicamentos.substring(0, 50) + "..." : medicamentos;
        }).setHeader("Medicamentos");
        grid.addColumn(prescricao -> {
            String posologia = prescricao.getPosologia();
            return posologia != null && posologia.length() > 30 ?
                posologia.substring(0, 30) + "..." : posologia;
        }).setHeader("Posologia");
        grid.addColumn(prescricao -> prescricao.getDataEmissao().toString()).setHeader("Data de Emissão");
        grid.addColumn(prescricao -> prescricao.getDataValidade().toString()).setHeader("Data de Validade");
        grid.addColumn(prescricao -> {
            if (prescricao.isValida()) {
                Span validoSpan = new Span("Válida");
                validoSpan.getStyle().set("color", "#10B981");
                validoSpan.getStyle().set("font-weight", "600");
                return validoSpan;
            } else {
                Span expiradoSpan = new Span("Expirada");
                expiradoSpan.getStyle().set("color", "#EF4444");
                expiradoSpan.getStyle().set("font-weight", "600");
                return expiradoSpan;
            }
        }).setHeader("Status");

        List<Prescricao> prescricoes = prontuarioService.listarPrescricoes();
        // Filtrar prescrições do paciente atual
        List<Prescricao> prescricoesPaciente = prescricoes.stream()
            .filter(prescricao -> prescricao.getProntuario() != null &&
                                prescricao.getProntuario().getPaciente().getId().equals(pacienteId))
            .toList();
        grid.setItems(prescricoesPaciente);

        grid.addItemClickListener(event -> mostrarDetalhesPrescricao(event.getItem()));

        if (prescricoesPaciente.isEmpty()) {
            Div emptyState = new Div();
            emptyState.setText("Você ainda não possui prescrições médicas.");
            emptyState.getStyle().set("text-align", "center");
            emptyState.getStyle().set("padding", "40px");
            emptyState.getStyle().set("color", "#6B7280");
            emptyState.getStyle().set("background", "white");
            emptyState.getStyle().set("border-radius", "8px");
            layout.add(titulo, emptyState);
        } else {
            layout.add(titulo, grid);
        }

        conteudoLayout.add(layout);
    }

    private void atualizarConteudoResumo() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setWidthFull();

        H3 titulo = new H3("Resumo da Minha Saúde");
        titulo.getStyle().set("margin-bottom", "24px");

        // Estatísticas de saúde
        HorizontalLayout statsLayout = new HorizontalLayout();
        statsLayout.setWidthFull();
        statsLayout.setSpacing(true);

        List<Prontuario> prontuarios = prontuarioService.buscarPorPaciente(pacienteId);
        List<Exame> exames = prontuarioService.listarExames().stream()
            .filter(exame -> exame.getProntuario() != null &&
                           exame.getProntuario().getPaciente().getId().equals(pacienteId))
            .toList();
        List<Prescricao> prescricoes = prontuarioService.listarPrescricoes().stream()
            .filter(prescricao -> prescricao.getProntuario() != null &&
                                prescricao.getProntuario().getPaciente().getId().equals(pacienteId))
            .toList();

        long examesLiberados = exames.stream()
            .filter(exame -> exame.getStatus() == Exame.StatusExame.LIBERADO)
            .count();
        long prescricoesValidas = prescricoes.stream()
            .filter(Prescricao::isValida)
            .count();

        Div stat1 = criarCardResumo("Total de Atendimentos", String.valueOf(prontuarios.size()), "#3B82F6", "stethoscope");
        Div stat2 = criarCardResumo("Exames Liberados", String.valueOf(examesLiberados), "#10B981", "document");
        Div stat3 = criarCardResumo("Prescrições Ativas", String.valueOf(prescricoesValidas), "#8B5CF6", "pill");
        Div stat4 = criarCardResumo("Último Atendimento", calcularUltimoAtendimento(prontuarios), "#F59E0B", "calendar");

        statsLayout.add(stat1, stat2, stat3, stat4);

        // Últimos diagnósticos
        Div diagnosticosDiv = new Div();
        diagnosticosDiv.getStyle().set("background", "white");
        diagnosticosDiv.getStyle().set("border-radius", "8px");
        diagnosticosDiv.getStyle().set("padding", "20px");
        diagnosticosDiv.getStyle().set("margin-top", "24px");

        H3 diagnosticosTitle = new H3("Últimos Diagnósticos");
        diagnosticosTitle.getStyle().set("margin-top", "0");
        diagnosticosTitle.getStyle().set("font-size", "18px");

        VerticalLayout diagnosticosList = new VerticalLayout();
        diagnosticosList.setSpacing(false);
        diagnosticosList.setPadding(false);

        prontuarios.stream()
            .filter(p -> p.getDiagnostico() != null && !p.getDiagnostico().isEmpty())
            .limit(5)
            .forEach(prontuario -> {
                Div diagnosticoItem = new Div();
                diagnosticoItem.getStyle().set("padding", "12px");
                diagnosticoItem.getStyle().set("border-bottom", "1px solid #E5E7EB");
                diagnosticoItem.getStyle().set("display", "flex");
                diagnosticoItem.getStyle().set("justify-content", "space-between");

                Span diagnosticoText = new Span(prontuario.getDiagnostico().length() > 60 ?
                    prontuario.getDiagnostico().substring(0, 60) + "..." :
                    prontuario.getDiagnostico());

                Span dataText = new Span(prontuario.getDataAtendimento().toLocalDate().toString());
                dataText.getStyle().set("color", "#6B7280");
                dataText.getStyle().set("font-size", "14px");

                diagnosticoItem.add(diagnosticoText, dataText);
                diagnosticosList.add(diagnosticoItem);
            });

        if (prontuarios.stream().noneMatch(p -> p.getDiagnostico() != null && !p.getDiagnostico().isEmpty())) {
            Div noDiagnosticos = new Div();
            noDiagnosticos.setText("Nenhum diagnóstico registrado ainda.");
            noDiagnosticos.getStyle().set("color", "#6B7280");
            noDiagnosticos.getStyle().set("padding", "20px");
            noDiagnosticos.getStyle().set("text-align", "center");
            diagnosticosList.add(noDiagnosticos);
        }

        diagnosticosDiv.add(diagnosticosTitle, diagnosticosList);

        layout.add(titulo, statsLayout, diagnosticosDiv);
        conteudoLayout.add(layout);
    }

    private Div criarCardResumo(String titulo, String valor, String cor, String icone) {
        Div card = new Div();
        card.getStyle().set("background", "white");
        card.getStyle().set("border-radius", "8px");
        card.getStyle().set("padding", "20px");
        card.getStyle().set("box-shadow", "0 1px 3px rgba(0,0,0,0.1)");
        card.getStyle().set("flex", "1");
        card.getStyle().set("text-align", "center");

        // Ícone
        Div iconDiv = new Div();
        iconDiv.getStyle().set("width", "40px");
        iconDiv.getStyle().set("height", "40px");
        iconDiv.getStyle().set("background", getCorFundoIcone(icone));
        iconDiv.getStyle().set("border-radius", "8px");
        iconDiv.getStyle().set("margin", "0 auto 12px auto");
        iconDiv.getStyle().set("display", "flex");
        iconDiv.getStyle().set("align-items", "center");
        iconDiv.getStyle().set("justify-content", "center");

        String svgContent = getSvgIcone(icone, cor);
        iconDiv.getElement().setProperty("innerHTML", svgContent);

        Span tituloSpan = new Span(titulo);
        tituloSpan.getStyle().set("display", "block");
        tituloSpan.getStyle().set("font-size", "14px");
        tituloSpan.getStyle().set("color", "#6B7280");
        tituloSpan.getStyle().set("margin-bottom", "8px");

        Span valorSpan = new Span(valor);
        valorSpan.getStyle().set("display", "block");
        valorSpan.getStyle().set("font-size", "28px");
        valorSpan.getStyle().set("font-weight", "700");
        valorSpan.getStyle().set("color", cor);

        card.add(iconDiv, tituloSpan, valorSpan);
        return card;
    }

    private String getCorFundoIcone(String tipoIcone) {
        return switch (tipoIcone) {
            case "stethoscope" -> "#EFF6FF";
            case "document" -> "#D1FAE5";
            case "pill" -> "#F3E8FF";
            case "calendar" -> "#FEF3C7";
            default -> "#F3F4F6";
        };
    }

    private String getSvgIcone(String tipoIcone, String cor) {
        String pathData = switch (tipoIcone) {
            case "stethoscope" -> "M14.828 14.828a4 4 0 01-5.656 0M9 10h1.586a1 1 0 01.707.293l.707.707A1 1 0 0012.414 11H13a1 1 0 011 1v3a1 1 0 01-1 1h-1a1 1 0 01-1-1v-1a1 1 0 00-1-1H9a1 1 0 00-1 1v1a1 1 0 01-1 1H6a1 1 0 01-1-1v-3a1 1 0 011-1h.586a1 1 0 00.707-.293l.707-.707A1 1 0 009.414 10H9a1 1 0 011-1zm4.828 5.828a4 4 0 005.656 0M13.5 17a6.5 6.5 0 100-13 6.5 6.5 0 000 13z";
            case "document" -> "M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z";
            case "pill" -> "M4.5 12.75l6 6 9-13.5";
            case "calendar" -> "M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z";
            default -> "";
        };

        return "<svg width=\"20\" height=\"20\" viewBox=\"0 0 24 24\" fill=\"none\" " +
               "style=\"stroke: " + cor + "; stroke-width: 2; stroke-linecap: round; stroke-linejoin: round;\">" +
               "<path d=\"" + pathData + "\"></path>" +
               "</svg>";
    }

    private String calcularUltimoAtendimento(List<Prontuario> prontuarios) {
        if (prontuarios.isEmpty()) {
            return "Nenhum";
        }

        return prontuarios.stream()
            .map(p -> p.getDataAtendimento().toLocalDate())
            .max(java.time.LocalDate::compareTo)
            .map(java.time.LocalDate::toString)
            .orElse("Nenhum");
    }

    private void mostrarDetalhesAtendimento(Prontuario prontuario) {
        Div detalhes = new Div();
        detalhes.setText("Detalhes do atendimento de " + prontuario.getDataAtendimento().toLocalDate());
        detalhes.getStyle().set("padding", "20px");
        detalhes.getStyle().set("background", "white");
        detalhes.getStyle().set("border-radius", "8px");

        if (prontuario.getAnamnese() != null) {
            Div anamneseDiv = new Div();
            anamneseDiv.setText("Anamnese: " + prontuario.getAnamnese());
            anamneseDiv.getStyle().set("margin-top", "16px");
            detalhes.add(anamneseDiv);
        }

        conteudoLayout.add(detalhes);
    }

    private void mostrarResultadoExame(Exame exame) {
        Div resultadoDiv = new Div();
        resultadoDiv.setText("Resultado do exame " + exame.getNomeExame() + ": " + exame.getResultado());
        resultadoDiv.getStyle().set("padding", "20px");
        resultadoDiv.getStyle().set("background", "white");
        resultadoDiv.getStyle().set("border-radius", "8px");

        conteudoLayout.add(resultadoDiv);
    }

    private void mostrarDetalhesPrescricao(Prescricao prescricao) {
        Div detalhesDiv = new Div();
        detalhesDiv.setText("Detalhes da prescrição");
        detalhesDiv.getStyle().set("padding", "20px");
        detalhesDiv.getStyle().set("background", "white");
        detalhesDiv.getStyle().set("border-radius", "8px");

        if (prescricao.getMedicamentos() != null) {
            Div medDiv = new Div();
            medDiv.setText("Medicamentos: " + prescricao.getMedicamentos());
            detalhesDiv.add(medDiv);
        }

        conteudoLayout.add(detalhesDiv);
    }
}
