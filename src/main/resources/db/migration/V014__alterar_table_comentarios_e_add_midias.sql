ALTER TABLE comentarios DROP COLUMN midia_url;

CREATE TABLE comentarios_midias (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    url VARCHAR(255) NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    comentario_id BIGINT NOT NULL,
    CONSTRAINT fk_comentario
        FOREIGN KEY (comentario_id)
        REFERENCES comentarios(id)
        ON DELETE CASCADE
);