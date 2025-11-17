# Sistema de Registro de Mascotas y Microchips

Este es un **Trabajo Final Integrador** desarrollado para la materia Programación 2 de la Tecncatura Universitaria en Programación a Distancia.

El objetivo principal fue desarrollar un sistema de gestión y registro para mascotas y sus microchips asociados. La aplicación modela y persiste los datos en una base de datos relacional MySQL, implementando una arquitectura multicapa (Config, Entities, DAO, Service, AppMenu) y utilizando el patrón **Data Access Object (DAO)** y **Service Layer** para encapsular la lógica de negocio y las operaciones transaccionales.

| **Apellido** | **Nombre** | **DNI** | **Rol** | 
| :--- | :--- | :--- | :--- | 
| Pozo | Felipe | 44.377.556 | Diseño y Entidades | 
| Barrios | Gonzalo | 43.393.592 | Base de Datos | 
| Molteni | Emmanuel | 36.873.560 | DAO y Servicio | 
| Gerbino | Facundo | 43.018.365 | AppMenu y Documentación |

## Enlace de Video

[![Demo YouTube](https://placehold.co/600x300/F0F8FF/333333?text=Click+para+ver+el+video)](https://www.youtube.com/watch?v=jU4fD-qF3fU)

## Características Clave

* **Dominio Elegido:** Registro de `Mascota` y `Microchip`.
* **Relación:** Asociación **unidireccional 1 a 1** (`Mascota` referencia a `Microchip` a través de una clave foránea).
* **Persistencia:** JDBC con MySQL.
* **Transacciones Obligatorias:** Implementación de `commit` y `rollback` en la Capa de Servicio para operaciones compuestas (ej. asignación de Microchip).
* **Interfaz:** Menú de Consola (CLI) con todas las operaciones CRUD y validaciones en múltiples capas.
* **Validación:** Sistema de validación en Capa de Presentación, Entidades y Servicio para garantizar la integridad de los datos.

## Requisitos del Sistema

Para compilar y ejecutar este proyecto, se requiere el siguiente entorno:

1. **Java Development Kit (JDK):** Versión 21 o superior
2. **Base de Datos:** MySQL Server (se recomienda MySQL Workbench o XAMPP)
3. **Conector JDBC:** Librería MySQL Connector
4. **IDE:** Apache NetBeans IDE (o similar, como IntelliJ IDEA o Eclipse)

## 🗄️ Configuración de la Base de Datos (MySQL)

Se requiere crear una base de datos llamada `mascotas_db` y ejecutar el siguiente script SQL para crear las tablas necesarias (`Microchip` y `Mascota`), respetando la relación unidireccional.

### Creación de la Base de Datos

```sql
-- Creación de la base de datos
CREATE DATABASE IF NOT EXISTS mascotas_db;

USE mascotas_db;

CREATE TABLE Microchip (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    eliminado BOOLEAN DEFAULT FALSE,
    codigo VARCHAR(25) NOT NULL UNIQUE,
    fecha_implantacion DATE,
    veterinaria VARCHAR(120),
    observaciones VARCHAR(255)
);

CREATE TABLE Mascota (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    eliminado BOOLEAN DEFAULT FALSE,
    nombre VARCHAR(60) NOT NULL,
    especie VARCHAR(30) NOT NULL,
    raza VARCHAR(60),
    fecha_nacimiento DATE,
    duenio VARCHAR(120) NOT NULL,
    microchip_id BIGINT UNIQUE,
    
    CONSTRAINT fk_mascota_microchip
        FOREIGN KEY (microchip_id) 
        REFERENCES Microchip(id)
        ON DELETE CASCADE
);
```

### INSERTs de Prueba

```sql
USE mascotas_db;

INSERT INTO Microchip (codigo, fecha_implantacion, veterinaria, observaciones) VALUES
('ABC001', '2022-03-15', 'Veterinaria Central', 'Chip implantado sin incidencias.'),
('ABC002', '2021-11-20', 'Hospital Veterinario Sur', 'Revision anual, chip funcional.'),
('ABC003', '2023-01-01', 'Veterinaria Central', 'Nuevo microchip para cachorro.'),
('ABC004', '2020-06-01', 'Consultorio Dr. Mascotas', 'Mascota rescatada, se le implanto un chip.');

INSERT INTO Mascota (nombre, especie, raza, fecha_nacimiento, duenio, microchip_id) VALUES
('Fido', 'Perro', 'Labrador Retriever', '2019-05-10', 'Juan Perez', 1),
('Minina', 'Gato', 'Siames', '2020-08-22', 'Maria Lopez', 2),
('Rocky', 'Perro', 'Pastor Aleman', '2018-02-01', 'Carlos Gomez', 3),
('Burbuja', 'Gato', 'Balines', '2021-04-05', 'Laura Fernandez', 4);
```

## Compilación y Ejecución

El proyecto está diseñado para ejecutarse desde la línea de comandos (CLI) a través de su clase principal.

Compilar: Abre el proyecto en tu IDE (NetBeans, IntelliJ, etc.). El IDE debe resolver automáticamente las dependencias (principalmente el conector JDBC). Compila el proyecto.

Ejecutar: Ejecuta la clase principal (Main.java o la que contenga el main que llama a AppMenu).

## Flujo de Uso (CLI):

El Menú Principal guia a través de las siguientes opciones:

CRUD para Mascotas.
CRUD para Microchips.
Operación Transaccional Compuesta: Asignar un microchip a una mascota existente (implica la gestión de commit/rollback).
Búsqueda por código de microchip para localizar a la mascota asociada.
Sigue las indicaciones del menú para probar la funcionalidad CRUD y las transacciones.

## Herramientas

- Lenguaje y Entorno: Java Development Kit 21 (Oracle OpenJDK), Apache Netbeans IDE
- Base de Datos: MySQL Workbench y XAMPP
- Librerías: MySQL Connector (JDBC driver)
- Diseño: UMLETINO (para diagrama uml) y APPDiagrams para el diagrama de flujos
