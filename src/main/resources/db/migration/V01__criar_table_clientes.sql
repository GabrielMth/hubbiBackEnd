CREATE TABLE clientes (
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(60) NOT NULL,
    documento VARCHAR(50) NOT NULL,
    celular VARCHAR(25) NOT NULL,
    telefone VARCHAR(25),
    rua VARCHAR(50),
    numero VARCHAR(25),
    bairro VARCHAR(50),
    cep VARCHAR(50),
    cidade VARCHAR(50),
    estado VARCHAR(50),
    data_cadastro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ativo BOOLEAN NOT NULL
);