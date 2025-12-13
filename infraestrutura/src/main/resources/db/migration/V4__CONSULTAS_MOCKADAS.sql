-- Inserção de consultas para fins demonstrativos
-- Consultas para o médico 2 (Dr. João Silva) com status AGENDADA

-- Remove consultas antigas se existirem para evitar duplicação (só se a tabela existir)
DELETE FROM consultas WHERE id IS NOT NULL;

-- Insere novas consultas com IDs fixos para garantir
INSERT INTO consultas (id, paciente_id, medico_id, data_consulta, hora_consulta, tipo, status) VALUES
(1, 4, 2, CURRENT_DATE, '14:30:00', 'ROTINA', 'AGENDADA'),
(2, 5, 2, DATEADD('DAY', 1, CURRENT_DATE), '10:00:00', 'RETORNO', 'AGENDADA'),
(3, 4, 2, DATEADD('DAY', 2, CURRENT_DATE), '16:00:00', 'ROTINA', 'AGENDADA'),
(4, 4, 3, CURRENT_DATE, '09:00:00', 'ROTINA', 'AGENDADA'),
(5, 5, 3, DATEADD('DAY', 1, CURRENT_DATE), '15:00:00', 'RETORNO', 'AGENDADA');

