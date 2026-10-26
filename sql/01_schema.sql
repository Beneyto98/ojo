-- Ejecutar conectado a una base de datos ojo nueva.
-- Si la tabla ya existe, este script falla sin borrar datos.
CREATE TABLE titulos (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    anio SMALLINT NOT NULL,
    genero VARCHAR(80) NOT NULL,
    sinopsis TEXT,
    portada VARCHAR(500),
    estado VARCHAR(20),
    puntuacion SMALLINT NOT NULL DEFAULT 0,
    comentario TEXT NOT NULL DEFAULT '',
    CONSTRAINT estado_valido CHECK (estado IN ('Viendo', 'Completada', 'Pendiente', 'Abandonada')),
    CONSTRAINT puntuacion_valida CHECK (puntuacion BETWEEN 0 AND 5)
);
