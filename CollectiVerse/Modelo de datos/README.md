# Plataforma de compra, venta e intercambio - Parte B (servicios REST)

Proyecto Spring Boot (Java 17+, Maven, JPA/Hibernate) con **10 entidades** que corresponden a las 10 tablas del diagrama. Cada entidad tiene:

- `GET /{ruta}/{id}` consulta por id
- `GET /{ruta}/todos` lista todos
- `POST /{ruta}/nuevo` registra uno nuevo

La base de datos es PostgreSQL en [Neon](https://neon.com).

## Servicios

| Tabla                   | Ruta base                  |
|-------------------------|----------------------------|
| usuario                 | `/usuario`                 |
| categoria               | `/categoria`               |
| coleccion               | `/coleccion`               |
| producto                | `/producto`                |
| imagen_producto         | `/imagen-producto`         |
| publicacion             | `/publicacion`             |
| publicacion_intercambio | `/publicacion-intercambio` |
| valoracion              | `/valoracion`              |
| favorito                | `/favorito`                |
| reporte                 | `/reporte`                 |

Ejemplos de consulta: `GET /usuario/1`, `GET /publicacion/2`, `GET /producto/todos`.

## POST: ejemplos de cuerpo (JSON)

Se envian con `Content-Type: application/json`. Los ids de las relaciones (`idUsuario`, `idProducto`, ...) tienen que existir.
Responden **201 Created** con el registro creado.

| Ruta                              | Cuerpo de ejemplo |
|-----------------------------------|-------------------|
| `POST /usuario/nuevo`             | `{"nombre":"Carlos Ruiz","correo":"carlos@correo.com","telefono":"955123456","tipo":false}` |
| `POST /categoria/nuevo`           | `{"nombre":"Cartas Magic","descripcion":"Magic The Gathering"}` |
| `POST /coleccion/nuevo`           | `{"idCategoria":1,"nombre":"Jungle","descripcion":"Expansion Jungle"}` |
| `POST /producto/nuevo`            | `{"idCategoria":1,"idColeccion":1,"nombre":"Blastoise","cantidad":1,"precio":120.50}` |
| `POST /imagen-producto/nuevo`     | `{"idProducto":1,"url":"https://ejemplo.com/img/blastoise.jpg","orden":1}` |
| `POST /publicacion/nuevo` (venta) | `{"idUsuario":1,"idProducto":3,"titulo":"Vendo Pikachu","descripcion":"Buen estado","tipo":"VENTA"}` |
| `POST /publicacion/nuevo` (cambio)| `{"idUsuario":1,"idProducto":3,"titulo":"Cambio Pikachu","tipo":"INTERCAMBIO","intercambios":[{"nombreSolicitado":"Dark Magician","descripcion":"Edicion original","cantidad":1}]}` |
| `POST /publicacion-intercambio/nuevo` | `{"idPublicacion":2,"nombreSolicitado":"Mew holo","descripcion":"Cualquier edicion","cantidad":1}` |
| `POST /valoracion/nuevo`          | `{"idUsuario":2,"idPublicacion":3,"califUsuario":4,"califPublicacion":5,"comentario":"Todo bien"}` |
| `POST /favorito/nuevo`            | `{"idUsuario":1,"idPublicacion":2}` |
| `POST /reporte/nuevo`             | `{"idUsuario":2,"idPublicacion":1,"motivo":"Precio sospechoso"}` |

Con curl (en la terminal):

```
curl -X POST http://localhost:8080/usuario/nuevo \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Carlos Ruiz","correo":"carlos@correo.com","telefono":"955123456","tipo":false}'
```

### Reglas que valida cada POST

- **usuario**: nombre y correo obligatorios; correo con formato valido y **no repetido**; telefono opcional (solo numeros, 6 a 15 digitos).
- **producto**: categoria obligatoria; cantidad >= 0; precio entre 0 y 99999999.99; si trae coleccion, esta debe **pertenecer a la categoria**.
- **publicacion**: `tipo` debe ser `VENTA`, `INTERCAMBIO` o `AMBOS`. Si es `VENTA` **no** puede traer productos a cambio; si es `INTERCAMBIO` o `AMBOS` **debe** traer al menos uno (lo que el vendedor acepta a cambio). Queda con estado `ACTIVA`.
- **publicacion-intercambio**: solo se puede agregar a publicaciones que no sean `VENTA`.
- **valoracion**: calificaciones de 1 a 5; no puedes valorar tu propia publicacion; una sola valoracion por usuario y publicacion.
- **favorito**: no se puede repetir el mismo favorito.
- **reporte**: motivo obligatorio; no puedes reportar tu propia publicacion; queda `PENDIENTE`.

## Errores (excepciones)

Todos los errores responden en el mismo formato JSON:

```json
{
  "status": 404,
  "error": "No encontrado",
  "mensaje": "Usuario con id 999 no existe",
  "fecha": "2026-10-08T22:30:00.123"
}
```

| Codigo | Cuando pasa | Excepcion |
|--------|-------------|-----------|
| **404** | Se consulta un id que no existe (`GET /usuario/999`) | `RecursoNoEncontradoException` |
| **400** | Faltan datos, estan mal, el id de una relacion no existe, JSON invalido o un id que no es numero (`GET /usuario/abc`) | `SolicitudInvalidaException` y errores de formato |
| **409** | El dato ya existe (correo repetido, valoracion o favorito duplicado) | `ConflictoException` |

El codigo esta en `excepcion/` (las excepciones y `ManejadorExcepciones`) y `util/Validar.java` (validaciones reutilizables).

## 1. Crear la base de datos en Neon

1. Entra a https://neon.com y crea una cuenta.
2. Crea un proyecto (**Create project**): ponle un nombre y elige una region.
3. En el panel del proyecto pulsa **Connect**. Ahi aparecen: **host**, **database**, **user** y **password**.
   Si ves un interruptor **Pooled connection**, apagalo (usa la conexion directa).
4. Arma la URL JDBC con este formato (sin usuario ni clave adentro):

   ```
   jdbc:postgresql://HOST/NOMBRE_BD?sslmode=require
   ```

   Si Neon te muestra algo como `...?sslmode=require&channel_binding=require`, **no copies** `channel_binding`.
5. Abre `src/main/resources/application.properties` y reemplaza los tres valores, o define las variables de entorno `DB_URL`, `DB_USER` y `DB_PASSWORD`.

**Las tablas no se crean a mano:** con `spring.jpa.hibernate.ddl-auto=update`, Hibernate las crea solo la primera vez que arranca la aplicacion. Para verlas, en Neon abre **SQL Editor** y ejecuta, por ejemplo: `select * from usuario;`

> No subas tu contrasena de Neon a un GitHub publico. Usa las variables de entorno.

## 2. Ejecutar

### En Eclipse
1. **File > Import > Maven > Existing Maven Projects** y elige esta carpeta.
2. Espera a que descargue las dependencias.
3. Clic derecho en `ProyectoApplication.java` > **Run As > Java Application**.

### En GitHub Codespaces
```
export DB_URL="jdbc:postgresql://HOST/NOMBRE_BD?sslmode=require"
export DB_USER="TU_USUARIO"
export DB_PASSWORD="TU_CLAVE"
mvn spring-boot:run
```
Abre el puerto 8080 desde la pestana **Ports** y agrega la ruta, por ejemplo `/usuario/1`.

## 3. Datos de prueba

La primera vez, la clase `config/DatosIniciales` carga datos de prueba (2 usuarios, 3 productos, 3 publicaciones, etc.), asi que ya hay ids para consultar:
`/usuario/1`, `/producto/1`, `/publicacion/2`, `/publicacion-intercambio/1`, `/valoracion/1`, `/favorito/1`, `/reporte/1`, `/categoria/1`, `/coleccion/1`, `/imagen-producto/1`.

Si no quieres datos de prueba, borra la clase `config/DatosIniciales.java`.

## 4. Estructura

```
relaciones.entity.proyecto
 |- ProyectoApplication
 |- config/DatosIniciales            datos de prueba
 |- dominio/entity/                  10 entidades JPA
 |- dominio/repository/              10 repositorios (JpaRepository)
 |- cu/consultar<entidad>/           GET /{id} y GET /todos  (Controller + Service + response/)
 |- cu/registrar<entidad>/           POST /nuevo             (Controller + Service + request/)
 |- excepcion/                       excepciones propias y manejador de errores
 |- util/Validar                     validaciones simples
```
