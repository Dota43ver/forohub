CREATE TABLE autores (
    id BIGINT NOT NULL AUTO_INCREMENT,
    login VARCHAR(100) NOT NULL UNIQUE,
    clave VARCHAR(300) NOT NULL,
    PRIMARY KEY(id)
);

-- Creación de la tabla de topicos
-- Esta tabla contendrá todos los tópicos del foro.
CREATE TABLE topicos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(255) NOT NULL UNIQUE,
    mensaje TEXT NOT NULL,
    fecha_de_creacion DATETIME NOT NULL,
    -- El status se guarda como texto para mapear directamente al Enum en Java (ej: 'ABIERTO', 'CERRADO')
    status VARCHAR(100) NOT NULL,
    autor_id BIGINT NOT NULL,
    curso VARCHAR(100) NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT fk_topicos_autor_id
    FOREIGN KEY (autor_id) REFERENCES autores(id)
);