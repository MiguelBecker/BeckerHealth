package dev.beckerhealth.infraestrutura.compartilhado;

import dev.beckerhealth.dominio.compartilhado.usuario.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepositoryJpa extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByCpf(String cpf);
}
