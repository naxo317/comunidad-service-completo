# comunidad-service — Plataforma de Cine-Teatro

Microservicio Spring Boot 3.3.13, Java 21, Maven, JPA y PostgreSQL/Supabase.

## Configurar base de datos
1. Crear un proyecto en Supabase o usar PostgreSQL local.
2. Definir variables de entorno `DB_URL`, `DB_USER`, `DB_PASSWORD`. Ejemplo URL JDBC Supabase: `jdbc:postgresql://HOST:5432/postgres?sslmode=require`. Consultar los datos exactos en el panel de Supabase; para conexiones IPv4 usar el pooler compatible con JDBC.
3. Por defecto se utiliza el esquema `public` para facilitar el arranque. Para usar esquema independiente `comunidad`, crearlo primero (`CREATE SCHEMA comunidad;`) y definir `DB_SCHEMA=comunidad` (el usuario de BD necesita permisos).
4. `DDL_AUTO=update` crea/actualiza tablas solo para desarrollo; en producción usar migraciones.

## Ejecutar en IntelliJ
Abrir esta carpeta como proyecto Maven, seleccionar JDK 21 y ejecutar `ComunidadApplication`. En Windows también se puede ejecutar `mvnw.cmd spring-boot:run`.

## Probar en Postman
URL base `http://localhost:8084/api/v1/comunidad`.
Para endpoints de escritura, añadir cabecera `X-User-Id: usuario-1`. Esta cabecera **solo es para pruebas locales**: el Gateway debe eliminar cabeceras entrantes y reinyectarlas después de verificar JWT. No exponer este servicio a internet directamente.

POST `/publicaciones` con body JSON: `{"titulo":"Mi reseña","contenido":"Excelente obra","categoria":"TEATRO"}`.
GET `/publicaciones`, GET `/publicaciones/1`, DELETE `/publicaciones/1`.
POST `/publicaciones/1/comentarios` con `{"texto":"Estoy de acuerdo"}`.
POST `/comentarios/1/respuestas` con `{"texto":"Gracias"}`.
POST y DELETE `/publicaciones/1/likes`.
POST y DELETE `/publicaciones/1/republicaciones`.
GET `/publicaciones/1/contadores`.
POST `/reportes` con `{"idPublicacion":1,"motivo":"Spam"}`.
PATCH `/publicaciones/1/estado` con `{"estado":"OCULTO"}` y cabecera interna `X-User-Role: ADMIN`.
PATCH `/comentarios/1/estado` funciona igual.

## Integración pendiente
El documento tiene una contradicción de puertos (8083/8084). Se usa **8084** porque 8083 ya está reservado para usuarios. Configurar Gateway `/api/v1/comunidad/**` hacia `http://localhost:8084`. Solo el Gateway debe administrar CORS y verificar JWT. `X-User-Id` y `X-User-Role` son contratos provisionales, a acordar con el equipo. La identidad se guarda como `String` hasta definir su tipo definitivo. No hay FK entre servicios.

## Alcance y limitaciones
Se implementan publicaciones, comentarios, respuestas (máximo 3 niveles), likes, republicaciones, reportes y moderación básica con borrado lógico. No se implementan todavía paginación por cursor, integración real con Gateway/usuarios, migraciones Flyway, validación remota de contenido ni tests automatizados. El rol ADMIN es confiable **solo si el Gateway inyecta la cabecera**. No usar servicio expuesto directamente.
