CREATE TABLE comentarios (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    texto TEXT NOT NULL,
    midia_url VARCHAR(255),
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    autor_id BIGINT NOT NULL,
    task_id BIGINT NOT NULL,

    CONSTRAINT fk_comentario_autor FOREIGN KEY (autor_id) REFERENCES usuarios(user_id),
    CONSTRAINT fk_comentario_task FOREIGN KEY (task_id) REFERENCES tasks(id)
);