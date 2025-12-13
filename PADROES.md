# Padrões de Projeto e Arquitetura - BeckerHealth

## 📋 Visão Geral

Este documento descreve os padrões de projeto e arquitetura implementados no sistema BeckerHealth, seguindo os princípios de Domain-Driven Design (DDD) e Clean Architecture.

---

## 🏗️ Arquitetura Geral

### Arquitetura em Camadas (Clean Architecture)

```
┌─────────────────────────────────────┐
│         Camada de Apresentação      │
│  ┌─────────────────────────────────┐ │
│  │   apresentacao-vaadin          │ │
│  │   apresentacao-frontend        │ │
│  │   apresentacao-backend         │ │
│  └─────────────────────────────────┘ │
└─────────────────────────────────────┘
                │
                ▼
┌─────────────────────────────────────┐
│       Camada de Aplicação          │
│  ┌─────────────────────────────────┐ │
│  │        aplicacao               │ │
│  └─────────────────────────────────┘ │
└─────────────────────────────────────┘
                │
                ▼
┌─────────────────────────────────────┐
│         Camada de Domínio          │
│  ┌─────────────────────────────────┐ │
│  │   dominio-consultas           │ │
│  │   dominio-prontuario          │ │
│  │   dominio-notificacao         │ │
│  │   dominio-relatorios          │ │
│  │   dominio-compartilhado       │ │
│  └─────────────────────────────────┘ │
└─────────────────────────────────────┘
                │
                ▼
┌─────────────────────────────────────┐
│     Camada de Infraestrutura       │
│  ┌─────────────────────────────────┐ │
│  │     infraestrutura             │ │
│  └─────────────────────────────────┘ │
└─────────────────────────────────────┘
```

### Bounded Contexts (DDD)

O sistema está dividido em **Bounded Contexts** independentes:

1. **Consultas** - Gestão de agendamentos e consultas médicas
2. **Prontuário** - Histórico médico e registros de atendimento
3. **Notificação** - Sistema de notificações e alertas
4. **Relatórios** - Geração e gestão de relatórios

---

## 🎯 Padrões de Domínio (Domain Layer)

### 1. Entity (Entidade)

**Propósito**: Representa objetos de negócio com identidade própria e ciclo de vida.

**Implementação**:
```java
public class Consulta {
    private ConsultaId id;  // Identidade única
    private Paciente paciente;
    private Medico medico;
    private LocalDate dataConsulta;
    private LocalTime horaConsulta;
    private TipoConsulta tipo;
    private StatusConsulta status;

    // Getters e Setters com validação
    public void setPaciente(Paciente paciente) {
        notNull(paciente, "O paciente não pode ser nulo");
        this.paciente = paciente;
    }
}
```

**Características**:
- Possui identidade única (`ConsultaId`)
- Contém lógica de negócio
- Validação de invariantes
- Estado mutável controlado

### 2. Value Object (Objeto de Valor)

**Propósito**: Representa conceitos imutáveis sem identidade própria.

**Implementação**:
```java
public class Cpf {
    private final String codigo;

    public Cpf(String codigo) {
        notNull(codigo, "O CPF não pode ser nulo");
        this.codigo = codigo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cpf cpf = (Cpf) o;
        return codigo.equals(cpf.codigo);
    }

    @Override
    public int hashCode() {
        return codigo.hashCode();
    }
}
```

**Características**:
- Imutável (`final`)
- Igualdade baseada em valor (não referência)
- Não possui identidade própria
- Criado através de Factory (`CpfFabrica`)

### 3. Factory Pattern

**Propósito**: Encapsular a criação de objetos complexos.

**Implementação**:
```java
public class CpfFabrica {
    public Cpf construir(String codigo) {
        notNull(codigo, "O código não pode ser nulo");
        if (codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF não pode estar vazio");
        }
        return new Cpf(codigo);
    }
}
```

### 4. Repository Pattern

**Propósito**: Abstrair o acesso aos dados, mantendo a camada de domínio independente da infraestrutura.

