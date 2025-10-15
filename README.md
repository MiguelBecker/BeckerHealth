# BeckerHealth - Sistema de Gestão Hospitalar

## Arquitetura Domain-Driven Design (DDD) Multi-módulo

Este projeto foi estruturado seguindo os princípios de **Domain-Driven Design (DDD)** com uma arquitetura **multi-módulo Maven**, separando claramente as responsabilidades entre bounded contexts e camadas de aplicação.

---

## 📁 Estrutura de Módulos

### 1. **Módulos de Domínio** (Bounded Contexts)

#### `dominio-compartilhado`
- **Propósito**: Entidades e Value Objects compartilhados entre múltiplos bounded contexts
- **Conteúdo**:
  - `Usuario` - Classe base para usuários do sistema
  - `Medico` - Especialização de Usuario para médicos
  - `Paciente` - Especialização de Usuario para pacientes
- **Package**: `dev.beckerhealth.dominio.compartilhado.usuario`

#### `dominio-consultas`
- **Propósito**: Bounded Context de Agendamento e Gestão de Consultas Médicas
- **Conteúdo**:
  - `Consulta` - Entidade de agendamento de consultas
  - `ConsultaRepository` - Interface de repositório (implementação na infraestrutura)
- **Package**: `dev.beckerhealth.dominio.consultas`

#### `dominio-prontuario`
- **Propósito**: Bounded Context de Prontuário Eletrônico
- **Conteúdo**:
  - `Prontuario` - Entidade de prontuário médico
  - `ProntuarioRepository` - Interface de repositório
- **Package**: `dev.beckerhealth.dominio.prontuario`

#### `dominio-notificacao`
- **Propósito**: Bounded Context de Sistema de Notificações
- **Conteúdo**:
  - `Notificacao` - Entidade de notificações
  - `NotificacaoRepository` - Interface de repositório
- **Package**: `dev.beckerhealth.dominio.notificacao`

#### `dominio-relatorios`
- **Propósito**: Bounded Context de Geração de Relatórios
- **Conteúdo**:
  - `Relatorio` - Entidade de relatórios
  - `RelatorioRepository` - Interface de repositório
- **Package**: `dev.beckerhealth.dominio.relatorios`

---

### 2. **Camada de Aplicação**

#### `aplicacao`
- **Propósito**: Casos de uso e DTOs (Data Transfer Objects)
- **Conteúdo**:
  - **DTOs**:
    - `ConsultaResumo` - DTO resumido para consultas
    - `ConsultaResumoExpandido` - DTO expandido com mais detalhes
  - **Casos de Uso**:
    - `AgendarConsulta` - Caso de uso para agendamento de consultas
- **Package**: `dev.beckerhealth.aplicacao`

---

### 3. **Camada de Infraestrutura**

#### `infraestrutura`
- **Propósito**: Implementações técnicas (persistência, repositórios JPA)
- **Conteúdo**:
  - Implementações JPA dos repositórios de domínio
  - Configurações de banco de dados
  - Migrações Flyway
- **Package**: `dev.beckerhealth.infraestrutura`

---

### 4. **Camadas de Apresentação**

#### `apresentacao-backend`
- **Propósito**: API REST - Controladores e Formulários de entrada
- **Conteúdo**:
  - `ConsultaController` - Endpoints REST para consultas
  - `AgendarConsultaForm` - Formulário de entrada para agendamento
- **Package**: `dev.beckerhealth.apresentacao.backend`

#### `apresentacao-vaadin`
- **Propósito**: Interface Web Vaadin - Views e Formulários
- **Conteúdo**:
  - `BeckerHealthApplication` - Classe principal Spring Boot
  - `MainView` - Layout principal da aplicação
  - `ConsultasView` - View de consultas
  - `ProntuariosView` - View de prontuários
  - `NotificacoesView` - View de notificações
  - `RelatoriosView` - View de relatórios
- **Package**: `dev.beckerhealth.apresentacao.vaadin`

---

## 🏗️ Arquitetura DDD

### Bounded Contexts Identificados

1. **Consultas** - Gestão de agendamentos e consultas médicas
2. **Prontuário** - Histórico médico e registros de atendimento
3. **Notificação** - Sistema de notificações e alertas
4. **Relatórios** - Geração e gestão de relatórios

### Padrões Implementados

- **Aggregate Root**: Cada entidade principal é um agregado
- **Repository Pattern**: Interfaces no domínio, implementações na infraestrutura
- **Domain Services**: Casos de uso na camada de aplicação
- **DTO Pattern**: Transferência de dados entre camadas
- **Dependency Inversion**: Domínio não depende de infraestrutura

---

## 🚀 Como Executar

### Pré-requisitos
- Java 17+
- Maven 3.6+

### Compilar o Projeto
```bash
mvn clean install
```

### Executar a Aplicação
```bash
cd apresentacao-vaadin
mvn spring-boot:run
```

### Acessar a Aplicação
- **Interface Web Vaadin**: http://localhost:8080
- **Console H2**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:beckerhealth`
  - Username: `sa`
  - Password: (vazio)

---

## 📊 Estrutura de Packages

```
dev.beckerhealth
├── dominio/
│   ├── compartilhado/
│   │   └── usuario/
│   ├── consultas/
│   ├── prontuario/
│   ├── notificacao/
│   └── relatorios/
├── aplicacao/
│   └── consultas/
│       ├── dto/
│       └── AgendarConsulta.java
├── infraestrutura/
│   ├── consultas/
│   ├── prontuario/
│   ├── notificacao/
│   ├── relatorios/
│   └── compartilhado/
└── apresentacao/
    ├── backend/
    │   └── consultas/
    └── vaadin/
```

---

## 🔧 Tecnologias Utilizadas

- **Spring Boot 3.5.6** - Framework principal
- **Vaadin 24.9.1** - Framework para interface web
- **H2 Database** - Banco de dados em memória
- **JPA/Hibernate** - ORM para persistência
- **Flyway** - Migração de banco de dados
- **Lombok** - Redução de boilerplate
- **Maven** - Gerenciamento de dependências

---

## 📝 Próximos Passos

- [ ] Implementar testes unitários e de integração
- [ ] Adicionar autenticação e autorização (Spring Security)
- [ ] Implementar eventos de domínio
- [ ] Adicionar validações mais robustas
- [ ] Implementar cache (Redis)
- [ ] Adicionar documentação da API (Swagger/OpenAPI)
- [ ] Implementar barramento de eventos
- [ ] Adicionar logs estruturados

---

## 👥 Desenvolvido por

Projeto desenvolvido para a disciplina de Requisitos de Software.

**Bounded Contexts Implementados:**
- ✅ Consultas
- ✅ Prontuário
- ✅ Notificação
- ✅ Relatórios

