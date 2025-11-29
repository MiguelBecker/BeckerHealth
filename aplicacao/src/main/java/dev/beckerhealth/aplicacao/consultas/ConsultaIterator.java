package dev.beckerhealth.aplicacao.consultas;

import dev.beckerhealth.dominio.consultas.Consulta;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class ConsultaIterator implements Iterator<Consulta> {
    private final List<Consulta> consultas;
    private int posicaoAtual;

    public ConsultaIterator(List<Consulta> consultas) {
        this.consultas = consultas;
        this.posicaoAtual = 0;
    }

    @Override
    public boolean hasNext() {
        return posicaoAtual < consultas.size();
    }

    @Override
    public Consulta next() {
        if (!hasNext()) {
            throw new NoSuchElementException("Não há mais consultas para iterar");
        }
        return consultas.get(posicaoAtual++);
    }

    public Consulta peek() {
        if (!hasNext()) {
            throw new NoSuchElementException("Não há mais consultas para visualizar");
        }
        return consultas.get(posicaoAtual);
    }

    public void reset() {
        posicaoAtual = 0;
    }
}

