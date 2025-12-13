package dev.beckerhealth.dominio.prontuario;

import static org.apache.commons.lang3.Validate.notNull;

import java.time.LocalDate;

public class Prescricao {
    private Long id;
    private Long prontuarioId;
    private String conteudo;
    private LocalDate validade;
    private String assinaturaMedica;

    public Prescricao() {
    }

    public Prescricao(Long id, Long prontuarioId, String conteudo, LocalDate validade, String assinaturaMedica) {
        notNull(prontuarioId, "O prontuário não pode ser nulo");
        notNull(conteudo, "O conteúdo da prescrição não pode ser nulo");
        notNull(validade, "A validade é obrigatória");
        notNull(assinaturaMedica, "A assinatura médica é obrigatória");
        
        this.id = id;
        this.prontuarioId = prontuarioId;
        this.conteudo = conteudo;
        this.validade = validade;
        this.assinaturaMedica = assinaturaMedica;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProntuarioId() {
        return prontuarioId;
    }

    public void setProntuarioId(Long prontuarioId) {
        notNull(prontuarioId, "O prontuário não pode ser nulo");
        this.prontuarioId = prontuarioId;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        notNull(conteudo, "O conteúdo da prescrição não pode ser nulo");
        this.conteudo = conteudo;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        notNull(validade, "A validade é obrigatória");
        this.validade = validade;
    }

    public String getAssinaturaMedica() {
        return assinaturaMedica;
    }

    public void setAssinaturaMedica(String assinaturaMedica) {
        notNull(assinaturaMedica, "A assinatura médica é obrigatória");
        this.assinaturaMedica = assinaturaMedica;
    }

    public boolean isValida() {
        return validade != null && validade.isAfter(LocalDate.now()) || validade.isEqual(LocalDate.now());
    }
}

