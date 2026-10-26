-- Datos de demostracion, no una exportacion de la base de datos del autor.
-- Ejecutar una sola vez despues de 01_schema.sql para evitar duplicados.
INSERT INTO titulos (nombre, tipo, anio, genero, sinopsis, portada, estado, puntuacion, comentario)
VALUES
('Rocky', 'Película', 1976, 'Drama deportivo', 'Un boxeador recibe la oportunidad de enfrentarse al campeón del mundo.', '/portadas/rocky.jpg', 'Completada', 5, 'Una película que volvería a ver.'),
('One Piece', 'Anime', 1999, 'Aventuras', 'Luffy y su tripulación buscan el legendario tesoro One Piece.', '/portadas/onepiece.jpg', 'Viendo', 0, ''),
('Breaking Bad', 'Serie', 2008, 'Drama criminal', 'Un profesor de química se adentra en el mundo del crimen.', '/portadas/breakingbad.webp', 'Pendiente', 0, ''),
('Origen', 'Película', 2010, 'Ciencia ficción', 'Un equipo se introduce en los sueños para implantar una idea.', '/portadas/origen.jpg', 'Pendiente', 0, ''),
('Gladiator', 'Película', 2000, 'Drama histórico', 'Un general romano traicionado se convierte en gladiador.', '/portadas/gladiator.jpg', 'Completada', 4, ''),
('Shingeki no Kyojin', 'Anime', 2013, 'Fantasía oscura', 'La humanidad se refugia tras murallas para protegerse de los titanes.', '/portadas/shingeki.jpg', 'Pendiente', 0, '');
