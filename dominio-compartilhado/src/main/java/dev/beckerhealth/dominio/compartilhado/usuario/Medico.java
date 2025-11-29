package dev.beckerhealth.dominio.compartilhado.usuario;

import static org.apache.commons.lang3.Validate.notNull;

public class Medico extends Usuario {
    private Crm crm;
    private String especialidade;

    public Medico() {
        super();
    }

    public Medico(Long id, String login, String senha, String nome, Email email, 
                   TipoUsuario tipo, Crm crm, String especialidade) {
        super(id, login, senha, nome, email, tipo);
        notNull(crm, "O CRM não pode ser nulo");
        notNull(especialidade, "A especialidade não pode ser nula");
        this.crm = crm;
        this.especialidade = especialidade;
    }

    public Crm getCrm() {
        return crm;
    }

    public void setCrm(Crm crm) {
        notNull(crm, "O CRM não pode ser nulo");
        this.crm = crm;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        notNull(especialidade, "A especialidade não pode ser nula");
        this.especialidade = especialidade;
    }
}

