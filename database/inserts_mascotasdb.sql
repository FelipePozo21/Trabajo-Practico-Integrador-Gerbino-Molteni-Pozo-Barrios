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
