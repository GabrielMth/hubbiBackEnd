CREATE TABLE kanban_board (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    cliente_id BIGINT UNIQUE,
    CONSTRAINT fk_kanban_cliente FOREIGN KEY (cliente_id)
        REFERENCES clientes(id)
        ON DELETE CASCADE
);