**Interface no Domínio**:
```java
public interface ConsultaRepository {
    Consulta salvar(Consulta consulta);
    Optional<Consulta> buscarPorId(ConsultaId id);
    List<Consulta> listarTodas();
    List<Consulta> buscarPorData(LocalDate data);
    void deletar(ConsultaId id);
}
```

**Implementação na Infraestrutura**:
```java
@Repository
class ConsultaRepositoryImpl implements ConsultaRepository {
    @Autowired
    ConsultaJpaRepository repositorio;

    @Override
    public Consulta salvar(Consulta consulta) {
        var consultaJpa = mapeador.map(consulta, ConsultaJpa.class);
        var consultaSalva = repositorio.save(consultaJpa);
        return mapeador.map(consultaSalva, Consulta.class);
    }
}
```

---

## 🔧 Padrões de Aplicação (Application Layer)

### 1. Application Service (Serviço de Aplicação)

**Propósito**: Orquestrar casos de uso e coordenar operações entre objetos de domínio.

**Implementação**:
```java
@Service
public class AgendarConsulta {
    private final ConsultaRepository consultaRepository;
    private final EventoBarramento eventoBarramento;
    private final ProcessamentoConsultaAgendada processamentoConsultaAgendada;

    public ConsultaResumo executar(Long pacienteId, Long medicoId,
            LocalDate dataConsulta, LocalTime horaConsulta, Consulta.TipoConsulta tipo) {
        // Validação
        ValidacaoConsultaStrategy validacaoStrategy = new ValidacaoHorarioStrategy();
        validacaoStrategy.validar(dataConsulta, horaConsulta, tipo);

        // Construção da entidade
        Consulta consulta = new Consulta();
        consulta.setPaciente(new Paciente(pacienteId));
        consulta.setMedico(new Medico(medicoId));
        consulta.setDataConsulta(dataConsulta);
        consulta.setHoraConsulta(horaConsulta);
        consulta.setTipo(tipo);
        consulta.setStatus(Consulta.StatusConsulta.AGENDADA);

        // Persistência
        Consulta consultaSalva = consultaRepository.salvar(consulta);

        // Pós-processamento
        processamentoConsultaAgendada.processar(consultaSalva);

        return mapearParaResumo(consultaSalva);
    }
}
```

### 2. DTO Pattern (Data Transfer Object)

**Propósito**: Transferir dados entre camadas sem expor objetos de domínio.

**Implementação**:
```java
public class ConsultaResumo {
    private Long id;
    private String pacienteNome;
    private String pacienteCpf;
    private String medicoNome;
    private LocalDate dataConsulta;
    private LocalTime horaConsulta;
    private String tipo;
    private String status;

    // Builder Pattern para construção fluída
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        // ... campos do builder

        public ConsultaResumo build() {
            return new ConsultaResumo(id, pacienteNome, pacienteCpf, medicoNome,
                                    dataConsulta, horaConsulta, tipo, status);
        }
    }
}
```

### 3. Strategy Pattern

**Propósito**: Permitir a variação de algoritmos de validação.

**Interface**:
```java
public interface ValidacaoConsultaStrategy {
    void validar(LocalDate dataConsulta, LocalTime horaConsulta, Consulta.TipoConsulta tipo);
}
```

**Implementações**:
```java
public class ValidacaoHorarioStrategy implements ValidacaoConsultaStrategy {
    @Override
    public void validar(LocalDate dataConsulta, LocalTime horaConsulta, Consulta.TipoConsulta tipo) {
        if (dataConsulta.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("A data da consulta não pode ser no passado");
        }
        if (horaConsulta.isBefore(LocalTime.of(8, 0)) || horaConsulta.isAfter(LocalTime.of(18, 0))) {
            throw new IllegalArgumentException("O horário deve estar entre 8h e 18h");
        }
    }
}
```

### 4. Template Method Pattern

**Propósito**: Definir o esqueleto de um algoritmo, permitindo que subclasses implementem etapas específicas.

