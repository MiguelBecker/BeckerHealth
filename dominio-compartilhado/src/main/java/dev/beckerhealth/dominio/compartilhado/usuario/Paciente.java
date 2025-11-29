package dev.beckerhealth.dominio.compartilhado.usuario;

import static org.apache.commons.lang3.Validate.notNull;

import java.time.LocalDate;

public class Paciente extends Usuario {
    private Cpf cpf;
    private LocalDate dataNascimento;
    private String convenio;

    public Paciente() {
        super();
    }

    public Paciente(Long id, String login, String senha, String nome, Email email,
                    TipoUsuario tipo, Cpf cpf, LocalDate dataNascimento, String convenio) {
        super(id, login, senha, nome, email, tipo);
        notNull(cpf, "O CPF não pode ser nulo");
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.convenio = convenio;
    }

    public Cpf getCpf() {
        return cpf;
    }

    public void setCpf(Cpf cpf) {
        notNull(cpf, "O CPF não pode ser nulo");
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getConvenio() {
        return convenio;
    }

    public void setConvenio(String convenio) {
        this.convenio = convenio;
    }
}

