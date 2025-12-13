-- Criação das tabelas do sistema BeckerHealth

-- Tabela de Usuários
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    tipo VARCHAR(20) NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_usuarios_login ON usuarios(login);
CREATE INDEX IF NOT EXISTS idx_usuarios_email ON usuarios(email);

-- Tabela de Médicos
CREATE TABLE IF NOT EXISTS medicos (
    usuario_id BIGINT PRIMARY KEY,
    crm VARCHAR(20) NOT NULL UNIQUE,
    especialidade VARCHAR(100) NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- Tabela de Pacientes
CREATE TABLE IF NOT EXISTS pacientes (
    usuario_id BIGINT PRIMARY KEY,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    data_nascimento DATE,
    convenio VARCHAR(255),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- Tabela de Consultas
CREATE TABLE IF NOT EXISTS consultas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    paciente_id BIGINT NOT NULL,
    medico_id BIGINT NOT NULL,
    data_consulta DATE NOT NULL,
    hora_consulta TIME NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    FOREIGN KEY (paciente_id) REFERENCES pacientes(usuario_id),
    FOREIGN KEY (medico_id) REFERENCES medicos(usuario_id)
);

CREATE INDEX IF NOT EXISTS idx_consultas_data ON consultas(data_consulta);
CREATE INDEX IF NOT EXISTS idx_consultas_paciente ON consultas(paciente_id);
CREATE INDEX IF NOT EXISTS idx_consultas_medico ON consultas(medico_id);
CREATE INDEX IF NOT EXISTS idx_consultas_status ON consultas(status);

-- Tabela de Prontuários
CREATE TABLE IF NOT EXISTS prontuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    paciente_id BIGINT NOT NULL,
    medico_responsavel_id BIGINT,
    anamnese TEXT NOT NULL,
    diagnostico TEXT,
    prescricao TEXT,
    data_atendimento TIMESTAMP NOT NULL,
    tipo_atendimento VARCHAR(20) NOT NULL,
    observacoes VARCHAR(500),
    FOREIGN KEY (paciente_id) REFERENCES pacientes(usuario_id),
    FOREIGN KEY (medico_responsavel_id) REFERENCES medicos(usuario_id)
);

-- Tabela de Notificações
CREATE TABLE IF NOT EXISTS notificacoes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    destinatario_id BIGINT NOT NULL,
    titulo VARCHAR(200) NOT NULL,
    mensagem TEXT NOT NULL,
    data_envio TIMESTAMP NOT NULL,
    data_leitura TIMESTAMP,
    tipo VARCHAR(50) NOT NULL,
    status VARCHAR(20) NOT NULL,
    link VARCHAR(500),
    FOREIGN KEY (destinatario_id) REFERENCES usuarios(id)
);

-- Tabela de Relatórios
CREATE TABLE IF NOT EXISTS relatorios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    conteudo TEXT NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    gerado_por_id BIGINT,
    data_geracao TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL,
    observacoes VARCHAR(500),
    FOREIGN KEY (gerado_por_id) REFERENCES usuarios(id)
);

