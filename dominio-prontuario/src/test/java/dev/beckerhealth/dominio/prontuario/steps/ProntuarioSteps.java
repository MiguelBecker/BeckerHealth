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
        medico = new Medico();
    }

    @Quando("o médico registrar um diagnóstico {string} no prontuário")
    public void oMedicoRegistrarUmDiagnostico(String diagnostico) {
        prontuario = new Prontuario();
        prontuario.setPaciente(paciente);
        prontuario.setMedicoResponsavel(medico);
        prontuario.setAnamnese("Anamnese inicial");
        prontuario.setDiagnostico(diagnostico);
        prontuario.setPrescricao("Prescrição inicial");
        prontuario.setDataAtendimento(LocalDateTime.now());
        prontuario.setTipoAtendimento(Prontuario.TipoAtendimento.CONSULTA);
    }

    @Então("o prontuário deve conter o diagnóstico {string}")
    public void oProntuarioDeveConterODiagnostico(String esperado) {
        assertNotNull(prontuario);
        assertEquals(esperado, prontuario.getDiagnostico());
    }
}
