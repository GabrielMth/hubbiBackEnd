ALTER TABLE usuarios ADD COLUMN cliente_id BIGINT;

ALTER TABLE usuarios
ADD CONSTRAINT cliente_id
FOREIGN KEY (cliente_id)
REFERENCES clientes(id);