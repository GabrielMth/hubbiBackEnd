CREATE TABLE tasks (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT NOT NULL,
    prioridade ENUM('ALTA', 'MEDIA', 'BAIXA') DEFAULT 'BAIXA',
    kanban_board_id BIGINT,
    autor_id BIGINT(20),

    CONSTRAINT fk_task_kanban FOREIGN KEY (kanban_board_id) REFERENCES kanban_board(id),
    CONSTRAINT fk_task_autor FOREIGN KEY (autor_id) REFERENCES usuarios(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;