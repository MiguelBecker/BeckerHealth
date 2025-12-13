package dev.beckerhealth.apresentacao.vaadin.medico;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import dev.beckerhealth.dominio.compartilhado.usuario.Cpf;
import dev.beckerhealth.dominio.compartilhado.usuario.Crm;
import dev.beckerhealth.dominio.compartilhado.usuario.Email;
import dev.beckerhealth.dominio.compartilhado.usuario.Medico;
import dev.beckerhealth.dominio.compartilhado.usuario.Paciente;
import dev.beckerhealth.dominio.compartilhado.usuario.Usuario;
import dev.beckerhealth.dominio.prontuario.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@PageTitle("Prontuários - Médico")
@Route("medico/prontuarios")
public class ProntuarioMedicoView extends VerticalLayout {

    private final ProntuarioService prontuarioService;
    private VerticalLayout conteudoLayout;
    private Tabs tabs;
    private Paciente pacienteSelecionado;

    // Dados mockados para demonstração
    private final List<Paciente> pacientesMock = Arrays.asList(
        new Paciente(4L, "paciente1", "senha", "Carlos Oliveira", 
                    new Email("carlos.oliveira@email.com"), 
                    Usuario.TipoUsuario.PACIENTE, 
                    new Cpf("12345678901"), 
                    LocalDate.of(1980, 5, 15), 
                    "Unimed"),
        new Paciente(5L, "paciente2", "senha", "Ana Costa", 
                    new Email("ana.costa@email.com"), 
                    Usuario.TipoUsuario.PACIENTE, 
                    new Cpf("98765432109"), 
                    LocalDate.of(1990, 8, 20), 
                    "SulAmérica")
    );

    public ProntuarioMedicoView(ProntuarioService prontuarioService) {
        this.prontuarioService = prontuarioService;

        setPadding(false);
        setSpacing(false);
        setSizeFull();
        getStyle().set("background", "#F9FAFB");

        add(criarHeader(), criarSelecaoPaciente(), criarAbas(), criarConteudo());
    }

    private HeaderMedico criarHeader() {
        return new HeaderMedico();
    }

    private HorizontalLayout criarSelecaoPaciente() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidthFull();
        layout.setPadding(true);
        layout.getStyle().set("background", "white");
        layout.getStyle().set("margin-bottom", "16px");
        layout.getStyle().set("border-radius", "8px");
        layout.getStyle().set("box-shadow", "0 1px 3px rgba(0,0,0,0.1)");

        Span label = new Span("Selecionar Paciente: ");
        label.getStyle().set("font-weight", "600");
        label.getStyle().set("color", "#374151");

        ComboBox<Paciente> pacienteCombo = new ComboBox<>();
        pacienteCombo.setItems(pacientesMock);
        pacienteCombo.setItemLabelGenerator(paciente -> paciente.getNome());
        pacienteCombo.setPlaceholder("Escolha um paciente");
        pacienteCombo.setWidth("300px");
        pacienteCombo.addValueChangeListener(e -> {
            pacienteSelecionado = e.getValue();
            atualizarConteudo();
        });

        layout.add(label, pacienteCombo);
        layout.setAlignItems(Alignment.CENTER);

