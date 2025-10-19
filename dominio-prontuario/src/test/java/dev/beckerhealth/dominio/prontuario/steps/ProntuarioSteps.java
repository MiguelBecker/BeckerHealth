package dev.beckerhealth.dominio.prontuario.steps;

import dev.beckerhealth.dominio.compartilhado.usuario.Medico;
import dev.beckerhealth.dominio.compartilhado.usuario.Paciente;
import io.cucumber.java.pt.*;
import dev.beckerhealth.dominio.prontuario.Prontuario;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

public class ProntuarioSteps {

    private Prontuario prontuario;
    private Medico medico;
    private Paciente paciente;

    @Dado("que existe um paciente e um médico")
    public void queExisteUmPacienteEMedico() {
        paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNome("João Silva");

        medico = new Medico();
        medico.setId(1L);
        medico.setNome("Dr. Maria Santos");
        medico.setCrm("12345");
        medico.setEspecialidade("Cardiologia");
    }

    @Dado("que um médico autenticado acessa o prontuário do paciente")
    public void queUmMedicoAutenticadoAcessaOProntuarioDoPaciente() {
        // Médico já configurado no passo anterior
    }

    @Quando("o médico registra um diagnóstico {string}")
    public void oMedicoRegistraUmDiagnostico(String diagnostico) {
        prontuario = new Prontuario();
        prontuario.setPaciente(paciente);
        prontuario.setMedicoResponsavel(medico);
        prontuario.setAnamnese("Anamnese inicial");
        prontuario.setDiagnostico(diagnostico);
        prontuario.setPrescricao("Prescrição inicial");
        prontuario.setTipoAtendimento(Prontuario.TipoAtendimento.CONSULTA);
    }

    @Entao("o prontuário deve conter o diagnóstico {string}")
    public void oProntuarioDeveConterODiagnostico(String esperado) {
        assertNotNull(prontuario);
        assertEquals(esperado, prontuario.getDiagnostico());
    }
}