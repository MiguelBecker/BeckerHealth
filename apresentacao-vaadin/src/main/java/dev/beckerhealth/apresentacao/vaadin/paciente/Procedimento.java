package dev.beckerhealth.apresentacao.vaadin.paciente;

import java.math.BigDecimal;

public enum Procedimento {
    CHECKUP("Exame de checkup", new BigDecimal("150.00")),
    LIMPEZA_DENTE("Limpeza de dente", new BigDecimal("80.00")),
    BOTOX("Botox", new BigDecimal("300.00")),
    CONSULTA_ROTINA("Consulta de rotina", new BigDecimal("120.00")),
    CONSULTA_RETORNO("Consulta de retorno", new BigDecimal("100.00")),
    EXAME_SANGUE("Exame de sangue", new BigDecimal("50.00")),
    ULTRASSOM("Ultrassom", new BigDecimal("200.00")),
    RAIOS_X("Raio-X", new BigDecimal("150.00"));

    private final String descricao;
    private final BigDecimal valor;

    Procedimento(String descricao, BigDecimal valor) {
        this.descricao = descricao;
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }
}


