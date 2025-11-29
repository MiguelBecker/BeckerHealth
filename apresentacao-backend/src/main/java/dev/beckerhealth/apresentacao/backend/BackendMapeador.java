package dev.beckerhealth.apresentacao.backend;

import dev.beckerhealth.apresentacao.backend.consultas.AgendarConsultaForm;
import dev.beckerhealth.dominio.compartilhado.usuario.*;
import dev.beckerhealth.dominio.consultas.Consulta;
import dev.beckerhealth.dominio.consultas.ConsultaId;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BackendMapeador extends ModelMapper {
    private CpfFabrica cpfFabrica;
    private CrmFabrica crmFabrica;
    private EmailFabrica emailFabrica;

    BackendMapeador() {
        cpfFabrica = new CpfFabrica();
        crmFabrica = new CrmFabrica();
        emailFabrica = new EmailFabrica();

        addConverter(new AbstractConverter<String, Cpf>() {
            @Override
            protected Cpf convert(String source) {
                return source != null ? cpfFabrica.construir(source) : null;
            }
        });

        addConverter(new AbstractConverter<String, Crm>() {
            @Override
            protected Crm convert(String source) {
                return source != null ? crmFabrica.construir(source) : null;
            }
        });

        addConverter(new AbstractConverter<String, Email>() {
            @Override
            protected Email convert(String source) {
                return source != null ? emailFabrica.construir(source) : null;
            }
        });

        addConverter(new AbstractConverter<Long, ConsultaId>() {
            @Override
            protected ConsultaId convert(Long source) {
                return source != null ? new ConsultaId(source) : null;
            }
        });

        addConverter(new AbstractConverter<ConsultaId, Long>() {
            @Override
            protected Long convert(ConsultaId source) {
                return source != null ? source.getId() : null;
            }
        });
    }

    @Override
    public <D> D map(Object source, Class<D> destinationType) {
        return source != null ? super.map(source, destinationType) : null;
    }
}

