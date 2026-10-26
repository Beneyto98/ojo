# OJO · Catálogo audiovisual

Proyecto de aprendizaje de Desarrollo de Aplicaciones Multiplataforma que conecta una interfaz web de películas, series y anime con un backend Java y una base de datos PostgreSQL.

OJO permite consultar un catálogo con portadas y fichas, y visualizar el estado de seguimiento, la puntuación y el comentario almacenados en la base de datos. Se presenta como proyecto de portfolio y se ejecuta en local; no requiere una demo publicada.

## Estado del proyecto

Primera versión funcional de **lectura de datos**. El backend consulta PostgreSQL mediante JDBC y entrega una respuesta JSON a la interfaz.

| Función | Estado |
| --- | --- |
| Consultar títulos desde PostgreSQL | Implementada |
| Mostrar fichas, sinopsis y portadas locales | Implementada |
| Leer estado, puntuación y comentario guardados en PostgreSQL | Implementada |
| Buscar por nombre y filtrar por estado en la interfaz | Implementada en JavaScript, sobre el catálogo cargado |
| Guardar cambios hechos desde la interfaz en PostgreSQL | Pendiente |
| Registro e inicio de sesión reales | Pendiente |
| Foro, notificaciones y otras pantallas sociales | Prototipo con datos de ejemplo |

Los cambios de estrellas, comentarios, estados y títulos creados desde la interfaz son temporales y se pierden al recargar. Para persistir datos en esta versión se utilizan consultas SQL. No es una aplicación multiusuario.

## Tecnologías

- Java 25 y el servidor HTTP del JDK (`HttpServer`).
- Maven para gestionar el proyecto y sus dependencias.
- PostgreSQL y JDBC, con pgJDBC 42.7.14.
- Gson 2.13.2 para convertir objetos Java a JSON.
- HTML, CSS y JavaScript para la interfaz.

No utiliza Spring Boot. La interfaz procede de una exportación de Claude Design y conserva su entorno de componentes `DCLogic`; no es un proyecto React convencional.

## Cómo funciona

1. El navegador solicita `/titulos` al servidor Java.
2. `TituloDAO` ejecuta una consulta SQL con `PreparedStatement`.
3. Cada fila se convierte en un objeto `Titulo` y se añade a una lista.
4. Gson convierte la lista en JSON.
5. La interfaz adapta los nombres de los campos y muestra el catálogo.

La base de datos usa `Viendo`, `Completada`, `Pendiente` y `Abandonada`. La interfaz los convierte a sus identificadores internos `watching`, `completed`, `pending` y `dropped`. Un estado nulo significa que el título no está en la lista personal; una puntuación de 0 significa que no se ha valorado.

## Organización

| Archivo o carpeta | Responsabilidad |
| --- | --- |
| `src/main/java/com/tienda/Titulo.java` | Modelo del título y getters |
| `src/main/java/com/tienda/TituloDAO.java` | Lectura de PostgreSQL y creación de objetos |
| `src/main/java/com/tienda/Servidor.java` | Punto de entrada HTTP, JSON, HTML y portadas |
| `src/main/java/com/tienda/Main.java` | Prueba de consulta desde consola |
| `src/main/resources/ojo.html` | Interfaz completa exportada y conectada al backend |
| `src/main/resources/index.html` | Página mínima para probar la conexión |
| `src/main/resources/portadas/` | Imágenes del catálogo de ejemplo |
| `sql/01_schema.sql` | Estructura de la tabla |
| `sql/02_datos_ejemplo.sql` | Seis registros para una instalación nueva |

## Ejecutar en local con IntelliJ

### 1. Requisitos

- JDK 25, correspondiente a la configuración actual de `pom.xml`.
- PostgreSQL instalado y en ejecución.
- IntelliJ IDEA con soporte Maven (o Maven instalado para compilar por terminal).
- pgAdmin u otro cliente SQL.

### 2. Crear la base de datos

En pgAdmin, crea una base de datos llamada `ojo`. Abre el Query Tool **conectado a esa base de datos** y ejecuta, en este orden:

1. `sql/01_schema.sql`.
2. `sql/02_datos_ejemplo.sql`.

Los scripts son para una base de datos nueva. No vuelvas a ejecutarlos sobre tu instalación actual: la tabla ya puede existir y los INSERT duplicarían registros. No contienen instrucciones para borrar tablas ni datos. Los datos incluidos son ejemplos, no una copia de la base de datos del autor.

