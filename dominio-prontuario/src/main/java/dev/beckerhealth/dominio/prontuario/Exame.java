package dev.beckerhealth.dominio.prontuario;

import static org.apache.commons.lang3.Validate.notNull;

import dev.beckerhealth.dominio.consultas.ConsultaId;

import java.time.LocalDate;

public class Exame {
    private Long id;
    private ConsultaId consultaId;
    private String tipo;
    private String resultado;
    private LocalDate dataExame;
    private boolean liberado;

    public Exame() {
    }

    public Exame(Long id, ConsultaId consultaId, String tipo, String resultado, 
                 LocalDate dataExame, boolean liberado) {
        notNull(consultaId, "A consulta não pode ser nula");
        notNull(tipo, "O tipo do exame não pode ser nulo");
        
        this.id = id;
        this.consultaId = consultaId;
        this.tipo = tipo;
        this.resultado = resultado;
        this.dataExame = dataExame;
        this.liberado = liberado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ConsultaId getConsultaId() {
        return consultaId;
    }

    public void setConsultaId(ConsultaId consultaId) {
        notNull(consultaId, "A consulta não pode ser nula");
        this.consultaId = consultaId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        notNull(tipo, "O tipo do exame não pode ser nulo");
        this.tipo = tipo;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public LocalDate getDataExame() {
        return dataExame;
    }

    public void setDataExame(LocalDate dataExame) {
        this.dataExame = dataExame;
    }

    public boolean isLiberado() {
        return liberado;
    }

    public void setLiberado(boolean liberado) {
        this.liberado = liberado;
    }
}

