USE medicenter_db;

CREATE TABLE IF NOT EXISTS usuario (
                                       id            BIGINT       NOT NULL AUTO_INCREMENT,
                                       login         VARCHAR(60)  NOT NULL,
    senha         VARCHAR(100) NOT NULL,
    perfil        VARCHAR(20)  NOT NULL,
    ativo         BOOLEAN      NOT NULL DEFAULT TRUE,
    data_cadastro DATETIME(6)  NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_usuario_login UNIQUE (login)
    ) ENGINE=InnoDB;

ALTER TABLE paciente
    ADD COLUMN usuario_id BIGINT NULL,
    ADD CONSTRAINT uk_paciente_usuario UNIQUE (usuario_id),
    ADD CONSTRAINT fk_paciente_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (id);

ALTER TABLE medico
    ADD COLUMN usuario_id BIGINT NULL,
    ADD CONSTRAINT uk_medico_usuario UNIQUE (usuario_id),
    ADD CONSTRAINT fk_medico_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (id);

ALTER TABLE funcionario
    ADD COLUMN usuario_id BIGINT NULL,
    ADD CONSTRAINT uk_funcionario_usuario UNIQUE (usuario_id),
    ADD CONSTRAINT fk_funcionario_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (id);