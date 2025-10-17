Feature: Notificações inteligentes de atendimento
  Como paciente e médico,
  Quero receber notificações automáticas sobre consultas e exames,
  Para me manter atualizado sobre meus compromissos e responsabilidades.

  Scenario: Paciente recebe lembrete 24h antes da consulta
    Given existe uma consulta marcada para amanhã
    When o sistema gera a notificação
    Then o paciente deve receber uma notificação com mensagem "Lembrete: você tem uma consulta amanhã"

  Scenario: Médico recebe relatório diário de agenda
    Given o médico possui consultas agendadas para hoje
    When o sistema gera as notificações do dia
    Then o médico deve receber uma notificação com mensagem "Relatório diário de pacientes"

  Scenario: Notificação de exame liberado
    Given um exame foi liberado pelo médico
    When o sistema gera a notificação
    Then o paciente deve receber uma notificação com mensagem "Resultado de exame disponível"