**Classe Abstrata**:
```java
public abstract class ProcessamentoConsultaTemplate {
    public final void processar(Consulta consulta) {
        validarPreProcessamento(consulta);
        executarProcessamento(consulta);
        validarPosProcessamento(consulta);
    }

    protected abstract void executarProcessamento(Consulta consulta);
    protected abstract void validarPosProcessamento(Consulta consulta);

    protected void validarPreProcessamento(Consulta consulta) {
        // Validação comum
    }
}
```

**Implementação**:
```java
public class ProcessamentoConsultaAgendada extends ProcessamentoConsultaTemplate {
    @Override
    protected void executarProcessamento(Consulta consulta) {
        ConsultaAgendadaEvento evento = new ConsultaAgendadaEvento(consulta);
        eventoBarramento.postar(evento);
    }

    @Override
    protected void validarPosProcessamento(Consulta consulta) {
        if (consulta.getStatus() != Consulta.StatusConsulta.AGENDADA) {
            throw new IllegalStateException("Consulta não foi agendada corretamente");
        }
    }
}
```

---

## 🎨 Padrões de Apresentação (Presentation Layer)

### 1. MVC Pattern (Model-View-Controller)

**Vaadin Views como Controllers**:
```java
@PageTitle("Consultas")
@Route(value = "consultas", layout = MainView.class)
public class ConsultasView extends VerticalLayout {

    private final ConsultaServicoAplicacao consultaServicoAplicacao;
    private final Grid<ConsultaResumo> grid;

    public ConsultasView(ConsultaServicoAplicacao consultaServicoAplicacao) {
        this.consultaServicoAplicacao = consultaServicoAplicacao;
        this.grid = new Grid<>(ConsultaResumo.class, false);

        configureGrid();
        add(grid);
    }

    private void configureGrid() {
        grid.addColumn(ConsultaResumo::getPacienteNome).setHeader("Paciente");
        grid.addColumn(ConsultaResumo::getMedicoNome).setHeader("Médico");
        grid.addColumn(ConsultaResumo::getDataConsulta).setHeader("Data");

        updateList();
    }

    private void updateList() {
        grid.setItems(consultaServicoAplicacao.pesquisarResumos());
    }
}
```

---

## 🔌 Padrões de Infraestrutura (Infrastructure Layer)

### 1. Adapter Pattern

**Propósito**: Adaptar interfaces de terceiros às interfaces do domínio.

**Mapeamento JPA**:
```java
@Repository
class ConsultaRepositoryImpl implements ConsultaRepository {
    @Autowired
    ConsultaJpaRepository jpaRepository;

    @Autowired
    JpaMapeador mapeador;

    @Override
    public Consulta salvar(Consulta consulta) {
        // Converte domínio para JPA
        var consultaJpa = mapeador.map(consulta, ConsultaJpa.class);
        var consultaSalva = jpaRepository.save(consultaJpa);
        // Converte JPA para domínio
        return mapeador.map(consultaSalva, Consulta.class);
    }
}
```

### 2. Dependency Injection Container

**Registro Manual de Beans**:
```java
@SpringBootApplication(scanBasePackages = {
    "dev.beckerhealth.apresentacao.vaadin",
    "dev.beckerhealth.aplicacao",
    "dev.beckerhealth.infraestrutura"
})
public class BeckerHealthApplication {

    @Bean
    public ConsultaServicoAplicacao consultaServicoAplicacao(ConsultaRepositorioAplicacao repositorio) {
        return new ConsultaServicoAplicacao(repositorio);
    }
}
```

---

## 📡 Padrões de Comunicação e Eventos

### 1. Observer Pattern (Event Bus)

**Barramento de Eventos**:
```java
public interface EventoBarramento {
    void postar(Evento evento);
    void registrar(OuvinteEvento ouvinte);
}

@Component
public class EventoBarramentoImpl implements EventoBarramento {
    private final List<OuvinteEvento> ouvintes = new ArrayList<>();

    @Override
    public void postar(Evento evento) {
        ouvintes.forEach(ouvinte -> ouvinte.processar(evento));
    }
}
```

