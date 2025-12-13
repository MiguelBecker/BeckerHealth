package dev.beckerhealth.infraestrutura.persistencia.jpa;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.beckerhealth.dominio.compartilhado.usuario.*;
import dev.beckerhealth.dominio.consultas.Consulta;
import dev.beckerhealth.dominio.consultas.ConsultaId;
import dev.beckerhealth.dominio.notificacao.Notificacao;
import dev.beckerhealth.dominio.prontuario.Prontuario;
import dev.beckerhealth.dominio.relatorios.Relatorio;

@Component
public class JpaMapeador extends ModelMapper {
    private CpfFabrica cpfFabrica;
    private CrmFabrica crmFabrica;
    private EmailFabrica emailFabrica;

    private @Autowired UsuarioJpaRepository usuarioRepositorio;
    private @Autowired MedicoJpaRepository medicoRepositorio;
    private @Autowired PacienteJpaRepository pacienteRepositorio;

    JpaMapeador() {
        cpfFabrica = new CpfFabrica();
        crmFabrica = new CrmFabrica();
        emailFabrica = new EmailFabrica();

        var configuracao = getConfiguration();
        configuracao.setFieldMatchingEnabled(true);
        configuracao.setFieldAccessLevel(AccessLevel.PRIVATE);

        addConverter(new AbstractConverter<String, Cpf>() {
            @Override
            protected Cpf convert(String source) {
                return source != null ? cpfFabrica.construir(source) : null;
            }
        });

        addConverter(new AbstractConverter<Cpf, String>() {
            @Override
            protected String convert(Cpf source) {
                return source != null ? source.getCodigo() : null;
            }
        });

        addConverter(new AbstractConverter<String, Crm>() {
            @Override
            protected Crm convert(String source) {
                return source != null ? crmFabrica.construir(source) : null;
            }
        });

        addConverter(new AbstractConverter<Crm, String>() {
            @Override
            protected String convert(Crm source) {
                return source != null ? source.getCodigo() : null;
            }
        });

        addConverter(new AbstractConverter<String, Email>() {
            @Override
            protected Email convert(String source) {
                return source != null ? emailFabrica.construir(source) : null;
            }
        });

        addConverter(new AbstractConverter<Email, String>() {
            @Override
            protected String convert(Email source) {
                return source != null ? source.getEndereco() : null;
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

        addConverter(new AbstractConverter<UsuarioJpa, Usuario>() {
            @Override
            protected Usuario convert(UsuarioJpa source) {
                if (source == null) return null;
                var email = source.email != null ? map(source.email, Email.class) : null;
                return new Usuario(source.id, source.login, source.senha, source.nome, email,
                        map(source.tipo, Usuario.TipoUsuario.class));
            }
        });

        addConverter(new AbstractConverter<Usuario, UsuarioJpa>() {
            @Override
            protected UsuarioJpa convert(Usuario source) {
                if (source == null) return null;
                var usuarioJpa = new UsuarioJpa();
                usuarioJpa.id = source.getId();
                usuarioJpa.login = source.getLogin();
                usuarioJpa.senha = source.getSenha();
                usuarioJpa.nome = source.getNome();
                usuarioJpa.email = source.getEmail() != null ? source.getEmail().getEndereco() : null;
                usuarioJpa.tipo = map(source.getTipo(), UsuarioJpa.TipoUsuario.class);
                return usuarioJpa;
            }
        });

        addConverter(new AbstractConverter<UsuarioJpa.TipoUsuario, Usuario.TipoUsuario>() {
            @Override
            protected Usuario.TipoUsuario convert(UsuarioJpa.TipoUsuario source) {
                return source != null ? Usuario.TipoUsuario.valueOf(source.name()) : null;
            }
        });

        addConverter(new AbstractConverter<Usuario.TipoUsuario, UsuarioJpa.TipoUsuario>() {
            @Override
            protected UsuarioJpa.TipoUsuario convert(Usuario.TipoUsuario source) {
                return source != null ? UsuarioJpa.TipoUsuario.valueOf(source.name()) : null;
            }
        });

        addConverter(new AbstractConverter<MedicoJpa, Medico>() {
            @Override
            protected Medico convert(MedicoJpa source) {
                if (source == null) return null;
                var email = source.email != null ? map(source.email, Email.class) : null;
                var crm = source.crm != null ? map(source.crm, Crm.class) : null;
                return new Medico(source.id, source.login, source.senha, source.nome, email,
                        map(source.tipo, Usuario.TipoUsuario.class), crm, source.especialidade);
            }
        });

        addConverter(new AbstractConverter<Medico, MedicoJpa>() {
            @Override
            protected MedicoJpa convert(Medico source) {
                if (source == null) return null;
                var medicoJpa = new MedicoJpa();
                medicoJpa.id = source.getId();
                medicoJpa.login = source.getLogin();
                medicoJpa.senha = source.getSenha();
                medicoJpa.nome = source.getNome();
                medicoJpa.email = source.getEmail() != null ? source.getEmail().getEndereco() : null;
                medicoJpa.tipo = map(source.getTipo(), UsuarioJpa.TipoUsuario.class);
                medicoJpa.crm = source.getCrm() != null ? source.getCrm().getCodigo() : null;
                medicoJpa.especialidade = source.getEspecialidade();
                return medicoJpa;
            }
        });

        addConverter(new AbstractConverter<PacienteJpa, Paciente>() {
            @Override
            protected Paciente convert(PacienteJpa source) {
                if (source == null) return null;
                var email = source.email != null ? map(source.email, Email.class) : null;
                var cpf = source.cpf != null ? map(source.cpf, Cpf.class) : null;
                return new Paciente(source.id, source.login, source.senha, source.nome, email,
                        map(source.tipo, Usuario.TipoUsuario.class), cpf, source.dataNascimento,
                        source.convenio);
            }
        });

        addConverter(new AbstractConverter<Paciente, PacienteJpa>() {
            @Override
            protected PacienteJpa convert(Paciente source) {
                if (source == null) return null;
                var pacienteJpa = new PacienteJpa();
                pacienteJpa.id = source.getId();
                pacienteJpa.login = source.getLogin();
                pacienteJpa.senha = source.getSenha();
                pacienteJpa.nome = source.getNome();
                pacienteJpa.email = source.getEmail() != null ? source.getEmail().getEndereco() : null;
                pacienteJpa.tipo = map(source.getTipo(), UsuarioJpa.TipoUsuario.class);
                pacienteJpa.cpf = source.getCpf() != null ? source.getCpf().getCodigo() : null;
                pacienteJpa.dataNascimento = source.getDataNascimento();
                pacienteJpa.convenio = source.getConvenio();
                return pacienteJpa;
            }
        });

        addConverter(new AbstractConverter<ConsultaJpa, Consulta>() {
            @Override
            protected Consulta convert(ConsultaJpa source) {
                if (source == null) return null;
                var paciente = map(source.paciente, Paciente.class);
                var medico = map(source.medico, Medico.class);
                var id = source.id != null ? new ConsultaId(source.id) : null;
                return new Consulta(id, paciente, medico, source.dataConsulta, source.horaConsulta,
                        map(source.tipo, Consulta.TipoConsulta.class),
                        map(source.status, Consulta.StatusConsulta.class));
            }
        });

        addConverter(new AbstractConverter<Consulta, ConsultaJpa>() {
            @Override
            protected ConsultaJpa convert(Consulta source) {
                if (source == null) return null;
                var consultaJpa = new ConsultaJpa();
                consultaJpa.id = source.getId() != null ? source.getId().getId() : null;
                
                // Buscar PacienteJpa do banco se tiver apenas o ID
                if (source.getPaciente() != null && source.getPaciente().getId() != null) {
                    consultaJpa.paciente = pacienteRepositorio.findById(source.getPaciente().getId())
                            .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado com ID: " + source.getPaciente().getId()));
                } else {
                    consultaJpa.paciente = map(source.getPaciente(), PacienteJpa.class);
                }
                
                // Buscar MedicoJpa do banco se tiver apenas o ID
                if (source.getMedico() != null && source.getMedico().getId() != null) {
                    consultaJpa.medico = medicoRepositorio.findById(source.getMedico().getId())
                            .orElseThrow(() -> new IllegalArgumentException("Médico não encontrado com ID: " + source.getMedico().getId()));
                } else {
                    consultaJpa.medico = map(source.getMedico(), MedicoJpa.class);
                }
                
                consultaJpa.dataConsulta = source.getDataConsulta();
                consultaJpa.horaConsulta = source.getHoraConsulta();
                consultaJpa.tipo = map(source.getTipo(), ConsultaJpa.TipoConsulta.class);
                consultaJpa.status = map(source.getStatus(), ConsultaJpa.StatusConsulta.class);
                return consultaJpa;
            }
        });

        addConverter(new AbstractConverter<ConsultaJpa.TipoConsulta, Consulta.TipoConsulta>() {
            @Override
            protected Consulta.TipoConsulta convert(ConsultaJpa.TipoConsulta source) {
                return source != null ? Consulta.TipoConsulta.valueOf(source.name()) : null;
            }
        });

        addConverter(new AbstractConverter<Consulta.TipoConsulta, ConsultaJpa.TipoConsulta>() {
            @Override
            protected ConsultaJpa.TipoConsulta convert(Consulta.TipoConsulta source) {
                return source != null ? ConsultaJpa.TipoConsulta.valueOf(source.name()) : null;
            }
        });

        addConverter(new AbstractConverter<ConsultaJpa.StatusConsulta, Consulta.StatusConsulta>() {
            @Override
            protected Consulta.StatusConsulta convert(ConsultaJpa.StatusConsulta source) {
                return source != null ? Consulta.StatusConsulta.valueOf(source.name()) : null;
            }
        });

        addConverter(new AbstractConverter<Consulta.StatusConsulta, ConsultaJpa.StatusConsulta>() {
            @Override
            protected ConsultaJpa.StatusConsulta convert(Consulta.StatusConsulta source) {
                return source != null ? ConsultaJpa.StatusConsulta.valueOf(source.name()) : null;
            }
        });

        addConverter(new AbstractConverter<ProntuarioJpa, Prontuario>() {
            @Override
            protected Prontuario convert(ProntuarioJpa source) {
                if (source == null) return null;
                var paciente = map(source.paciente, Paciente.class);
                var medico = source.medicoResponsavel != null ? map(source.medicoResponsavel, Medico.class) : null;
                return new Prontuario(source.id, paciente, medico, source.anamnese, source.diagnostico,
                        source.prescricao, source.dataAtendimento,
                        map(source.tipoAtendimento, Prontuario.TipoAtendimento.class), source.observacoes);
            }
        });

        addConverter(new AbstractConverter<Prontuario, ProntuarioJpa>() {
            @Override
            protected ProntuarioJpa convert(Prontuario source) {
                if (source == null) return null;
                var prontuarioJpa = new ProntuarioJpa();
                prontuarioJpa.id = source.getId();
                
                // Buscar PacienteJpa do banco se tiver apenas o ID
                if (source.getPaciente() != null && source.getPaciente().getId() != null) {
                    prontuarioJpa.paciente = pacienteRepositorio.findById(source.getPaciente().getId())
                            .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado com ID: " + source.getPaciente().getId()));
                } else {
                    prontuarioJpa.paciente = map(source.getPaciente(), PacienteJpa.class);
                }
                
                // Buscar MedicoJpa do banco se tiver apenas o ID
                if (source.getMedicoResponsavel() != null && source.getMedicoResponsavel().getId() != null) {
                    prontuarioJpa.medicoResponsavel = medicoRepositorio.findById(source.getMedicoResponsavel().getId())
                            .orElse(null); // Médico é opcional, então pode ser null
                } else {
                    prontuarioJpa.medicoResponsavel = source.getMedicoResponsavel() != null ?
                            map(source.getMedicoResponsavel(), MedicoJpa.class) : null;
                }
                
                prontuarioJpa.anamnese = source.getAnamnese();
                prontuarioJpa.diagnostico = source.getDiagnostico();
                prontuarioJpa.prescricao = source.getPrescricao();
                prontuarioJpa.dataAtendimento = source.getDataAtendimento();
                prontuarioJpa.tipoAtendimento = map(source.getTipoAtendimento(), ProntuarioJpa.TipoAtendimento.class);
                prontuarioJpa.observacoes = source.getObservacoes();
                return prontuarioJpa;
            }
        });

        addConverter(new AbstractConverter<ProntuarioJpa.TipoAtendimento, Prontuario.TipoAtendimento>() {
            @Override
            protected Prontuario.TipoAtendimento convert(ProntuarioJpa.TipoAtendimento source) {
                return source != null ? Prontuario.TipoAtendimento.valueOf(source.name()) : null;
            }
        });

        addConverter(new AbstractConverter<Prontuario.TipoAtendimento, ProntuarioJpa.TipoAtendimento>() {
            @Override
            protected ProntuarioJpa.TipoAtendimento convert(Prontuario.TipoAtendimento source) {
                return source != null ? ProntuarioJpa.TipoAtendimento.valueOf(source.name()) : null;
            }
        });

        addConverter(new AbstractConverter<NotificacaoJpa, Notificacao>() {
            @Override
            protected Notificacao convert(NotificacaoJpa source) {
                if (source == null) return null;
                var destinatario = map(source.destinatario, Usuario.class);
                return new Notificacao(source.id, destinatario, source.titulo, source.mensagem,
                        source.dataEnvio, source.dataLeitura,
                        map(source.tipo, Notificacao.TipoNotificacao.class),
                        map(source.status, Notificacao.StatusNotificacao.class), source.link);
            }
        });

        addConverter(new AbstractConverter<Notificacao, NotificacaoJpa>() {
            @Override
            protected NotificacaoJpa convert(Notificacao source) {
                if (source == null) return null;
                var notificacaoJpa = new NotificacaoJpa();
                notificacaoJpa.id = source.getId();
                
                // Buscar UsuarioJpa do banco se tiver apenas o ID
                if (source.getDestinatario() != null && source.getDestinatario().getId() != null) {
                    notificacaoJpa.destinatario = usuarioRepositorio.findById(source.getDestinatario().getId())
                            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com ID: " + source.getDestinatario().getId()));
                } else {
                    notificacaoJpa.destinatario = map(source.getDestinatario(), UsuarioJpa.class);
                }
                
                notificacaoJpa.titulo = source.getTitulo();
                notificacaoJpa.mensagem = source.getMensagem();
                notificacaoJpa.dataEnvio = source.getDataEnvio();
                notificacaoJpa.dataLeitura = source.getDataLeitura();
                notificacaoJpa.tipo = map(source.getTipo(), NotificacaoJpa.TipoNotificacao.class);
                notificacaoJpa.status = map(source.getStatus(), NotificacaoJpa.StatusNotificacao.class);
                notificacaoJpa.link = source.getLink();
                return notificacaoJpa;
            }
        });

        addConverter(new AbstractConverter<NotificacaoJpa.TipoNotificacao, Notificacao.TipoNotificacao>() {
            @Override
            protected Notificacao.TipoNotificacao convert(NotificacaoJpa.TipoNotificacao source) {
                return source != null ? Notificacao.TipoNotificacao.valueOf(source.name()) : null;
            }
        });

        addConverter(new AbstractConverter<Notificacao.TipoNotificacao, NotificacaoJpa.TipoNotificacao>() {
            @Override
            protected NotificacaoJpa.TipoNotificacao convert(Notificacao.TipoNotificacao source) {
                return source != null ? NotificacaoJpa.TipoNotificacao.valueOf(source.name()) : null;
            }
        });

        addConverter(new AbstractConverter<NotificacaoJpa.StatusNotificacao, Notificacao.StatusNotificacao>() {
            @Override
            protected Notificacao.StatusNotificacao convert(NotificacaoJpa.StatusNotificacao source) {
                return source != null ? Notificacao.StatusNotificacao.valueOf(source.name()) : null;
            }
        });

        addConverter(new AbstractConverter<Notificacao.StatusNotificacao, NotificacaoJpa.StatusNotificacao>() {
            @Override
            protected NotificacaoJpa.StatusNotificacao convert(Notificacao.StatusNotificacao source) {
                return source != null ? NotificacaoJpa.StatusNotificacao.valueOf(source.name()) : null;
            }
        });
        addConverter(new AbstractConverter<RelatorioJpa, Relatorio>() {
            @Override
            protected Relatorio convert(RelatorioJpa source) {
                if (source == null) return null;
            Long geradoPorId = source.geradoPor != null ? source.geradoPor.id : null;
            return new Relatorio(source.id, source.titulo, source.conteudo,
                map(source.tipo, Relatorio.TipoRelatorio.class), geradoPorId, source.dataGeracao,
                        map(source.status, Relatorio.StatusRelatorio.class), source.observacoes);
            }
        });

        addConverter(new AbstractConverter<Relatorio, RelatorioJpa>() {
            @Override
            protected RelatorioJpa convert(Relatorio source) {
                if (source == null) return null;
                var relatorioJpa = new RelatorioJpa();
                relatorioJpa.id = source.getId();
                relatorioJpa.titulo = source.getTitulo();
                relatorioJpa.conteudo = source.getConteudo();
                relatorioJpa.tipo = map(source.getTipo(), RelatorioJpa.TipoRelatorio.class);
                if (source.getGeradoPorId() != null) {
                    var usuarioJpa = new UsuarioJpa();
                    usuarioJpa.id = source.getGeradoPorId();
                    relatorioJpa.geradoPor = usuarioJpa;
                } else {
                    relatorioJpa.geradoPor = null;
                }
                relatorioJpa.dataGeracao = source.getDataGeracao();
                relatorioJpa.status = map(source.getStatus(), RelatorioJpa.StatusRelatorio.class);
                relatorioJpa.observacoes = source.getObservacoes();
                return relatorioJpa;
            }
        });

        addConverter(new AbstractConverter<RelatorioJpa.TipoRelatorio, Relatorio.TipoRelatorio>() {
            @Override
            protected Relatorio.TipoRelatorio convert(RelatorioJpa.TipoRelatorio source) {
                return source != null ? Relatorio.TipoRelatorio.valueOf(source.name()) : null;
            }
        });

        addConverter(new AbstractConverter<Relatorio.TipoRelatorio, RelatorioJpa.TipoRelatorio>() {
            @Override
            protected RelatorioJpa.TipoRelatorio convert(Relatorio.TipoRelatorio source) {
                return source != null ? RelatorioJpa.TipoRelatorio.valueOf(source.name()) : null;
            }
        });

        addConverter(new AbstractConverter<RelatorioJpa.StatusRelatorio, Relatorio.StatusRelatorio>() {
            @Override
            protected Relatorio.StatusRelatorio convert(RelatorioJpa.StatusRelatorio source) {
                return source != null ? Relatorio.StatusRelatorio.valueOf(source.name()) : null;
            }
        });

        addConverter(new AbstractConverter<Relatorio.StatusRelatorio, RelatorioJpa.StatusRelatorio>() {
            @Override
            protected RelatorioJpa.StatusRelatorio convert(Relatorio.StatusRelatorio source) {
                return source != null ? RelatorioJpa.StatusRelatorio.valueOf(source.name()) : null;
            }
        });
    }

    @Override
    public <D> D map(Object source, Class<D> destinationType) {
        return source != null ? super.map(source, destinationType) : null;
    }
}

