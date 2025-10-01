-- Cria o banco de dados
create database if not exists SistemaSaude;
use SistemaSaude;

-- ===========================
-- Tabela Usuario (base)
-- ===========================
create table if not exists Usuario (
    id int not null auto_increment,
    login varchar(50) not null unique,
    senha varchar(255) not null,
    nome varchar(100) not null,
    email varchar(100),
    tipo enum('ADMIN', 'MEDICO', 'PACIENTE', 'RECEPCIONISTA') not null,
    primary key (id)
);

-- ===========================
-- Tabelas de especialização
-- ===========================
create table if not exists Medico (
    id int not null,
    crm varchar(20) not null unique,
    especialidade varchar(100) not null,
    primary key (id),
    foreign key (id) references Usuario(id)
);

create table if not exists Paciente (
    id int not null,
    cpf varchar(14) not null unique,
    data_nascimento date,
    convenio varchar(100),
    primary key (id),
    foreign key (id) references Usuario(id)
);

-- ===========================
-- Tabela Consulta
-- ===========================
create table if not exists Consulta (
    id int not null auto_increment,
    id_paciente int not null,
    id_medico int not null,
    data_consulta date not null,
    hora_consulta time not null,
    tipo enum('ROTINA', 'RETORNO', 'URGENCIA') not null,
    status enum('AGENDADA', 'CONCLUIDA', 'CANCELADA') not null default 'AGENDADA',
    primary key (id),
    foreign key (id_paciente) references Paciente(id),
    foreign key (id_medico) references Medico(id)
);

-- ===========================
-- Tabela Prontuario
-- ===========================
create table if not exists Prontuario (
    id int not null auto_increment,
    id_consulta int not null,
    diagnostico text,
    prescricao text,
    observacoes text,
    primary key (id),
    foreign key (id_consulta) references Consulta(id)
);

-- ===========================
-- Tabela Exame
-- ===========================
create table if not exists Exame (
    id int not null auto_increment,
    id_consulta int not null,
    tipo varchar(100) not null,
    resultado text,
    data_exame date,
    liberado bool default false,
    primary key (id),
    foreign key (id_consulta) references Consulta(id)
);

-- ===========================
-- Tabela Receita Digital
-- ===========================
create table if not exists Receita (
    id int not null auto_increment,
    id_prontuario int not null,
    validade date not null,
    conteudo text not null,
    primary key (id),
    foreign key (id_prontuario) references Prontuario(id)
);

-- ===========================
-- Tabela Notificacao
-- ===========================
create table if not exists Notificacao (
    id int not null auto_increment,
    id_usuario int not null,
    mensagem varchar(255) not null,
    data_envio datetime not null,
    canal enum('EMAIL', 'SMS', 'APP') default 'EMAIL',
    primary key (id),
    foreign key (id_usuario) references Usuario(id)
);

-- ===========================
-- Tabela Relatorio
-- ===========================
create table if not exists Relatorio (
    id int not null auto_increment,
    tipo enum('MEDICO', 'ADMINISTRATIVO') not null,
    periodo_inicio date not null,
    periodo_fim date not null,
    conteudo text,
    primary key (id)
);
