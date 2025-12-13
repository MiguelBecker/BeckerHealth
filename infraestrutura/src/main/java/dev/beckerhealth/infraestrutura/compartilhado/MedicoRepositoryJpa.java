package dev.beckerhealth.infraestrutura.compartilhado;

import dev.beckerhealth.dominio.compartilhado.usuario.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicoRepositoryJpa extends JpaRepository<Medico, Long> {
    Optional<Medico> findByCrm(String crm);
}