Comprueba la carga con:

```sql
SELECT id, nombre, portada, estado, puntuacion
FROM titulos
ORDER BY id;
```

### 3. Abrir el proyecto y configurar la conexión

Abre la carpeta que contiene `pom.xml` en IntelliJ y carga el proyecto Maven. Selecciona JDK 25 como SDK del proyecto.

En **Run → Edit Configurations**, crea o selecciona la configuración de la clase `com.tienda.Servidor`. En **Environment variables** añade:

| Variable | Valor |
| --- | --- |
| `DB_URL` | `jdbc:postgresql://localhost:5432/ojo` |
| `DB_USER` | Tu usuario de PostgreSQL, por ejemplo `postgres` |
| `DB_PASSWORD` | Tu contraseña real, configurada únicamente en tu equipo |

Si no defines `DB_URL` o `DB_USER`, se utilizan los valores locales de la tabla. `DB_PASSWORD` es obligatoria. Si IntelliJ no muestra Environment variables, busca esa opción en Modify options de la configuración de ejecución.

El programa lee estas variables mediante `System.getenv`. **No carga automáticamente archivos `.env`.** No escribas contraseñas reales en las clases ni en el README. `.idea` y los archivos `.env` están excluidos de Git.

### 4. Arrancar

Ejecuta el método `main` de **`Servidor`**, no el de `Main`. Abre:

- [Interfaz de OJO](http://localhost:8080/ojo).
- [Catálogo JSON](http://localhost:8080/titulos).
- [Página de prueba](http://localhost:8080/app).
- [Portada de ejemplo](http://localhost:8080/portadas/rocky.jpg).

El servidor escucha en `localhost:8080`: es una configuración de desarrollo local. El programa debe permanecer ejecutándose. No abras el HTML directamente con doble clic, porque necesita las rutas del servidor.

Si tienes Maven instalado, puedes compilar desde la carpeta del proyecto con `mvn clean compile`; la forma de arranque documentada es la configuración de IntelliJ anterior. No se incluye Maven Wrapper ni un JAR ejecutable independiente.

## Añadir títulos y portadas

Guarda la imagen en `src/main/resources/portadas` y registra una ruta como `/portadas/mi-pelicula.jpg` en el campo `portada`. La ruta admite nombres con letras, números, guiones y guiones bajos, y extensiones minúsculas `jpg`, `jpeg`, `png` o `webp`.

Después de añadir archivos, reconstruye el proyecto y reinicia el servidor. Los cambios realizados solo en PostgreSQL se muestran al recargar la página, sin recompilar Java.

## Resolución de problemas

- **Falta DB_PASSWORD:** configura la variable en la ejecución de `Servidor` (y en la de `Main` si usas la prueba de consola).
- **Autenticación fallida:** revisa usuario, contraseña, puerto y base de datos.
- **Puerto ocupado:** detén la ejecución anterior antes de arrancar otra.
- **No se actualiza el HTML o una portada:** Build → Rebuild Project, reinicia y recarga el navegador con Ctrl + F5.
- **No aparece un título en Mi lista:** comprueba que tenga un estado permitido; los títulos sin estado siguen disponibles en el catálogo.

## Aprendizaje y alcance

El trabajo técnico del proyecto se centra en programación orientada a objetos, encapsulación, listas, JDBC, consultas parametrizadas, separación del acceso a datos mediante DAO, manejo de excepciones, HTTP y transformación de objetos a JSON.

La interfaz se creó inicialmente con ayuda de Claude Design. La conexión con PostgreSQL y la adaptación de sus datos se desarrollaron con asistencia de IA como parte del aprendizaje. El HTML exportado incluye recursos empaquetados; su tamaño y formato responden a esa exportación. Una mejora futura es separar el código de la interfaz en archivos editables.

Las portadas son material gráfico de terceros y no se presentan como diseño original del autor. Su inclusión no concede derechos de reutilización sobre esas imágenes.

## Próximos pasos

- Guardar estados, puntuaciones y comentarios desde la interfaz.
- Validar las peticiones y devolver errores claros.
- Separar catálogo y valoraciones si se incorporan usuarios.
- Reorganizar la exportación de la interfaz y añadir capturas del proyecto.

Autor: **Borja Beneyto**. Proyecto formativo de DAM.
