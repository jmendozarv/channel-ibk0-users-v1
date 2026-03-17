Solution architecture
=====================

Resumen
-------
Microservicio Spring Boot que expone una API REST para gestionar usuarios. La
persistencia se realiza mediante JPA/Hibernate y una base de datos en memoria H2
durante la ejecución. El proyecto incluye un script `schema.sql` que crea las tablas
necesarias.

Componentes
-----------
- API (Spring WebFlux) — controladores REST.
- Service — lógica de negocio y validaciones.
- Repository — interfaces Spring Data JPA (extienden JpaRepository).
- Database — H2 in-memory con `schema.sql`.

Diagrama simple
--------------

         [ HTTP clients ]
                |
                v
         [ Controllers ]
                |
                v
         [ Services ]
                |
                v
         [ Repositories ] <---> [ H2 (in-memory) ]

Notas
-----
- La base de datos en memoria se inicializa a partir de `schema.sql` y `spring.jpa.hibernate.ddl-auto`.
- Para entornos productivos se recomienda reemplazar H2 por una base de datos persistente
  (PostgreSQL, MySQL, etc.) y aplicar migraciones controladas (Flyway/Liquibase).

