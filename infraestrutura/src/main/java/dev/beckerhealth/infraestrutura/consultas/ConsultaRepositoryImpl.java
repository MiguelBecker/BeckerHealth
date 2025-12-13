package dev.beckerhealth.infraestrutura.consultas;

import dev.beckerhealth.aplicacao.consultas.ConsultaRepositorioAplicacao;
import dev.beckerhealth.aplicacao.consultas.dto.ConsultaResumo;
import dev.beckerhealth.aplicacao.consultas.dto.ConsultaResumoExpandido;
import dev.beckerhealth.dominio.consultas.Consulta;
import dev.beckerhealth.dominio.consultas.ConsultaId;
import dev.beckerhealth.dominio.consultas.ConsultaRepository;
import dev.beckerhealth.infraestrutura.persistencia.jpa.ConsultaJpa;
import dev.beckerhealth.infraestrutura.persistencia.jpa.ConsultaJpaRepository;
import dev.beckerhealth.infraestrutura.persistencia.jpa.JpaMapeador;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ConsultaRepositoryImpl implements ConsultaRepository, ConsultaRepositorioAplicacao {
    @Autowired
    ConsultaJpaRepository repositorio;

    @Autowired
    JpaMapeador mapeador;

    @Override
    public Consulta salvar(Consulta consulta) {
        var consultaJpa = mapeador.map(consulta, ConsultaJpa.class);
        var consultaSalva = repositorio.save(consultaJpa);
        return mapeador.map(consultaSalva, Consulta.class);
    }

    @Transactional
    @Override
    public Optional<Consulta> buscarPorId(ConsultaId id) {
        // Usar método com fetch join para garantir que paciente e médico sejam carregados
        return repositorio.findByIdWithRelations(id != null ? id.getId() : null)
                .map(consultaJpa -> mapeador.map(consultaJpa, Consulta.class));
    }

    @Override
    public List<Consulta> listarTodas() {
        return repositorio.findAll().stream()
                .map(consultaJpa -> mapeador.map(consultaJpa, Consulta.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Consulta> buscarPorData(LocalDate data) {
        return repositorio.findByDataConsulta(data).stream()
                .map(consultaJpa -> mapeador.map(consultaJpa, Consulta.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Consulta> buscarPorStatus(Consulta.StatusConsulta status) {
        return repositorio.findByStatus(mapStatus(status)).stream()
                .map(consultaJpa -> mapeador.map(consultaJpa, Consulta.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Consulta> buscarPorPaciente(Long pacienteId) {
        return repositorio.findByPacienteId(pacienteId).stream()
                .map(consultaJpa -> mapeador.map(consultaJpa, Consulta.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Consulta> buscarPorMedico(Long medicoId) {
        return repositorio.findByMedicoId(medicoId).stream()
                .map(consultaJpa -> mapeador.map(consultaJpa, Consulta.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deletar(ConsultaId id) {
        if (id != null) {
            repositorio.deleteById(id.getId());
        }
    }

    @Override
    public List<ConsultaResumo> pesquisarResumos() {
        return repositorio.findAll().stream()
                .filter(c -> c.status == ConsultaJpa.StatusConsulta.AGENDADA) // Filtrar apenas consultas agendadas
                .map(this::mapearParaResumo)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConsultaResumo> pesquisarResumosPorMedico(Long medicoId) {
        if (medicoId == null) {
            return List.of();
        }
        return repositorio.findByMedicoId(medicoId).stream()
                .filter(c -> c.status == ConsultaJpa.StatusConsulta.AGENDADA) // Filtrar apenas consultas agendadas
                .map(this::mapearParaResumo)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConsultaResumoExpandido> pesquisarResumosExpandidos() {
        return repositorio.findAll().stream()
                .map(this::mapearParaResumoExpandido)
                .collect(Collectors.toList());
    }

    private ConsultaJpa.StatusConsulta mapStatus(Consulta.StatusConsulta status) {
        return status != null ? ConsultaJpa.StatusConsulta.valueOf(status.name()) : null;
    }

    private ConsultaResumo mapearParaResumo(ConsultaJpa consultaJpa) {
        return ConsultaResumo.builder()
                .id(consultaJpa.id)
                .pacienteNome(consultaJpa.paciente != null ? consultaJpa.paciente.nome : null)
                .pacienteCpf(consultaJpa.paciente != null ? consultaJpa.paciente.cpf : null)
                .medicoNome(consultaJpa.medico != null ? consultaJpa.medico.nome : null)
                .medicoEspecialidade(consultaJpa.medico != null ? consultaJpa.medico.especialidade : null)
                .dataConsulta(consultaJpa.dataConsulta)
                .horaConsulta(consultaJpa.horaConsulta)
                .tipo(consultaJpa.tipo != null ? consultaJpa.tipo.name() : null)
                .status(consultaJpa.status != null ? consultaJpa.status.name() : null)
                .build();
    }

    private ConsultaResumoExpandido mapearParaResumoExpandido(ConsultaJpa consultaJpa) {
        var dto = new ConsultaResumoExpandido();
        dto.setId(consultaJpa.id);
        dto.setPacienteId(consultaJpa.paciente != null ? consultaJpa.paciente.id : null);
        dto.setPacienteNome(consultaJpa.paciente != null ? consultaJpa.paciente.nome : null);
        dto.setPacienteCpf(consultaJpa.paciente != null ? consultaJpa.paciente.cpf : null);
        dto.setPacienteEmail(consultaJpa.paciente != null ? consultaJpa.paciente.email : null);
        dto.setPacienteConvenio(consultaJpa.paciente != null ? consultaJpa.paciente.convenio : null);
        dto.setMedicoId(consultaJpa.medico != null ? consultaJpa.medico.id : null);
        dto.setMedicoNome(consultaJpa.medico != null ? consultaJpa.medico.nome : null);
        dto.setMedicoCrm(consultaJpa.medico != null ? consultaJpa.medico.crm : null);
        dto.setMedicoEspecialidade(consultaJpa.medico != null ? consultaJpa.medico.especialidade : null);
        dto.setMedicoEmail(consultaJpa.medico != null ? consultaJpa.medico.email : null);
        dto.setDataConsulta(consultaJpa.dataConsulta);
        dto.setHoraConsulta(consultaJpa.horaConsulta);
        dto.setTipo(consultaJpa.tipo != null ? consultaJpa.tipo.name() : null);
        dto.setStatus(consultaJpa.status != null ? consultaJpa.status.name() : null);
        return dto;
    }
}


