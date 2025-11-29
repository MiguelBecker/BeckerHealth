package dev.beckerhealth.dominio.compartilhado.usuario;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

public class Usuario {
    private Long id;
    private String login;
    private String senha;
    private String nome;
    private Email email;
    private TipoUsuario tipo;

    public Usuario() {
    }

    public Usuario(Long id, String login, String senha, String nome, Email email, TipoUsuario tipo) {
        notNull(login, "O login não pode ser nulo");
        notBlank(login, "O login não pode estar em branco");
        notNull(senha, "A senha não pode ser nula");
        notBlank(senha, "A senha não pode estar em branco");
        notNull(nome, "O nome não pode ser nulo");
        notBlank(nome, "O nome não pode estar em branco");
        notNull(tipo, "O tipo não pode ser nulo");
        
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.email = email;
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        notNull(login, "O login não pode ser nulo");
        notBlank(login, "O login não pode estar em branco");
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        notNull(senha, "A senha não pode ser nula");
        notBlank(senha, "A senha não pode estar em branco");
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        notNull(nome, "O nome não pode ser nulo");
        notBlank(nome, "O nome não pode estar em branco");
        this.nome = nome;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        notNull(tipo, "O tipo não pode ser nulo");
        this.tipo = tipo;
    }

    public enum TipoUsuario {
        ADMIN, MEDICO, PACIENTE, RECEPCIONISTA
    }
}

