# channel-ibk0-users-v1

Proyecto Spring Boot para gestión de usuarios (API REST). A continuación se detallan
instrucciones para compilar, ejecutar y probar la aplicación, así como información
sobre la base de datos en memoria y los artefactos del proyecto.

Requisitos
- Java 17 (o cualquier JDK >= 11 compatible con el proyecto)
- Maven (se incluye el wrapper `mvnw`/`mvnw.cmd`)

Build
En Windows (PowerShell o cmd):

```
mvnw.cmd clean package
```

En sistemas Unix/macOS:

```
./mvnw clean package
```

Ejecución
Después de compilar, ejecutar el JAR generado:

Windows:

```
java -jar target\channel-ibk0-users-v1-0.0.1-SNAPSHOT.jar
```

La aplicación expone por defecto el puerto 8080.

Base de datos
- Se usa H2 en memoria (dependencia `com.h2database:h2`) y la URL configurada es `jdbc:h2:mem:usersdb`.
- El script de creación de esquema está en `src/main/resources/schema.sql` y `spring.jpa.hibernate.ddl-auto` está configurado como `create-drop`.
- Console H2 disponible en: http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:mem:usersdb`, usuario: `sa`, contraseña vacía).

API / Documentación
- Swagger UI (Springdoc) disponible en: http://localhost:8080/swagger-ui.html
- OpenAPI JSON en: http://localhost:8080/v3/api-docs

Tests
Para ejecutar los tests unitarios/integración:

Windows:

```
mvnw.cmd test
```

Puntos a destacar
- Persistencia JPA: `spring-boot-starter-data-jpa` y repositorios que extienden `JpaRepository`.
- Entidades de dominio: `UserEntity`, `PhoneEntity` en `src/main/java/com/challenge/ibk/users/entity`.
- El repositorio remoto está configurado en: https://github.com/jmendozarv/channel-ibk0-users-v1.git

Si necesita que agregue ejemplos de peticiones (curl/Postman) o un diagrama más detallado,
puedo añadirlos en el repositorio.

Ejemplos rápidos (curl)

1) Registrar un usuario

```
curl -X POST "http://localhost:8080/api/v1/usuarios" -H "Content-Type: application/json" -d "{
  \"name\": \"Juan Perez\",
  \"email\": \"juan.perez@example.com\",
  \"password\": \"Password1\",
  \"phones\": [ { \"number\": \"999999999\", \"citycode\": \"01\", \"contrycode\": \"51\" } ]
}"
```

Respuesta esperada: HTTP 201 con el cuerpo `UserCreateResponse`.

2) Obtener información (ejemplo de endpoint si existe)

```
curl -X GET "http://localhost:8080/api/v1/usuarios/{id}"
```

Diagrama
- Se añadió `ARCHITECTURE.md` con un diagrama ASCII y también hay un diagrama vectorial en `docs/diagram.svg`.

