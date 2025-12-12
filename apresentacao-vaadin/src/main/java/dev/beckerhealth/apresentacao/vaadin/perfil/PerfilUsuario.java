package dev.beckerhealth.apresentacao.vaadin.perfil;

import java.util.List;

public enum PerfilUsuario {
    PACIENTE("Paciente", "Área do paciente", "#10B981", 
            List.of("Agendar consultas", "Ver prontuário", "Resultados de exames", "Notificações"),
            "person"),
    MEDICO("Médico", "Área médica", "#3B82F6",
            List.of("Gerenciar agenda", "Prontuários médicos", "Prescrições digitais", "Solicitar exames"),
            "stethoscope"),
    ADMINISTRADOR("Administrador", "Área administrativa", "#8B5CF6",
            List.of("Relatórios gerenciais", "Gestão de usuários", "Dashboard analytics", "Controle financeiro"),
            "shield");

    private final String titulo;
    private final String subtitulo;
    private final String cor;
    private final List<String> funcionalidades;
    private final String tipoIcone;

    PerfilUsuario(String titulo, String subtitulo, String cor, List<String> funcionalidades, String tipoIcone) {
        this.titulo = titulo;
        this.subtitulo = subtitulo;
        this.cor = cor;
        this.funcionalidades = funcionalidades;
        this.tipoIcone = tipoIcone;
    }

    public String getTitulo() { return titulo; }
    public String getSubtitulo() { return subtitulo; }
    public String getCor() { return cor; }
    public List<String> getFuncionalidades() { return funcionalidades; }
    public String getTextoBotao() { return "Acessar como " + (titulo.equals("Administrador") ? "Admin" : titulo); }
    public String getTipoIcone() { return tipoIcone; }
}