### 2. Domain Events

**Evento de Domínio**:
```java
public class ConsultaAgendadaEvento implements Evento {
    private final Consulta consulta;
    private final LocalDateTime timestamp;

    public ConsultaAgendadaEvento(Consulta consulta) {
        this.consulta = consulta;
        this.timestamp = LocalDateTime.now();
    }
}
```

---

## 🛡️ Padrões de Segurança e Validação

### 1. Validation Pattern

**Validação com Apache Commons**:
```java
import static org.apache.commons.lang3.Validate.notNull;

public class Consulta {
    public void setPaciente(Paciente paciente) {
        notNull(paciente, "O paciente não pode ser nulo");
        this.paciente = paciente;
    }
}
```

### 2. Guard Clauses

**Validações no início dos métodos**:
```java
public ConsultaResumo executar(Long pacienteId, Long medicoId, LocalDate dataConsulta,
                              LocalTime horaConsulta, Consulta.TipoConsulta tipo) {
    // Validações iniciais
    if (pacienteId == null || medicoId == null) {
        throw new IllegalArgumentException("IDs não podem ser nulos");
    }

    // Lógica do negócio
    // ...
}
```

---

## 📊 Padrões de Persistência

### 1. JPA Repository Pattern

**Interface JPA**:
```java
@Repository
public interface ConsultaJpaRepository extends JpaRepository<ConsultaJpa, Long> {
    List<ConsultaJpa> findByDataConsulta(LocalDate data);
    List<ConsultaJpa> findByPacienteId(Long pacienteId);
    List<ConsultaJpa> findByMedicoId(Long medicoId);
    List<ConsultaJpa> findByStatus(ConsultaJpa.StatusConsulta status);
}
```

### 2. Flyway Migrations

**Estrutura de Migrações**:
```
db/migration/
├── V1__Create_Usuario_Table.sql
├── V2__Create_Consulta_Table.sql
└── V3__Create_Prontuario_Table.sql
```

---

## 🔄 Princípios SOLID Implementados

### 1. Single Responsibility Principle (SRP)
- Cada classe tem uma única responsabilidade
- `ConsultaRepository` só gerencia persistência
- `AgendarConsulta` só orquestra o caso de uso

### 2. Open/Closed Principle (OCP)
- Interfaces permitem extensão sem modificação
- Strategy Pattern para diferentes validações

### 3. Liskov Substitution Principle (LSP)
- Implementações substituem interfaces sem quebrar contrato
- `ValidacaoHorarioStrategy` pode ser substituída por outras validações

### 4. Interface Segregation Principle (ISP)
- Interfaces específicas por contexto
- `ConsultaRepository` vs `ConsultaRepositorioAplicacao`

### 5. Dependency Inversion Principle (DIP)
- Domínio não depende de infraestrutura
- Injeção de dependências via construtor

---

## 📈 Benefícios da Arquitetura

### Manutenibilidade
- Separação clara de responsabilidades
- Código organizado por bounded contexts
- Facilita testes unitários e integração

### Escalabilidade
- Módulos independentes
- Possibilidade de deploy separado
- Facilita distribuição em microserviços

### Testabilidade
- Interfaces permitem mocks
- Injeção de dependências facilita testes
- Validações isoladas por estratégia

### Evolução
- Mudanças isoladas por módulo
- Contratos claros entre camadas
- Facilita refatoração

---

## 🎯 Conclusão

O projeto BeckerHealth implementa uma arquitetura robusta baseada em DDD e Clean Architecture, utilizando diversos padrões de projeto que promovem:

- **Separação de responsabilidades** clara
- **Manutenibilidade** e **evolutibilidade** do código
- **Testabilidade** através de interfaces e injeção de dependências
- **Escalabilidade** via módulos independentes
- **Qualidade** através dos princípios SOLID

Esta abordagem garante que o sistema possa evoluir de forma sustentável, mantendo a complexidade controlada mesmo com o crescimento do projeto.