        return layout;
    }

    private Tabs criarAbas() {
        tabs = new Tabs();
        tabs.setWidthFull();
        tabs.getStyle().set("background", "white");
        tabs.getStyle().set("border-bottom", "1px solid #E5E7EB");

        Tab atendimentos = new Tab("Atendimentos");
        Tab exames = new Tab("Exames");
        Tab prescricoes = new Tab("Prescrições");
        Tab historico = new Tab("Histórico Completo");

        tabs.add(atendimentos, exames, prescricoes, historico);
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
        if (pacienteSelecionado == null) {
            conteudoLayout.removeAll();
            Div mensagem = new Div();
            mensagem.setText("Selecione um paciente para visualizar os prontuários");
            mensagem.getStyle().set("text-align", "center");
            mensagem.getStyle().set("padding", "40px");
            mensagem.getStyle().set("color", "#6B7280");
            conteudoLayout.add(mensagem);
            return;
        }

        conteudoLayout.removeAll();

        Tab abaSelecionada = tabs.getSelectedTab();
        if (abaSelecionada.getLabel().equals("Atendimentos")) {
            atualizarConteudoAtendimentos();
        } else if (abaSelecionada.getLabel().equals("Exames")) {
            atualizarConteudoExames();
        } else if (abaSelecionada.getLabel().equals("Prescrições")) {
            atualizarConteudoPrescricoes();
        } else if (abaSelecionada.getLabel().equals("Histórico Completo")) {
            atualizarConteudoHistorico();
        }
    }

    private void atualizarConteudoAtendimentos() {
        if (pacienteSelecionado == null) return;

        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setWidthFull();

        // Cabeçalho com botão de novo atendimento
        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.setWidthFull();
        headerLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        headerLayout.setAlignItems(Alignment.CENTER);

        H3 titulo = new H3("Atendimentos de " + pacienteSelecionado.getNome());
        titulo.getStyle().set("margin", "0");

        Button novoAtendimentoButton = new Button("Novo Atendimento");
        novoAtendimentoButton.getStyle().set("background", "#3B82F6");
        novoAtendimentoButton.getStyle().set("color", "white");
        novoAtendimentoButton.addClickListener(e -> abrirDialogNovoAtendimento());

        headerLayout.add(titulo, novoAtendimentoButton);

        // Lista de atendimentos
        Grid<Prontuario> grid = new Grid<>(Prontuario.class, false);
        grid.addColumn(prontuario -> prontuario.getDataAtendimento().toLocalDate())
            .setHeader("Data")
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

        List<Prontuario> prontuarios = prontuarioService.buscarPorPaciente(pacienteSelecionado.getId());
        grid.setItems(prontuarios);

        grid.addItemClickListener(event -> abrirDialogEditarAtendimento(event.getItem()));

        layout.add(headerLayout, grid);
        conteudoLayout.add(layout);
    }

    private void atualizarConteudoExames() {
        if (pacienteSelecionado == null) return;

        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setWidthFull();

        // Cabeçalho com botão de solicitar exame
        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.setWidthFull();
        headerLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        headerLayout.setAlignItems(Alignment.CENTER);

        H3 titulo = new H3("Exames de " + pacienteSelecionado.getNome());
        titulo.getStyle().set("margin", "0");

        Button solicitarExameButton = new Button("Solicitar Exame");
        solicitarExameButton.getStyle().set("background", "#10B981");
        solicitarExameButton.getStyle().set("color", "white");
        solicitarExameButton.addClickListener(e -> abrirDialogSolicitarExame());

        headerLayout.add(titulo, solicitarExameButton);

        // Lista de exames
        Grid<Exame> grid = new Grid<>(Exame.class, false);
        grid.addColumn(Exame::getNomeExame).setHeader("Exame");
        grid.addColumn(exame -> exame.getDataSolicitacao().toLocalDate()).setHeader("Solicitado em");
        grid.addColumn(exame -> exame.getStatus().name()).setHeader("Status");
        grid.addColumn(exame -> exame.getResultado() != null ? "Liberado" : "Pendente").setHeader("Resultado");

        List<Exame> exames = prontuarioService.listarExames();
        // Filtrar exames do paciente selecionado
        List<Exame> examesPaciente = exames.stream()
            .filter(exame -> exame.getProntuario() != null &&
                           exame.getProntuario().getPaciente().getId().equals(pacienteSelecionado.getId()))
            .toList();
        grid.setItems(examesPaciente);

        grid.addItemClickListener(event -> {
            if (event.getItem().getStatus() == Exame.StatusExame.LIBERADO) {
                mostrarResultadoExame(event.getItem());
            } else {
                abrirDialogLiberarResultado(event.getItem());
            }
        });

        layout.add(headerLayout, grid);
        conteudoLayout.add(layout);
    }

    private void atualizarConteudoPrescricoes() {
        if (pacienteSelecionado == null) return;

        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setWidthFull();

        // Cabeçalho com botão de nova prescrição
        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.setWidthFull();
        headerLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        headerLayout.setAlignItems(Alignment.CENTER);

        H3 titulo = new H3("Prescrições de " + pacienteSelecionado.getNome());
        titulo.getStyle().set("margin", "0");

        Button novaPrescricaoButton = new Button("Nova Prescrição");
        novaPrescricaoButton.getStyle().set("background", "#8B5CF6");
        novaPrescricaoButton.getStyle().set("color", "white");
        novaPrescricaoButton.addClickListener(e -> abrirDialogNovaPrescricao());

        headerLayout.add(titulo, novaPrescricaoButton);

        // Lista de prescrições
        Grid<Prescricao> grid = new Grid<>(Prescricao.class, false);
        grid.addColumn(Prescricao::getMedicamentos).setHeader("Medicamentos");
        grid.addColumn(Prescricao::getPosologia).setHeader("Posologia");
        grid.addColumn(prescricao -> prescricao.getDataEmissao().toString()).setHeader("Emissão");
        grid.addColumn(prescricao -> prescricao.getDataValidade().toString()).setHeader("Validade");
        grid.addColumn(prescricao -> prescricao.isValida() ? "Válida" : "Expirada").setHeader("Status");

        List<Prescricao> prescricoes = prontuarioService.listarPrescricoes();
        // Filtrar prescrições do paciente selecionado
        List<Prescricao> prescricoesPaciente = prescricoes.stream()
            .filter(prescricao -> prescricao.getProntuario() != null &&
                                prescricao.getProntuario().getPaciente().getId().equals(pacienteSelecionado.getId()))
            .toList();
        grid.setItems(prescricoesPaciente);

        layout.add(headerLayout, grid);
        conteudoLayout.add(layout);
    }

    private void atualizarConteudoHistorico() {
        if (pacienteSelecionado == null) return;

        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setWidthFull();

        H3 titulo = new H3("Histórico Completo de " + pacienteSelecionado.getNome());
        titulo.getStyle().set("margin-bottom", "24px");

        // Estatísticas rápidas
        HorizontalLayout statsLayout = new HorizontalLayout();
        statsLayout.setWidthFull();
        statsLayout.setSpacing(true);

        List<Prontuario> prontuarios = prontuarioService.buscarPorPaciente(pacienteSelecionado.getId());
        List<Exame> exames = prontuarioService.listarExames().stream()
            .filter(exame -> exame.getProntuario() != null &&
                           exame.getProntuario().getPaciente().getId().equals(pacienteSelecionado.getId()))
            .toList();
        List<Prescricao> prescricoes = prontuarioService.listarPrescricoes().stream()
            .filter(prescricao -> prescricao.getProntuario() != null &&
                                prescricao.getProntuario().getPaciente().getId().equals(pacienteSelecionado.getId()))
            .toList();

        Div stat1 = criarCardEstatistica("Atendimentos", String.valueOf(prontuarios.size()), "#3B82F6");
        Div stat2 = criarCardEstatistica("Exames", String.valueOf(exames.size()), "#10B981");
        Div stat3 = criarCardEstatistica("Prescrições", String.valueOf(prescricoes.size()), "#8B5CF6");

        statsLayout.add(stat1, stat2, stat3);

        // Timeline de atividades
        Div timelineDiv = new Div();
        timelineDiv.setText("Timeline de atividades médicas será implementada aqui");
        timelineDiv.getStyle().set("padding", "20px");
        timelineDiv.getStyle().set("background", "white");
        timelineDiv.getStyle().set("border-radius", "8px");
        timelineDiv.getStyle().set("margin-top", "24px");

        layout.add(titulo, statsLayout, timelineDiv);
        conteudoLayout.add(layout);
    }

    private Div criarCardEstatistica(String titulo, String valor, String cor) {
        Div card = new Div();
        card.getStyle().set("background", "white");
        card.getStyle().set("border-radius", "8px");
        card.getStyle().set("padding", "20px");
        card.getStyle().set("box-shadow", "0 1px 3px rgba(0,0,0,0.1)");
        card.getStyle().set("flex", "1");
        card.getStyle().set("text-align", "center");

        Span tituloSpan = new Span(titulo);
        tituloSpan.getStyle().set("display", "block");
        tituloSpan.getStyle().set("font-size", "14px");
        tituloSpan.getStyle().set("color", "#6B7280");
        tituloSpan.getStyle().set("margin-bottom", "8px");

        Span valorSpan = new Span(valor);
        valorSpan.getStyle().set("display", "block");
        valorSpan.getStyle().set("font-size", "32px");
        valorSpan.getStyle().set("font-weight", "700");
        valorSpan.getStyle().set("color", cor);

        card.add(tituloSpan, valorSpan);
        return card;
    }

    private void abrirDialogNovoAtendimento() {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Novo Atendimento");
        dialog.setWidth("800px");
        dialog.setMaxHeight("80vh");

        VerticalLayout content = new VerticalLayout();
        content.setPadding(true);
        content.setSpacing(true);
        content.setWidthFull();

        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();

        // Campos do atendimento
        ComboBox<Prontuario.TipoAtendimento> tipoCombo = new ComboBox<>("Tipo de Atendimento");
        tipoCombo.setItems(Prontuario.TipoAtendimento.values());
        tipoCombo.setItemLabelGenerator(Enum::name);
        tipoCombo.setValue(Prontuario.TipoAtendimento.CONSULTA);

        TextArea anamneseField = new TextArea("Anamnese");
        anamneseField.setWidthFull();
        anamneseField.setHeight("100px");
        anamneseField.setPlaceholder("Descreva os sintomas, queixas e histórico do paciente...");

        TextArea diagnosticoField = new TextArea("Diagnóstico");
        diagnosticoField.setWidthFull();
        diagnosticoField.setHeight("80px");
        diagnosticoField.setPlaceholder("Registre o diagnóstico médico...");

        TextArea prescricaoField = new TextArea("Prescrição");
        prescricaoField.setWidthFull();
        prescricaoField.setHeight("80px");
        prescricaoField.setPlaceholder("Medicamentos e orientações...");

        TextArea observacoesField = new TextArea("Observações");
        observacoesField.setWidthFull();
        observacoesField.setHeight("60px");

        formLayout.add(tipoCombo, anamneseField, diagnosticoField, prescricaoField, observacoesField);

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.setWidthFull();
        buttonsLayout.setJustifyContentMode(JustifyContentMode.END);
        buttonsLayout.setSpacing(true);

        Button cancelarButton = new Button("Cancelar");
        cancelarButton.addClickListener(e -> dialog.close());

        Button salvarButton = new Button("Salvar Atendimento");
        salvarButton.getStyle().set("background", "#3B82F6");
        salvarButton.getStyle().set("color", "white");
        salvarButton.addClickListener(e -> {
            salvarNovoAtendimento(tipoCombo.getValue(), anamneseField.getValue(),
                               diagnosticoField.getValue(), prescricaoField.getValue(),
                               observacoesField.getValue());
            dialog.close();
        });

        buttonsLayout.add(cancelarButton, salvarButton);

        content.add(formLayout, buttonsLayout);
        dialog.add(content);
        dialog.open();
    }

    private void abrirDialogEditarAtendimento(Prontuario prontuario) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Editar Atendimento");
        dialog.setWidth("800px");

        VerticalLayout content = new VerticalLayout();
        content.setPadding(true);
        content.setSpacing(true);

        // Implementar formulário de edição
        Div placeholder = new Div();
        placeholder.setText("Formulário de edição será implementado");
        content.add(placeholder);

        Button fecharButton = new Button("Fechar");
        fecharButton.addClickListener(e -> dialog.close());
        content.add(fecharButton);

        dialog.add(content);
        dialog.open();
    }

    private void abrirDialogSolicitarExame() {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Solicitar Exame");
        dialog.setWidth("600px");

        VerticalLayout content = new VerticalLayout();
        content.setPadding(true);
        content.setSpacing(true);

        TextField nomeExameField = new TextField("Nome do Exame");
        nomeExameField.setWidthFull();

        TextArea descricaoField = new TextArea("Descrição/Observações");
        descricaoField.setWidthFull();
        descricaoField.setHeight("80px");

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.setWidthFull();
        buttonsLayout.setJustifyContentMode(JustifyContentMode.END);

        Button cancelarButton = new Button("Cancelar");
        cancelarButton.addClickListener(e -> dialog.close());

        Button solicitarButton = new Button("Solicitar");
        solicitarButton.getStyle().set("background", "#10B981");
        solicitarButton.getStyle().set("color", "white");
        solicitarButton.addClickListener(e -> {
            solicitarExame(nomeExameField.getValue(), descricaoField.getValue());
            dialog.close();
        });

        buttonsLayout.add(cancelarButton, solicitarButton);

        content.add(nomeExameField, descricaoField, buttonsLayout);
        dialog.add(content);
        dialog.open();
    }

    private void abrirDialogLiberarResultado(Exame exame) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Liberar Resultado - " + exame.getNomeExame());
        dialog.setWidth("600px");

        VerticalLayout content = new VerticalLayout();
        content.setPadding(true);
        content.setSpacing(true);

        TextArea resultadoField = new TextArea("Resultado");
        resultadoField.setWidthFull();
        resultadoField.setHeight("120px");
        resultadoField.setPlaceholder("Digite o resultado do exame...");

        TextArea observacoesField = new TextArea("Observações Médicas");
        observacoesField.setWidthFull();
        observacoesField.setHeight("80px");

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.setWidthFull();
        buttonsLayout.setJustifyContentMode(JustifyContentMode.END);

        Button cancelarButton = new Button("Cancelar");
        cancelarButton.addClickListener(e -> dialog.close());

        Button liberarButton = new Button("Liberar Resultado");
        liberarButton.getStyle().set("background", "#10B981");
        liberarButton.getStyle().set("color", "white");
        liberarButton.addClickListener(e -> {
            liberarResultadoExame(exame, resultadoField.getValue(), observacoesField.getValue());
            dialog.close();
        });

        buttonsLayout.add(cancelarButton, liberarButton);

        content.add(resultadoField, observacoesField, buttonsLayout);
        dialog.add(content);
        dialog.open();
    }

    private void mostrarResultadoExame(Exame exame) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Resultado - " + exame.getNomeExame());
        dialog.setWidth("600px");

        VerticalLayout content = new VerticalLayout();
        content.setPadding(true);
        content.setSpacing(true);

        Div resultadoDiv = new Div();
        resultadoDiv.setText("Resultado: " + exame.getResultado());
        resultadoDiv.getStyle().set("background", "#F0FDF4");
        resultadoDiv.getStyle().set("padding", "16px");
        resultadoDiv.getStyle().set("border-radius", "8px");
        resultadoDiv.getStyle().set("border", "1px solid #D1FAE5");

        if (exame.getObservacoesMedico() != null && !exame.getObservacoesMedico().isEmpty()) {
            Div obsDiv = new Div();
            obsDiv.setText("Observações: " + exame.getObservacoesMedico());
            obsDiv.getStyle().set("margin-top", "16px");
            content.add(obsDiv);
        }

        Button fecharButton = new Button("Fechar");
        fecharButton.addClickListener(e -> dialog.close());

        content.add(resultadoDiv, fecharButton);
        dialog.add(content);
        dialog.open();
    }

    private void abrirDialogNovaPrescricao() {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Nova Prescrição");
        dialog.setWidth("700px");

        VerticalLayout content = new VerticalLayout();
        content.setPadding(true);
        content.setSpacing(true);

        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();

        TextArea medicamentosField = new TextArea("Medicamentos");
        medicamentosField.setWidthFull();
        medicamentosField.setHeight("100px");
        medicamentosField.setPlaceholder("Liste os medicamentos prescritos...");

        TextArea posologiaField = new TextArea("Posologia");
        posologiaField.setWidthFull();
        posologiaField.setHeight("80px");
        posologiaField.setPlaceholder("Instruções de uso...");

        DatePicker validadeField = new DatePicker("Data de Validade");
        validadeField.setMin(LocalDate.now());

        TextArea observacoesField = new TextArea("Observações");
        observacoesField.setWidthFull();
        observacoesField.setHeight("60px");

        formLayout.add(medicamentosField, posologiaField, validadeField, observacoesField);

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.setWidthFull();
        buttonsLayout.setJustifyContentMode(JustifyContentMode.END);

        Button cancelarButton = new Button("Cancelar");
        cancelarButton.addClickListener(e -> dialog.close());

        Button criarButton = new Button("Criar Prescrição");
        criarButton.getStyle().set("background", "#8B5CF6");
        criarButton.getStyle().set("color", "white");
        criarButton.addClickListener(e -> {
            criarPrescricao(medicamentosField.getValue(), posologiaField.getValue(),
                          validadeField.getValue(), observacoesField.getValue());
            dialog.close();
        });

        buttonsLayout.add(cancelarButton, criarButton);

        content.add(formLayout, buttonsLayout);
        dialog.add(content);
        dialog.open();
    }

    private void salvarNovoAtendimento(Prontuario.TipoAtendimento tipo, String anamnese,
                                     String diagnostico, String prescricao, String observacoes) {
        try {
            Prontuario novoProntuario = new Prontuario();
            novoProntuario.setPaciente(pacienteSelecionado);
            novoProntuario.setAnamnese(anamnese);
            novoProntuario.setDiagnostico(diagnostico);
            novoProntuario.setPrescricao(prescricao);
            novoProntuario.setTipoAtendimento(tipo);
            novoProntuario.setObservacoes(observacoes);

            // Médico mockado para demonstração
            Medico medico = new Medico();
            medico.setId(2L);
            medico.setLogin("medico1");
            medico.setSenha("senha");
            medico.setNome("Dr. João Silva");
            medico.setEmail(new Email("joao.silva@email.com"));
            medico.setTipo(Usuario.TipoUsuario.MEDICO);
            medico.setCrm(new Crm("12345"));
            medico.setEspecialidade("Cardiologia");
            novoProntuario.setMedicoResponsavel(medico);

            prontuarioService.criarProntuario(novoProntuario);

            Notification.show("Atendimento salvo com sucesso!", 3000, Notification.Position.TOP_CENTER);
            atualizarConteudo();
        } catch (Exception e) {
            Notification.show("Erro ao salvar atendimento: " + e.getMessage(), 5000, Notification.Position.TOP_CENTER);
        }
    }

    private void solicitarExame(String nomeExame, String descricao) {
        try {
            // Criar um prontuário mockado para o exame
            Prontuario prontuarioMock = new Prontuario();
            prontuarioMock.setId(1L);
            prontuarioMock.setPaciente(pacienteSelecionado);

            // Usar ID de consulta mockada
            Long consultaIdMock = 1L;

            Exame exame = new Exame(null, prontuarioMock, consultaIdMock, nomeExame,
                                  descricao, LocalDateTime.now(), null, null, null, null);

            prontuarioService.solicitarExame(exame);

            Notification.show("Exame solicitado com sucesso!", 3000, Notification.Position.TOP_CENTER);
            atualizarConteudo();
        } catch (Exception e) {
            Notification.show("Erro ao solicitar exame: " + e.getMessage(), 5000, Notification.Position.TOP_CENTER);
        }
    }

    private void liberarResultadoExame(Exame exame, String resultado, String observacoes) {
        try {
            prontuarioService.liberarResultadoExame(exame.getId(), resultado, observacoes);
            Notification.show("Resultado liberado com sucesso!", 3000, Notification.Position.TOP_CENTER);
            atualizarConteudo();
        } catch (Exception e) {
            Notification.show("Erro ao liberar resultado: " + e.getMessage(), 5000, Notification.Position.TOP_CENTER);
        }
    }

    private void criarPrescricao(String medicamentos, String posologia, LocalDate validade, String observacoes) {
        try {
            // Criar um prontuário mockado para a prescrição
            Prontuario prontuarioMock = new Prontuario();
            prontuarioMock.setId(1L);
            prontuarioMock.setPaciente(pacienteSelecionado);

            Prescricao prescricao = new Prescricao(null, prontuarioMock, medicamentos,
                                                 posologia, LocalDate.now(), validade,
                                                 "Dr. João Silva", observacoes);

            prontuarioService.criarPrescricao(prescricao);

            Notification.show("Prescrição criada com sucesso!", 3000, Notification.Position.TOP_CENTER);
            atualizarConteudo();
        } catch (Exception e) {
            Notification.show("Erro ao criar prescrição: " + e.getMessage(), 5000, Notification.Position.TOP_CENTER);
        }
    }
}
