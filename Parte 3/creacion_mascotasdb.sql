CREATE DATABASE IF NOT EXISTS mascotasdb;
USE mascotasdb;

CREATE TABLE mascota (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    eliminado BOOLEAN NOT NULL DEFAULT FALSE,
    nombre VARCHAR(60) NOT NULL,
    especie VARCHAR(30) NOT NULL,
    raza VARCHAR(60),
    fecha_nacimiento DATE,
    duenio VARCHAR(120) NOT NULL
);

CREATE TABLE microchip (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    eliminado BOOLEAN NOT NULL DEFAULT FALSE,
    codigo VARCHAR(25) NOT NULL UNIQUE,
    fecha_implementacion DATE,
    veterinaria VARCHAR(120),
    observaciones VARCHAR(255),
    mascota_id BIGINT UNIQUE, -- UNIQUE para asegurar que un microchip solo puede tener una mascota (y viceversa)
    
    CONSTRAINT fk_microchip_mascota
        FOREIGN KEY (mascota_id) 
        REFERENCES mascota(id)
        ON DELETE CASCADE -- Si se borra la mascota, el microchip asociado tambien se borra
        ON UPDATE CASCADE
);
