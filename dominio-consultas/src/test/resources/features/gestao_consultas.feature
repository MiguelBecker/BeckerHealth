# language: pt
Funcionalidade: Gestão completa de consultas
  Como paciente,
  Quero agendar, cancelar e reagendar consultas médicas respeitando regras de disponibilidade,
  Para organizar meu atendimento.

  Cenário: Agendar consulta em horário livre
    Dado que existe um médico com horário livre às 10:00
    Quando o paciente agenda uma consulta para 10:00
    Então a consulta deve ser marcada com sucesso

  Cenário: Impedir agendamento em horário já ocupado
    Dado que o médico já possui uma consulta às 10:00
    Quando o paciente tenta agendar outra consulta para 10:00
    Então o sistema deve impedir o agendamento

  Cenário: Cancelar consulta com mais de 2h de antecedência
    Dado que o paciente possui uma consulta marcada para daqui a 5 horas
    Quando o paciente cancela a consulta
    Então o sistema deve cancelar sem aplicar penalidade

  Cenário: Cancelar consulta com menos de 2h de antecedência
    Dado que o paciente possui uma consulta marcada para daqui a 1 hora
    Quando o paciente cancela a consulta
    Então o sistema deve cancelar aplicando penalidade

  Cenário: Consulta de urgência sobrescreve rotina
    Dado que o médico já possui uma consulta de rotina às 10:00
    Quando o paciente agenda uma consulta de urgência às 10:00
    Então o sistema deve permitir o agendamento de urgência

  Cenário: Reagendar consulta somente se houver horário disponível
    Dado que o paciente possui uma consulta marcada para às 10:00
    E o médico tem horário livre às 14:00
    Quando o paciente reagenda a consulta para às 14:00
    Então o sistema deve atualizar a consulta para o novo horário
