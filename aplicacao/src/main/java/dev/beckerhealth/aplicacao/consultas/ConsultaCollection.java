package dev.beckerhealth.aplicacao.consultas;

import dev.beckerhealth.dominio.consultas.Consulta;

import java.util.Iterator;
import java.util.List;

public class ConsultaCollection implements Iterable<Consulta> {
    private final List<Consulta> consultas;

    public ConsultaCollection(List<Consulta> consultas) {
        this.consultas = consultas;
    }

    @Override
    public Iterator<Consulta> iterator() {
        return new ConsultaIterator(consultas);
    }

    public Iterator<Consulta> iteratorFiltrado(Consulta.StatusConsulta status) {
        List<Consulta> filtradas = consultas.stream()
                .filter(c -> c.getStatus() == status)
                .toList();
        return new ConsultaIterator(filtradas);
    }
}

