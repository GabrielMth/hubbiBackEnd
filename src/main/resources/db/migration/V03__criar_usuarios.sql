CREATE TABLE usuarios (
    user_id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(60) NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;