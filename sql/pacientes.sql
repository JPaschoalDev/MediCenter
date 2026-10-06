CREATE DATABASE IF NOT EXISTS medicenter_db
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE medicenter_db;

CREATE TABLE IF NOT EXISTS paciente (
                                        id              BIGINT       NOT NULL AUTO_INCREMENT,
                                        nome            VARCHAR(120) NOT NULL,
    cpf             VARCHAR(11)  NOT NULL,
    data_nascimento DATE         NOT NULL,
    telefone        VARCHAR(15),
    email           VARCHAR(120),
    endereco        VARCHAR(200),
    data_cadastro   DATETIME(6)  NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_paciente_cpf UNIQUE (cpf)
    ) ENGINE=InnoDB;