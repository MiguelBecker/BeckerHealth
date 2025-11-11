package dev.beckerhealth.infraestrutura.compartilhado;

import dev.beckerhealth.dominio.compartilhado.usuario.Medico;
import dev.beckerhealth.dominio.compartilhado.usuario.Paciente;
import dev.beckerhealth.dominio.compartilhado.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepositoryJpa extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByLogin(String login);
    Optional<Usuario> findByEmail(String email);
}

@Repository
public interface MedicoRepositoryJpa extends JpaRepository<Medico, Long> {
    Optional<Medico> findByCrm(String crm);
}

@Repository
public interface PacienteRepositoryJpa extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByCpf(String cpf);
}
