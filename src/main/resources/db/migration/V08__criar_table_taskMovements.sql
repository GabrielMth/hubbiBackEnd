CREATE TABLE task_movements (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    task_id BIGINT NOT NULL,
    coluna_origem ENUM('EM_ESPERA', 'EM_PRODUCAO', 'CONCLUIDO', 'EM_ANALISE', 'APROVADO'),
    coluna_destino ENUM('EM_ESPERA', 'EM_PRODUCAO', 'CONCLUIDO', 'EM_ANALISE', 'APROVADO'),
    data_movimentacao DATETIME NOT NULL,
    usuario_id BIGINT(20),

    CONSTRAINT fk_task_movement_task FOREIGN KEY (task_id) REFERENCES tasks(id),
    CONSTRAINT fk_task_movement_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;