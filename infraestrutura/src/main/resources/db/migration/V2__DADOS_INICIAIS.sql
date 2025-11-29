INSERT INTO usuarios (id, login, senha, nome, email, tipo) VALUES
(1, 'admin', 'admin123', 'Administrador', 'admin@beckerhealth.com', 'ADMIN'),
(2, 'medico1', 'medico123', 'Dr. João Silva', 'joao.silva@beckerhealth.com', 'MEDICO'),
(3, 'medico2', 'medico123', 'Dra. Maria Santos', 'maria.santos@beckerhealth.com', 'MEDICO'),
(4, 'paciente1', 'paciente123', 'Carlos Oliveira', 'carlos.oliveira@email.com', 'PACIENTE'),
(5, 'paciente2', 'paciente123', 'Ana Costa', 'ana.costa@email.com', 'PACIENTE');

INSERT INTO medicos (usuario_id, crm, especialidade) VALUES
(2, 'CRM12345', 'Cardiologia'),
(3, 'CRM67890', 'Pediatria');

INSERT INTO pacientes (usuario_id, cpf, data_nascimento, convenio) VALUES
(4, '123.456.789-00', '1980-05-15', 'Unimed'),
(5, '987.654.321-00', '1990-08-20', 'SulAmérica');
