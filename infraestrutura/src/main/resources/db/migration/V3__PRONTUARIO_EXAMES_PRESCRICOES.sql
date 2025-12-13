-- Tabela de Exames
CREATE TABLE IF NOT EXISTS exames (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    consulta_id BIGINT NOT NULL,
    tipo VARCHAR(100) NOT NULL,
    resultado TEXT,
    data_exame DATE,
    liberado BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (consulta_id) REFERENCES consultas(id)
);

CREATE INDEX IF NOT EXISTS idx_exames_consulta ON exames(consulta_id);
CREATE INDEX IF NOT EXISTS idx_exames_liberado ON exames(liberado);

-- Tabela de Prescrições (Receitas Digitais)
CREATE TABLE IF NOT EXISTS prescricoes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    prontuario_id BIGINT NOT NULL,
    conteudo TEXT NOT NULL,
    validade DATE NOT NULL,
    assinatura_medica VARCHAR(255) NOT NULL,
    FOREIGN KEY (prontuario_id) REFERENCES prontuarios(id)
);

CREATE INDEX IF NOT EXISTS idx_prescricoes_prontuario ON prescricoes(prontuario_id);
CREATE INDEX IF NOT EXISTS idx_prescricoes_validade ON prescricoes(validade);

