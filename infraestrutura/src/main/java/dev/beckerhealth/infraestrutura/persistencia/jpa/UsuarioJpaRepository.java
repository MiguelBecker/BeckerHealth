package dev.beckerhealth.infraestrutura.persistencia.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioJpaRepository extends JpaRepository<UsuarioJpa, Long> {
}
