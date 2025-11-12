USE mascotasdb;

INSERT INTO mascota (eliminado, nombre, especie, raza, fecha_nacimiento, duenio) VALUES
(FALSE, 'Firulais', 'Perro', 'Mestizo', '2022-10-05', 'Juan Perez'),           -- id generado: 1
(FALSE, 'Mishi', 'Gato', 'Siames', '2022-08-30', 'Ana Gomez'),                 -- id generado: 2
(FALSE, 'Luna', 'Perro', 'Golden Retriever', '2023-05-22', 'Carlos Sanchez'),  -- id generado: 3 (esta no tiene microchip)
(TRUE, 'Rocky', 'Perro', 'Pastor Aleman', '2020-01-18', 'Juan Perez');         -- id generado: 4

INSERT INTO microchip (eliminado, codigo, fecha_implementacion, veterinaria, observaciones, mascota_id) VALUES
-- Microchip asignado a Firulais (id=1)
(FALSE, 'ABC001', '2023-01-15', 'Veterinaria Central', 'Chip implantado en cachorro de 3 meses.', 1),

-- Microchip asignado a Mishi (id=2)
(FALSE, 'ABC002', '2022-11-20', 'Clinica Animalia', NULL, 2),

-- Microchip asignado a Rocky (id=4), que es una mascota eliminada logicamente
(TRUE, 'ABC003', '2021-03-01', 'Veterinaria Central', "Chip asignado a mascota con baja logica.", 4),

-- Microchip SIN mascota asignada
(FALSE, 'ABC004', '2024-02-10', 'Clinica Animalia', 'Chip en stock, listo para ser asignado.', NULL);

SELECT * FROM mascota;
SELECT * FROM microchip;
