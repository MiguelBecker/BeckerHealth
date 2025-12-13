package dev.beckerhealth.dominio.prontuario;

import static org.apache.commons.lang3.Validate.notNull;

import java.time.LocalDate;

public class Prescricao {
    private Long id;
    private Prontuario prontuario;
    private String medicamentos;
    private String posologia;
    private LocalDate dataEmissao;
    private LocalDate dataValidade;
    private String assinaturaMedica;
    private String observacoes;

    public Prescricao() {
    }

    public Prescricao(Long id, Prontuario prontuario, String medicamentos,
                     String posologia, LocalDate dataEmissao, LocalDate dataValidade,
                     String assinaturaMedica, String observacoes) {
        notNull(prontuario, "O prontuário não pode ser nulo");
        notNull(medicamentos, "Os medicamentos não podem ser nulos");
        notNull(dataEmissao, "A data de emissão não pode ser nula");
        notNull(dataValidade, "A data de validade não pode ser nula");
        notNull(assinaturaMedica, "A assinatura médica não pode ser nula");

        validarValidade(dataEmissao, dataValidade);

        this.id = id;
        this.prontuario = prontuario;
        this.medicamentos = medicamentos;
        this.posologia = posologia;
        this.dataEmissao = dataEmissao;
        this.dataValidade = dataValidade;
        this.assinaturaMedica = assinaturaMedica;
        this.observacoes = observacoes;
    }

    private void validarValidade(LocalDate emissao, LocalDate validade) {
        if (validade.isBefore(emissao)) {
            throw new IllegalArgumentException("A data de validade deve ser posterior à data de emissão");
        }
    }

    // Getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Prontuario getProntuario() { return prontuario; }
    public void setProntuario(Prontuario prontuario) {
        notNull(prontuario, "O prontuário não pode ser nulo");
        this.prontuario = prontuario;
    }

    public String getMedicamentos() { return medicamentos; }
    public void setMedicamentos(String medicamentos) {
        notNull(medicamentos, "Os medicamentos não podem ser nulos");
        this.medicamentos = medicamentos;
    }

    public String getPosologia() { return posologia; }
    public void setPosologia(String posologia) { this.posologia = posologia; }

    public LocalDate getDataEmissao() { return dataEmissao; }
    public void setDataEmissao(LocalDate dataEmissao) {
        notNull(dataEmissao, "A data de emissão não pode ser nula");
        this.dataEmissao = dataEmissao;
    }

    public LocalDate getDataValidade() { return dataValidade; }
    public void setDataValidade(LocalDate dataValidade) {
        notNull(dataValidade, "A data de validade não pode ser nula");
        validarValidade(this.dataEmissao, dataValidade);
        this.dataValidade = dataValidade;
    }

    public String getAssinaturaMedica() { return assinaturaMedica; }
    public void setAssinaturaMedica(String assinaturaMedica) {
        notNull(assinaturaMedica, "A assinatura médica não pode ser nula");
        this.assinaturaMedica = assinaturaMedica;
    }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public boolean isValida() {
        return LocalDate.now().isBefore(dataValidade) || LocalDate.now().isEqual(dataValidade);
    }
}
