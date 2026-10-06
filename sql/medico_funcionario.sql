USE medicenter_db;

CREATE TABLE IF NOT EXISTS medico (
                                      id            BIGINT       NOT NULL AUTO_INCREMENT,
                                      nome          VARCHAR(120) NOT NULL,
    cpf           VARCHAR(11)  NOT NULL,
    crm           VARCHAR(20)  NOT NULL,
    especialidade VARCHAR(80)  NOT NULL,
    telefone      VARCHAR(15),
    email         VARCHAR(120),
    data_cadastro DATETIME(6)  NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_medico_cpf UNIQUE (cpf),
    CONSTRAINT uk_medico_crm UNIQUE (crm)
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS funcionario (
                                           id             BIGINT       NOT NULL AUTO_INCREMENT,
                                           nome           VARCHAR(120) NOT NULL,
    cpf            VARCHAR(11)  NOT NULL,
    cargo          VARCHAR(80)  NOT NULL,
    data_admissao  DATE         NOT NULL,
    telefone       VARCHAR(15),
    email          VARCHAR(120),
    data_cadastro  DATETIME(6)  NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_funcionario_cpf UNIQUE (cpf)
    ) ENGINE=InnoDB;