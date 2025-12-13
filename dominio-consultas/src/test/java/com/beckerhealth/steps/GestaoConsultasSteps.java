package com.beckerhealth.steps;

import dev.beckerhealth.dominio.consultas.Consulta;
import dev.beckerhealth.dominio.consultas.ConsultaRepository;
import dev.beckerhealth.dominio.consultas.ConsultaService;
import dev.beckerhealth.dominio.compartilhado.usuario.Medico;
import dev.beckerhealth.dominio.compartilhado.usuario.Paciente;

import io.cucumber.java.pt.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class GestaoConsultasSteps {

    private ConsultaService consultaService;
    private ConsultaRepository consultaRepositoryFake;
    private Consulta consultaAgendada;
    private Medico medico;
    private Paciente paciente;
    private Exception excecaoCapturada;

    public GestaoConsultasSteps() {
        this.consultaRepositoryFake = new ConsultaRepository() {
            private List<Consulta> consultas = new ArrayList<>();
            private Long idCounter = 1L;

            @Override
            public Consulta salvar(Consulta consulta) {
                if (consulta.getId() == null) {
                    consulta.setId(idCounter++);
                }
                consultas.removeIf(c -> c.getId().equals(consulta.getId()));
                consultas.add(consulta);
                return consulta;
            }

            @Override
            public Optional<Consulta> buscarPorId(Long id) {
                return consultas.stream().filter(c -> c.getId().equals(id)).findFirst();
            }

            @Override
            public List<Consulta> listarTodas() {
                return consultas;
            }

            @Override
            public List<Consulta> buscarPorData(LocalDate data) {
                return consultas.stream()
                        .filter(c -> c.getDataConsulta().equals(data))
                        .toList();
            }

            @Override
            public List<Consulta> buscarPorStatus(Consulta.StatusConsulta status) {
                return consultas.stream()
                        .filter(c -> c.getStatus() == status)
                        .toList();
            }

            @Override
            public List<Consulta> buscarPorPaciente(Long pacienteId) {
                return consultas.stream()
                        .filter(c -> c.getPaciente().getId().equals(pacienteId))
                        .toList();
            }

            @Override
            public List<Consulta> buscarPorMedico(Long medicoId) {
                return consultas.stream()
                        .filter(c -> c.getMedico().getId().equals(medicoId))
                        .toList();
            }

            @Override
            public void deletar(Long id) {
                consultas.removeIf(c -> c.getId().equals(id));
            }
        };

        this.consultaService = new ConsultaService(consultaRepositoryFake);
    }

    @Dado("que existe um médico com horário livre às {int}:{int}")
    public void medicoComHorarioLivre(int hora, int minuto) {
        medico = new Medico();
        medico.setId(1L);

        paciente = new Paciente();
        paciente.setId(1L);

        consultaAgendada = new Consulta();
        consultaAgendada.setMedico(medico);
        consultaAgendada.setPaciente(paciente);
        consultaAgendada.setDataConsulta(LocalDate.now());
        consultaAgendada.setHoraConsulta(LocalTime.of(hora, minuto));
        consultaAgendada.setTipo(Consulta.TipoConsulta.ROTINA);
    }

    @Quando("o paciente agenda uma consulta para {int}:{int}")
    public void pacienteAgendaConsulta(int hora, int minuto) {
        consultaAgendada.setHoraConsulta(LocalTime.of(hora, minuto));
        consultaAgendada = consultaService.agendarConsulta(consultaAgendada);
    }

    @Entao("a consulta deve ser marcada com sucesso")
    public void consultaMarcadaComSucesso() {
        assertNotNull(consultaAgendada.getId());
        assertEquals(Consulta.StatusConsulta.AGENDADA, consultaAgendada.getStatus());
    }

    @Dado("que o médico já possui uma consulta às {int}:{int}")
    public void medicoJaPossuiConsulta(int hora, int minuto) {
        Consulta consultaExistente = new Consulta();
        consultaExistente.setId(99L);
        consultaExistente.setMedico(medico);
        consultaExistente.setPaciente(paciente);
        consultaExistente.setDataConsulta(LocalDate.now());
        consultaExistente.setHoraConsulta(LocalTime.of(hora, minuto));
        consultaExistente.setTipo(Consulta.TipoConsulta.ROTINA);
        consultaExistente.setStatus(Consulta.StatusConsulta.AGENDADA);

        consultaRepositoryFake.salvar(consultaExistente);
    }

    @Quando("o paciente tenta agendar outra consulta para {int}:{int}")
    public void pacienteTentaAgendarOutraConsulta(int hora, int minuto) {
        Consulta novaConsulta = new Consulta();
        novaConsulta.setMedico(medico);
        novaConsulta.setPaciente(paciente);
        novaConsulta.setDataConsulta(LocalDate.now());
        novaConsulta.setHoraConsulta(LocalTime.of(hora, minuto));
        novaConsulta.setTipo(Consulta.TipoConsulta.ROTINA);

        try {
            consultaService.agendarConsulta(novaConsulta);
            fail("Esperado erro de horário ocupado");
        } catch (Exception e) {
            excecaoCapturada = e;
        }
    }

    @Entao("o sistema deve impedir o agendamento")
    public void sistemaImpedeAgendamento() {
        assertTrue(excecaoCapturada instanceof IllegalStateException);
    }

    @Dado("que o paciente possui uma consulta marcada para daqui a {int} horas")
    public void pacientePossuiConsultaDaquiHoras(int horas) {
        consultaAgendada = new Consulta();
        consultaAgendada.setId(2L);
        consultaAgendada.setMedico(medico);
        consultaAgendada.setPaciente(paciente);
        consultaAgendada.setDataConsulta(LocalDate.now());
        consultaAgendada.setHoraConsulta(LocalTime.now().plusHours(horas));
        consultaAgendada.setTipo(Consulta.TipoConsulta.ROTINA);
        consultaAgendada.setStatus(Consulta.StatusConsulta.AGENDADA);
        consultaRepositoryFake.salvar(consultaAgendada);
    }

    @Quando("o paciente cancela a consulta")
    public void pacienteCancelaConsulta() {
        try {
            consultaService.cancelarConsulta(consultaAgendada, LocalDate.now(), LocalTime.now());
        } catch (Exception e) {
            excecaoCapturada = e;
        }
    }

    @Entao("o sistema deve cancelar sem aplicar penalidade")
    public void sistemaCancelaSemPenalidade() {
        assertEquals(Consulta.StatusConsulta.CANCELADA, consultaAgendada.getStatus());
        assertNull("Não deveria haver exceção", excecaoCapturada);
    }

    @Entao("o sistema deve cancelar aplicando penalidade")
    public void sistemaCancelaComPenalidade() {
        assertEquals(Consulta.StatusConsulta.CANCELADA, consultaAgendada.getStatus());
        assertTrue("Esperava penalidade (exceção)", excecaoCapturada instanceof IllegalStateException);
    }

    @Dado("que o médico já possui uma consulta de rotina às {int}:{int}")
    public void medicoJaPossuiConsultaDeRotina(int hora, int minuto) {
        Consulta rotina = new Consulta();
        rotina.setId(3L);
        rotina.setMedico(medico);
        rotina.setPaciente(paciente);
        rotina.setDataConsulta(LocalDate.now());
        rotina.setHoraConsulta(LocalTime.of(hora, minuto));
        rotina.setTipo(Consulta.TipoConsulta.ROTINA);
        rotina.setStatus(Consulta.StatusConsulta.AGENDADA);

        consultaRepositoryFake.salvar(rotina);
    }

    @Quando("o paciente agenda uma consulta de urgência às {int}:{int}")
    public void pacienteAgendaConsultaUrgencia(int hora, int minuto) {
        Consulta urgencia = new Consulta();
        urgencia.setMedico(medico);
        urgencia.setPaciente(paciente);
        urgencia.setDataConsulta(LocalDate.now());
        urgencia.setHoraConsulta(LocalTime.of(hora, minuto));
        urgencia.setTipo(Consulta.TipoConsulta.URGENCIA);

        consultaAgendada = consultaService.agendarConsulta(urgencia);
    }

    @Entao("o sistema deve permitir o agendamento de urgência")
    public void sistemaPermiteAgendamentoUrgencia() {
        assertNotNull(consultaAgendada.getId());
        assertEquals(Consulta.TipoConsulta.URGENCIA, consultaAgendada.getTipo());
    }

    @Dado("o médico tem horário livre às {int}:{int}")
    public void medicoTemHorarioLivre(int hora, int minuto) {
    }

    @Quando("o paciente reagenda a consulta para às {int}:{int}")
    public void pacienteReagendaConsulta(int hora, int minuto) {
        consultaAgendada = consultaService.reagendarConsulta(
            consultaAgendada,
            LocalTime.of(hora, minuto)
        );
    }

    @Entao("o sistema deve atualizar a consulta para o novo horário")
    public void sistemaAtualizaConsultaNovoHorario() {
        assertNotNull(consultaAgendada.getId());
        assertEquals(Consulta.StatusConsulta.AGENDADA, consultaAgendada.getStatus());
    }
}
    