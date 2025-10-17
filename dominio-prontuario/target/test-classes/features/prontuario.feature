# language: pt
Funcionalidade: 2 - Prontuário médico integrado
  Como médico, quero registrar diagnósticos, prescrições e solicitar exames 
  no prontuário do paciente, para manter um histórico clínico consolidado.

  Regra: Apenas médicos podem editar o prontuário
    Cenário: Médico edita o prontuário com sucesso
      Dado que um médico autenticado acessa o prontuário do paciente
      Quando o médico registra um diagnóstico "Hipertensão"
      Então o prontuário deve conter o diagnóstico "Hipertensão"

    Cenário: Paciente tenta editar o prontuário
      Dado que um paciente autenticado acessa o prontuário
      Quando o paciente tenta registrar um diagnóstico
      Então uma exceção deve ser lançada informando que apenas médicos podem editar

  Regra: Exames precisam de consulta vinculada
    Cenário: Solicitar exame com consulta vinculada
      Dado que um médico autenticado acessa o prontuário com consulta vinculada
      Quando o médico solicita um exame "Hemograma"
      Então o exame "Hemograma" deve ser registrado no prontuário

    Cenário: Solicitar exame sem consulta vinculada
      Dado que um médico autenticado acessa o prontuário sem consulta vinculada
      Quando o médico solicita um exame "Raio-X"
      Então uma exceção deve ser lançada informando que não há consulta vinculada

  Regra: Resultados só após liberação
    Cenário: Médico libera resultado de exame
      Dado que um exame "Hemograma" foi registrado e está pendente de liberação
      Quando o médico libera o resultado "Normal"
      Então o paciente deve conseguir visualizar o resultado "Normal"

    Cenário: Resultado não liberado
      Dado que um exame "Hemograma" foi registrado e está pendente de liberação
      Quando o paciente tenta visualizar o resultado
      Então uma exceção deve ser lançada informando que o resultado ainda não foi liberado

  Regra: Prescrições exigem validade e assinatura
    Cenário: Criar prescrição válida
      Dado que um médico autenticado acessa o prontuário
      Quando o médico cria uma prescrição "Atorvastatina 10mg" com validade de 30 dias e assinatura digital
      Então a prescrição deve ser registrada com sucesso

    Cenário: Criar prescrição sem validade
      Dado que um médico autenticado acessa o prontuário
      Quando o médico cria uma prescrição "Atorvastatina 10mg" sem validade
      Então uma exceção deve ser lançada informando que a validade é obrigatória
