# 📚 Literalura

Aplicación de consola desarrollada con **Java 17 + Spring Boot + PostgreSQL** que consume la API pública de Gutendex y almacena libros en una base de datos relacional.

El sistema funciona completamente desde terminal mediante un menú interactivo.

---

## 🚀 Tecnologías utilizadas

* Java 17
* Spring Boot
* Spring Data JPA
* PostgreSQL
* Jackson (para conversión JSON)
* HttpClient (java.net.http)

---

## 🌐 API utilizada

Se consume la API pública:

[https://gutendex.com/books/?search=](https://gutendex.com/books/?search=)

La aplicación:

* Construye dinámicamente la URL de búsqueda
* Consume la API usando HttpClient
* Convierte el JSON usando Jackson
* Transforma los datos en entidades JPA
* Guarda la información en PostgreSQL

---

## 📦 Funcionalidades

El sistema permite realizar las siguientes operaciones:

1. Buscar libro por título (consume API)
2. Mostrar libros registrados en la base de datos
3. Mostrar autores registrados
4. Mostrar autores vivos en determinado año
5. Mostrar libros por idioma (EN / ES)
6. Salir

---

## ⚙️ Reglas del sistema

* Solo se guardan libros con idioma **EN o ES**
* Solo se utiliza el **primer resultado** de la API
* Solo se toma el **primer autor**
* No se permiten libros duplicados (validación por título)
* No se permiten autores duplicados (validación por nombre)
* Validación para que el año ingresado sea numérico
* El sistema no se detiene ante entradas inválidas

---

## 🗄️ Modelo de Datos

### Libro

* id
* titulo (unique)
* idioma
* numeroDescargas
* autor (ManyToOne)

### Autor

* id
* nombre (unique)
* fechaNacimiento
* fechaMuerte

Relación:

* Un autor puede tener varios libros
* Un libro pertenece a un solo autor

---

## 🛠️ Configuración de Base de Datos

La conexión a PostgreSQL se realiza utilizando **variables de entorno**, lo que mejora la seguridad y evita exponer credenciales en el código.


```
spring.application.name=literalura
spring.datasource.url=jdbc:postgresql://${DB_host}/${DB_NAME}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=org.postgresql.Driver
hibernate.dialect=org.hibernate.dialect.HSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.format-sql=true

```

Antes de ejecutar la aplicación, se deben definir las variables de entorno:

En Windows (PowerShell):

```
setx DB_USER "postgres"
setx DB_PASSWORD "tu_password"
setx DB_NAME "nombre_BD"
setx DB_Host "localhost"
```

En Linux / Mac:

```
export DB_Host=localhost
export DB_USER=postgres
export DB_PASSWORD=tu_password
export DB_NAME=nombre_BD
```

---

## ▶️ Cómo ejecutar

1. Clonar el repositorio
2. Crear la base de datos en PostgreSQL
3. Configurar las variables de entorno
4. Ejecutar desde el IDE o con Maven:

```
mvn spring-boot:run
```

---

## 📂 Estructura del Proyecto

* model → Entidades JPA y DTOs
* repository → Interfaces JPA con consultas personalizadas (@Query)
* service → Consumo de API y conversión JSON
* principal → Lógica del menú
* LiteraluraApplication → Punto de entrada con CommandLineRunner

---

## 🧠 Buenas prácticas aplicadas

* Uso de DTO para desacoplar la API externa
* Validaciones antes de persistir datos
* Uso de Optional para evitar nulls
* Uso de lambdas para recorridos de listas
* Restricciones UNIQUE en base de datos
* Separación clara por capas

---

## 🔮 Posibles mejoras futuras

* Ordenar libros por número de descargas
* Top 10 libros más descargados
* Estadísticas por idioma
* Manejo global de excepciones
* Logging estructurado
* Dockerización

---

Proyecto desarrollado como práctica de consumo de API, persistencia con JPA y arquitectura básica en Spring Boot.
