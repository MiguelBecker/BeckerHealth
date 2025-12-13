-- Inserção de consultas para fins demonstrativos
-- Consultas para o médico 2 (Dr. João Silva) com status AGENDADA

-- Remove consultas antigas se existirem para evitar duplicação
DELETE FROM consultas;

-- Insere novas consultas (deixa o banco gerar os IDs automaticamente)
INSERT INTO consultas (paciente_id, medico_id, data_consulta, hora_consulta, tipo, status) VALUES
(4, 2, CURRENT_DATE, '14:30:00', 'ROTINA', 'AGENDADA'),
(5, 2, DATEADD('DAY', 1, CURRENT_DATE), '10:00:00', 'RETORNO', 'AGENDADA'),
(4, 2, DATEADD('DAY', 2, CURRENT_DATE), '16:00:00', 'ROTINA', 'AGENDADA'),
(4, 3, CURRENT_DATE, '09:00:00', 'ROTINA', 'AGENDADA'),
(5, 3, DATEADD('DAY', 1, CURRENT_DATE), '15:00:00', 'RETORNO', 'AGENDADA');

