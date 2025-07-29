ForoHub API
API REST para un foro de discusión, desarrollada como parte del Challenge de Alura Latam. La API permite a los usuarios registrarse, autenticarse y gestionar tópicos de discusión (crear, leer, actualizar y eliminar).

Características
Autenticación JWT: Seguridad basada en tokens (JSON Web Tokens) para proteger los endpoints.

CRUD de Tópicos: Gestión completa de los tópicos del foro.

Registro de Usuarios: Creación de nuevas cuentas de autor.

Autorización: Verificación de permisos para asegurar que solo los autores puedan modificar o eliminar sus propios tópicos.

Paginación y Filtrado: Listado de tópicos paginado y con opción de filtrado por curso y año.

Migraciones de Base de Datos: Gestión del esquema de la base de datos con Flyway.

🛠️ Tecnologías Utilizadas
Lenguaje: Java 17

Framework: Spring Boot 3

Base de Datos: MySQL

Seguridad: Spring Security

ORM: Spring Data JPA / Hibernate

Migraciones: Flyway

Autenticación: JSON Web Tokens (JWT)

Validaciones: Spring Validation

Gestión de dependencias: Maven

Utilidades: Lombok

🚀 Pre-requisitos
Antes de comenzar, asegúrate de tener instalado en tu entorno de desarrollo:

JDK 17 o superior.

Apache Maven 3.8 o superior.

MySQL 8.0 o una instancia de Docker con la imagen de MySQL.

Un cliente de API como Postman o Insomnia para probar los endpoints.
