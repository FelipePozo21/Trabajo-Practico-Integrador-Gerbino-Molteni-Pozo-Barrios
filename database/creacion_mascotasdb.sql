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
