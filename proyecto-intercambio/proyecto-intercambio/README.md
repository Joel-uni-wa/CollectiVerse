# Plataforma de compra, venta e intercambio - Parte B (consulta por id)

Proyecto Spring Boot (Java 17+, Maven, JPA/Hibernate) con **10 entidades** que corresponden a las 10 tablas del diagrama y **un servicio REST de consulta por id para cada una**. La base de datos es PostgreSQL en [Neon](https://neon.com).

## Servicios de consulta por id

| Entidad / tabla          | Servicio                                   |
|--------------------------|--------------------------------------------|
| usuario                  | `GET /usuario/{id}`                        |
| categoria                | `GET /categoria/{id}`                      |
| coleccion                | `GET /coleccion/{id}`                      |
| producto                 | `GET /producto/{id}`                       |
| imagen_producto          | `GET /imagen-producto/{id}`                |
| publicacion              | `GET /publicacion/{id}`                    |
| publicacion_intercambio  | `GET /publicacion-intercambio/{id}`        |
| valoracion               | `GET /valoracion/{id}`                     |
| favorito                 | `GET /favorito/{id}`                       |
| reporte                  | `GET /reporte/{id}`                        |

Si el id no existe, responde **404** con un mensaje.

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
5. Abre `src/main/resources/application.properties` y reemplaza los tres valores:

   ```
   spring.datasource.url=jdbc:postgresql://HOST/NOMBRE_BD?sslmode=require
   spring.datasource.username=TU_USUARIO
   spring.datasource.password=TU_CLAVE
   ```

   (o define las variables de entorno `DB_URL`, `DB_USER` y `DB_PASSWORD`).

**Las tablas no se crean a mano:** con `spring.jpa.hibernate.ddl-auto=update`, Hibernate las crea solo la primera vez que arranca la aplicacion. Para verlas, en Neon abre **SQL Editor** y ejecuta, por ejemplo: `select * from usuario;`

> No subas tu contrasena de Neon a un GitHub publico. Si vas a subir el proyecto, usa las variables de entorno.

## 2. Ejecutar

### En Eclipse
1. **File > Import > Maven > Existing Maven Projects** y elige esta carpeta.
2. Espera a que descargue las dependencias (barra de progreso abajo a la derecha).
3. Clic derecho en `ProyectoApplication.java` > **Run As > Java Application**.
4. Cuando veas `Started ProyectoApplication`, abre el navegador.

### En GitHub Codespaces
1. Sube el proyecto a un repositorio de GitHub y abre **Code > Codespaces > Create codespace**.
2. En la terminal:
   ```
   export DB_URL="jdbc:postgresql://HOST/NOMBRE_BD?sslmode=require"
   export DB_USER="TU_USUARIO"
   export DB_PASSWORD="TU_CLAVE"
   mvn spring-boot:run
   ```
3. Codespaces te avisa que el puerto 8080 esta disponible: abre la URL y agrega la ruta, por ejemplo `/usuario/1`.

## 3. Probar

La primera vez, la clase `DatosIniciales` carga datos de prueba (2 usuarios, 3 productos, 3 publicaciones, etc.), asi que ya hay ids para consultar:

```
http://localhost:8080/usuario/1
http://localhost:8080/producto/1
http://localhost:8080/publicacion/2
http://localhost:8080/publicacion-intercambio/1
http://localhost:8080/valoracion/1
http://localhost:8080/favorito/1
http://localhost:8080/reporte/1
http://localhost:8080/categoria/1
http://localhost:8080/coleccion/1
http://localhost:8080/imagen-producto/1
http://localhost:8080/usuario/999      <- no existe: devuelve 404
```

Si no quieres datos de prueba, borra la clase `config/DatosIniciales.java`.

## 4. Estructura (igual que el proyecto base de clase)

```
relaciones.entity.proyecto
 |- ProyectoApplication
 |- config/DatosIniciales            datos de prueba
 |- dominio/entity/                  10 entidades JPA
 |- dominio/repository/              10 repositorios (JpaRepository)
 |- cu/consultar<entidad>/           Controller + Service + response/Response<Entidad>
```

Cada caso de uso (`consultarusuario`, `consultarproducto`, ...) tiene su `ControllerConsultar...`, `ServiceConsultar...` y su `Response...`, igual que `registrarmenu` en el proyecto de clase.
