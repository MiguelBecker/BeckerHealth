package dev.beckerhealth.aplicacao.consultas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaResumoExpandido {
    private Long id;
    private Long pacienteId;
    private String pacienteNome;
    private String pacienteCpf;
    private String pacienteEmail;
    private String pacienteConvenio;
    private Long medicoId;
    private String medicoNome;
    private String medicoCrm;
    private String medicoEspecialidade;
    private String medicoEmail;
    private LocalDate dataConsulta;
    private LocalTime horaConsulta;
    private String tipo;
    private String status;

    // Explicit setters added to ensure infra module can compile even if Lombok processing is not performed.
    // These mirror the Lombok-generated setters from @Data.
    public void setId(Long id) { this.id = id; }
    public void setPacienteId(Long pacienteId) { this.pacienteId = pacienteId; }
    public void setPacienteNome(String pacienteNome) { this.pacienteNome = pacienteNome; }
    public void setPacienteCpf(String pacienteCpf) { this.pacienteCpf = pacienteCpf; }
    public void setPacienteEmail(String pacienteEmail) { this.pacienteEmail = pacienteEmail; }
    public void setPacienteConvenio(String pacienteConvenio) { this.pacienteConvenio = pacienteConvenio; }
    public void setMedicoId(Long medicoId) { this.medicoId = medicoId; }
    public void setMedicoNome(String medicoNome) { this.medicoNome = medicoNome; }
    public void setMedicoCrm(String medicoCrm) { this.medicoCrm = medicoCrm; }
    public void setMedicoEspecialidade(String medicoEspecialidade) { this.medicoEspecialidade = medicoEspecialidade; }
    public void setMedicoEmail(String medicoEmail) { this.medicoEmail = medicoEmail; }
    public void setDataConsulta(LocalDate dataConsulta) { this.dataConsulta = dataConsulta; }
    public void setHoraConsulta(LocalTime horaConsulta) { this.horaConsulta = horaConsulta; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setStatus(String status) { this.status = status; }
}

