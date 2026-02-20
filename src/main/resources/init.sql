-- =====================================================
-- MiVoto Service - Database Seed Data
-- System: Electronic Voting Platform
-- Generated: 2026-01-31
-- =====================================================

-- =====================================================
-- USERS TABLE (50 records)
-- Password hash represents: "Password123!"
-- =====================================================

INSERT INTO users (document_number, first_name, last_name, email, password, role, active, created_at, updated_at, last_login) VALUES
-- ADMIN Users (5)
('12345678', 'Carlos', 'Rodríguez', 'carlos.rodriguez@mivoto.pe', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'ADMIN', true, '2025-01-15 10:00:00', '2025-01-15 10:00:00', '2026-01-30 14:30:00'),
('23456789', 'María', 'González', 'maria.gonzalez@mivoto.pe', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'ADMIN', true, '2025-01-16 11:00:00', '2025-01-16 11:00:00', '2026-01-31 09:15:00'),
('34567890', 'Juan', 'Pérez', 'juan.perez@mivoto.pe', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'ADMIN', true, '2025-01-17 12:00:00', '2025-01-17 12:00:00', '2026-01-29 16:45:00'),
('45678901', 'Ana', 'Torres', 'ana.torres@mivoto.pe', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'ADMIN', true, '2025-01-18 13:00:00', '2025-01-18 13:00:00', '2026-01-31 11:20:00'),
('56789012', 'Pedro', 'Sánchez', 'pedro.sanchez@mivoto.pe', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'ADMIN', false, '2025-01-19 14:00:00', '2025-01-19 14:00:00', '2025-12-20 10:00:00'),

-- SUPERVISOR Users (3)
('67890123', 'Luis', 'Ramírez', 'luis.ramirez@mivoto.pe', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'SUPERVISOR', true, '2025-01-20 15:00:00', '2025-01-20 15:00:00', '2026-01-30 18:30:00'),
('78901234', 'Carmen', 'Vargas', 'carmen.vargas@mivoto.pe', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'SUPERVISOR', true, '2025-01-21 16:00:00', '2025-01-21 16:00:00', '2026-01-31 08:45:00'),
('89012345', 'Roberto', 'Quispe', 'roberto.quispe@mivoto.pe', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'SUPERVISOR', true, '2025-01-22 17:00:00', '2025-01-22 17:00:00', '2026-01-29 20:10:00'),

-- AUDITOR Users (2)
('90123456', 'Patricia', 'Flores', 'patricia.flores@mivoto.pe', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'AUDITOR', true, '2025-01-23 18:00:00', '2025-01-23 18:00:00', '2026-01-31 07:00:00'),
('01234567', 'Miguel', 'Castillo', 'miguel.castillo@mivoto.pe', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'AUDITOR', true, '2025-01-24 19:00:00', '2025-01-24 19:00:00', '2026-01-30 12:30:00'),

-- VOTER Users (40)
('11111111', 'José', 'Mendoza', 'jose.mendoza@email.com', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'VOTER', true, '2025-02-01 08:00:00', '2025-02-01 08:00:00', '2026-01-30 19:00:00'),
('22222222', 'Laura', 'Rojas', 'laura.rojas@email.com', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'VOTER', true, '2025-02-02 08:30:00', '2025-02-02 08:30:00', '2026-01-31 10:15:00'),
('33333333', 'Diego', 'Morales', 'diego.morales@email.com', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'VOTER', true, '2025-02-03 09:00:00', '2025-02-03 09:00:00', '2026-01-29 15:30:00'),
('44444444', 'Sofía', 'Vega', 'sofia.vega@email.com', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'VOTER', true, '2025-02-04 09:30:00', '2025-02-04 09:30:00', '2026-01-30 11:45:00'),
('55555555', 'Fernando', 'Cruz', 'fernando.cruz@email.com', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'VOTER', true, '2025-02-05 10:00:00', '2025-02-05 10:00:00', '2026-01-31 13:20:00'),
('66666666', 'Valentina', 'Herrera', 'valentina.herrera@email.com', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'VOTER', true, '2025-02-06 10:30:00', '2025-02-06 10:30:00', '2026-01-28 17:00:00'),
('77777777', 'Ricardo', 'Ortega', 'ricardo.ortega@email.com', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'VOTER', true, '2025-02-07 11:00:00', '2025-02-07 11:00:00', '2026-01-29 09:30:00'),
('88888888', 'Isabella', 'Medina', 'isabella.medina@email.com', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'VOTER', true, '2025-02-08 11:30:00', '2025-02-08 11:30:00', '2026-01-30 14:45:00'),
('99999999', 'Andrés', 'Guzmán', 'andres.guzman@email.com', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'VOTER', true, '2025-02-09 12:00:00', '2025-02-09 12:00:00', '2026-01-31 16:10:00'),
('10101010', 'Camila', 'Reyes', 'camila.reyes@email.com', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'VOTER', true, '2025-02-10 12:30:00', '2025-02-10 12:30:00', '2026-01-27 18:20:00'),
('20202020', 'Javier', 'Paredes', 'javier.paredes@email.com', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'VOTER', true, '2025-02-11 13:00:00', '2025-02-11 13:00:00', '2026-01-28 10:00:00'),
('30303030', 'Natalia', 'Silva', 'natalia.silva@email.com', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'VOTER', true, '2025-02-12 13:30:00', '2025-02-12 13:30:00', '2026-01-29 12:15:00'),
('40404040', 'Gabriel', 'Campos', 'gabriel.campos@email.com', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'VOTER', true, '2025-02-13 14:00:00', '2025-02-13 14:00:00', '2026-01-30 08:30:00'),
('50505050', 'Daniela', 'Ramos', 'daniela.ramos@email.com', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'VOTER', true, '2025-02-14 14:30:00', '2025-02-14 14:30:00', '2026-01-31 11:45:00'),
('60606060', 'Sebastián', 'Navarro', 'sebastian.navarro@email.com', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'VOTER', true, '2025-02-15 15:00:00', '2025-02-15 15:00:00', '2026-01-26 15:20:00'),
('70707070', 'Valeria', 'Delgado', 'valeria.delgado@email.com', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'VOTER', true, '2025-02-16 15:30:00', '2025-02-16 15:30:00', '2026-01-27 13:40:00'),
('80808080', 'Mateo', 'Romero', 'mateo.romero@email.com', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'VOTER', true, '2025-02-17 16:00:00', '2025-02-17 16:00:00', '2026-01-28 16:50:00'),
('12121212', 'Luciana', 'Jiménez', 'luciana.jimenez@email.com', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'VOTER', true, '2025-02-18 16:30:00', '2025-02-18 16:30:00', '2026-01-29 19:00:00'),
('13131313', 'Alejandro', 'Márquez', 'alejandro.marquez@email.com', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'VOTER', true, '2025-02-19 17:00:00', '2025-02-19 17:00:00', '2026-01-30 07:15:00'),
('14141414', 'Martina', 'Alvarado', 'martina.alvarado@email.com', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'VOTER', true, '2025-02-20 17:30:00', '2025-02-20 17:30:00', '2026-01-31 09:30:00'),
('15151515', 'Nicolás', 'Bustamante', 'nicolas.bustamante@email.com', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'VOTER', true, '2025-02-21 18:00:00', '2025-02-21 18:00:00', '2026-01-25 12:00:00'),
('16161616', 'Renata', 'Cárdenas', 'renata.cardenas@email.com', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'VOTER', true, '2025-02-22 18:30:00', '2025-02-22 18:30:00', '2026-01-26 14:20:00'),
('17171717', 'Emilio', 'Ponce', 'emilio.ponce@email.com', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'VOTER', true, '2025-02-23 19:00:00', '2025-02-23 19:00:00', '2026-01-27 16:35:00'),
('18181818', 'Victoria', 'Aguilar', 'victoria.aguilar@email.com', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'VOTER', true, '2025-02-24 19:30:00', '2025-02-24 19:30:00', '2026-01-28 18:45:00'),
('19191919', 'Lucas', 'Espinoza', 'lucas.espinoza@email.com', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'VOTER', true, '2025-02-25 20:00:00', '2025-02-25 20:00:00', '2026-01-29 20:50:00'),
('21212121', 'Amanda', 'Cabrera', 'amanda.cabrera@email.com', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'VOTER', true, '2025-02-26 20:30:00', '2025-02-26 20:30:00', '2026-01-30 15:10:00'),
('23232323', 'Maximiliano', 'León', 'maximiliano.leon@email.com', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'VOTER', true, '2025-02-27 21:00:00', '2025-02-27 21:00:00', '2026-01-31 17:25:00'),
('24242424', 'Julieta', 'Ibáñez', 'julieta.ibanez@email.com', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'VOTER', true, '2025-02-28 21:30:00', '2025-02-28 21:30:00', '2026-01-24 19:30:00'),
('25252525', 'Santiago', 'Fuentes', 'santiago.fuentes@email.com', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'VOTER', true, '2025-03-01 22:00:00', '2025-03-01 22:00:00', '2026-01-25 21:40:00'),
('26262626', 'Florencia', 'Salazar', 'florencia.salazar@email.com', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'VOTER', true, '2025-03-02 08:00:00', '2025-03-02 08:00:00', '2026-01-26 08:50:00'),
('27272727', 'Joaquín', 'Chávez', 'joaquin.chavez@email.com', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'VOTER', true, '2025-03-03 08:30:00', '2025-03-03 08:30:00', '2026-01-27 10:00:00'),
('28282828', 'Antonella', 'Benítez', 'antonella.benitez@email.com', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'VOTER', true, '2025-03-04 09:00:00', '2025-03-04 09:00:00', '2026-01-28 12:10:00'),
('29292929', 'Felipe', 'Soto', 'felipe.soto@email.com', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'VOTER', true, '2025-03-05 09:30:00', '2025-03-05 09:30:00', '2026-01-29 14:20:00'),
('31313131', 'Carolina', 'Maldonado', 'carolina.maldonado@email.com', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'VOTER', true, '2025-03-06 10:00:00', '2025-03-06 10:00:00', '2026-01-30 16:30:00'),
('32323232', 'Tomás', 'Montoya', 'tomas.montoya@email.com', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'VOTER', true, '2025-03-07 10:30:00', '2025-03-07 10:30:00', '2026-01-31 18:40:00'),
('35353535', 'Guadalupe', 'Acosta', 'guadalupe.acosta@email.com', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'VOTER', true, '2025-03-08 11:00:00', '2025-03-08 11:00:00', '2026-01-23 20:50:00'),
('36363636', 'Ignacio', 'Villanueva', 'ignacio.villanueva@email.com', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'VOTER', true, '2025-03-09 11:30:00', '2025-03-09 11:30:00', '2026-01-24 09:00:00'),
('37373737', 'Catalina', 'Cortés', 'catalina.cortes@email.com', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'VOTER', false, '2025-03-10 12:00:00', '2025-03-10 12:00:00', '2025-11-15 14:00:00'),
('38383838', 'Benjamín', 'Guerrero', 'benjamin.guerrero@email.com', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'VOTER', false, '2025-03-11 12:30:00', '2025-03-11 12:30:00', '2025-12-01 10:30:00'),
('39393939', 'Francisca', 'Peña', 'francisca.pena@email.com', '$2a$10$aRbnNtHe2zW248R7J7ZBee2j4pU5aHDGIka04vcXKXBILz2l3uJ5a', 'VOTER', false, '2025-03-12 13:00:00', '2025-03-12 13:00:00', '2025-10-20 16:45:00');

-- =====================================================
-- DISTRICTS TABLE (15 records)
-- Hierarchical structure: Departments -> Provinces
-- =====================================================

INSERT INTO districts (name, code, type, parent_district_id, registered_voters, active, created_at, updated_at) VALUES
-- Departments (parent level)
('Lima', 'DEPT-LIMA', 'Departamento', NULL, 850000, true, '2025-01-10 08:00:00', '2025-01-10 08:00:00'),
('Arequipa', 'DEPT-ARQP', 'Departamento', NULL, 450000, true, '2025-01-10 08:15:00', '2025-01-10 08:15:00'),
('Cusco', 'DEPT-CUSC', 'Departamento', NULL, 380000, true, '2025-01-10 08:30:00', '2025-01-10 08:30:00'),
('La Libertad', 'DEPT-LALI', 'Departamento', NULL, 520000, true, '2025-01-10 08:45:00', '2025-01-10 08:45:00'),
('Piura', 'DEPT-PIUR', 'Departamento', NULL, 480000, true, '2025-01-10 09:00:00', '2025-01-10 09:00:00'),

-- Provinces (child level)
('Lima Metropolitana', 'PROV-LIMA-METRO', 'Provincia', 1, 650000, true, '2025-01-10 09:15:00', '2025-01-10 09:15:00'),
('Callao', 'PROV-CALLAO', 'Provincia', 1, 200000, true, '2025-01-10 09:30:00', '2025-01-10 09:30:00'),
('Arequipa Centro', 'PROV-ARQP-CENTRO', 'Provincia', 2, 280000, true, '2025-01-10 09:45:00', '2025-01-10 09:45:00'),
('Camaná', 'PROV-CAMANA', 'Provincia', 2, 170000, true, '2025-01-10 10:00:00', '2025-01-10 10:00:00'),
('Cusco Centro', 'PROV-CUSC-CENTRO', 'Provincia', 3, 220000, true, '2025-01-10 10:15:00', '2025-01-10 10:15:00'),
('Urubamba', 'PROV-URUBAMBA', 'Provincia', 3, 160000, true, '2025-01-10 10:30:00', '2025-01-10 10:30:00'),
('Trujillo', 'PROV-TRUJILLO', 'Provincia', 4, 320000, true, '2025-01-10 10:45:00', '2025-01-10 10:45:00'),
('Pacasmayo', 'PROV-PACASMAYO', 'Provincia', 4, 200000, true, '2025-01-10 11:00:00', '2025-01-10 11:00:00'),
('Piura Centro', 'PROV-PIUR-CENTRO', 'Provincia', 5, 290000, true, '2025-01-10 11:15:00', '2025-01-10 11:15:00'),
('Sullana', 'PROV-SULLANA', 'Provincia', 5, 190000, true, '2025-01-10 11:30:00', '2025-01-10 11:30:00');

-- =====================================================
-- ELECTIONS TABLE (8 records)
-- Various statuses: DRAFT, SCHEDULED, ACTIVE, CLOSED, CANCELLED
-- =====================================================

INSERT INTO elections (title, description, status, start_date, end_date, max_votes_per_user, allows_blank_vote, requires_verification, created_at, updated_at, created_by) VALUES
-- DRAFT Elections (2)
('Elección de Delegados 2026', 'Elección interna para seleccionar delegados estudiantiles de las diferentes facultades universitarias.', 'DRAFT', '2026-03-15 08:00:00', '2026-03-15 20:00:00', 1, true, true, '2026-01-20 10:00:00', '2026-01-25 14:30:00', 1),
('Referéndum Ley Ambiental', 'Consulta popular sobre la aprobación de nuevas medidas de protección ambiental.', 'DRAFT', '2026-04-10 07:00:00', '2026-04-10 19:00:00', 1, true, false, '2026-01-22 11:00:00', '2026-01-28 16:45:00', 2),

-- SCHEDULED Elections (2)
('Elección Municipal 2026', 'Elección de alcaldes y regidores para el período 2026-2030 en diferentes distritos del país.', 'SCHEDULED', '2026-10-05 08:00:00', '2026-10-05 19:00:00', 1, false, true, '2025-12-10 09:00:00', '2026-01-15 10:30:00', 1),
('Junta Directiva Asociación Vecinal', 'Elección de la nueva junta directiva de la asociación de vecinos del sector norte.', 'SCHEDULED', '2026-03-01 09:00:00', '2026-03-01 18:00:00', 3, true, false, '2026-01-05 13:00:00', '2026-01-18 15:00:00', 3),

-- ACTIVE Elections (2)
('Elección Presidencial 2026', 'Elección para elegir al presidente y vicepresidentes de la República para el período 2026-2031.', 'ACTIVE', '2026-01-25 06:00:00', '2026-02-15 22:00:00', 1, true, true, '2025-11-01 08:00:00', '2026-01-25 06:00:00', 1),
('Consejo Estudiantil Secundaria', 'Votación para elegir representantes del consejo estudiantil de educación secundaria.', 'ACTIVE', '2026-01-28 08:00:00', '2026-02-05 17:00:00', 2, false, false, '2026-01-10 10:00:00', '2026-01-28 08:00:00', 4),

-- CLOSED Election (1)
('Elección Gremial 2025', 'Elección de representantes del gremio de trabajadores del sector salud.', 'CLOSED', '2025-11-10 07:00:00', '2025-11-10 18:00:00', 1, true, true, '2025-10-01 09:00:00', '2025-11-10 18:00:01', 2),

-- CANCELLED Election (1)
('Elección Regional Cancelada', 'Elección suspendida por irregularidades detectadas en el proceso de inscripción de candidatos.', 'CANCELLED', '2025-12-15 08:00:00', '2025-12-15 19:00:00', 1, false, true, '2025-11-15 10:00:00', '2025-12-10 14:00:00', 1);

-- =====================================================
-- CANDIDATES TABLE (40 records)
-- Distributed across elections
-- =====================================================

INSERT INTO candidates (election_id, number, name, party, description, photo_url, active, vote_count, created_at, updated_at) VALUES
-- Election ID 5: Elección Presidencial 2026 (ACTIVE) - 6 candidates
(5, 1, 'Carlos Alberto Mendoza Ruiz', 'Partido Progresista Nacional', 'Ex gobernador regional con enfoque en educación y salud pública.', 'https://cdn.mivoto.pe/photos/candidate_5_1.jpg', true, 45, '2025-11-05 10:00:00', '2026-01-30 18:00:00'),
(5, 2, 'María Elena Vásquez Torres', 'Alianza por el Cambio', 'Economista especializada en desarrollo sostenible y equidad social.', 'https://cdn.mivoto.pe/photos/candidate_5_2.jpg', true, 38, '2025-11-05 10:30:00', '2026-01-30 18:00:00'),
(5, 3, 'Jorge Luis Ramírez Flores', 'Frente Democrático Popular', 'Senador con 15 años de experiencia en legislación laboral.', 'https://cdn.mivoto.pe/photos/candidate_5_3.jpg', true, 29, '2025-11-05 11:00:00', '2026-01-30 18:00:00'),
(5, 4, 'Patricia Sofía Herrera Campos', 'Movimiento Ciudadano Independiente', 'Activista social y defensora de derechos humanos.', 'https://cdn.mivoto.pe/photos/candidate_5_4.jpg', true, 17, '2025-11-05 11:30:00', '2026-01-30 18:00:00'),
(5, 5, 'Roberto Antonio García Salazar', 'Partido Liberal Reformista', 'Empresario con propuestas de modernización tecnológica.', 'https://cdn.mivoto.pe/photos/candidate_5_5.jpg', true, 22, '2025-11-05 12:00:00', '2026-01-30 18:00:00'),
(5, 6, 'Ana Lucía Fernández Díaz', 'Coalición Verde Sostenible', 'Bióloga ambientalista con planes de conservación ecológica.', 'https://cdn.mivoto.pe/photos/candidate_5_6.jpg', true, 11, '2025-11-05 12:30:00', '2026-01-30 18:00:00'),

-- Election ID 6: Consejo Estudiantil Secundaria (ACTIVE) - 5 candidates
(6, 1, 'Diego Martínez López', 'Lista Azul', 'Estudiante de 5to año, capitán del equipo de debate.', 'https://cdn.mivoto.pe/photos/candidate_6_1.jpg', true, 18, '2026-01-12 09:00:00', '2026-01-31 12:00:00'),
(6, 2, 'Valeria Ramos Silva', 'Lista Roja', 'Presidenta del club de ciencias, enfocada en proyectos STEM.', 'https://cdn.mivoto.pe/photos/candidate_6_2.jpg', true, 15, '2026-01-12 09:30:00', '2026-01-31 12:00:00'),
(6, 3, 'Sebastián Torres Vargas', 'Lista Verde', 'Líder del coro escolar y promotor de actividades culturales.', 'https://cdn.mivoto.pe/photos/candidate_6_3.jpg', true, 23, '2026-01-12 10:00:00', '2026-01-31 12:00:00'),
(6, 4, 'Isabella Cruz Méndez', 'Lista Amarilla', 'Voluntaria comunitaria, enfocada en bienestar estudiantil.', 'https://cdn.mivoto.pe/photos/candidate_6_4.jpg', true, 12, '2026-01-12 10:30:00', '2026-01-31 12:00:00'),
(6, 5, 'Lucas Paredes Gómez', 'Lista Naranja', 'Deportista destacado, propone mejorar infraestructura deportiva.', 'https://cdn.mivoto.pe/photos/candidate_6_5.jpg', true, 9, '2026-01-12 11:00:00', '2026-01-31 12:00:00'),

-- Election ID 7: Elección Gremial 2025 (CLOSED) - 4 candidates
(7, 1, 'Luis Fernando Quispe Mamani', 'Sindicato Unido de Enfermeros', 'Enfermero con 20 años de experiencia en hospitales públicos.', 'https://cdn.mivoto.pe/photos/candidate_7_1.jpg', true, 89, '2025-10-05 08:00:00', '2025-11-10 18:00:00'),
(7, 2, 'Carmen Rosa Delgado Pérez', 'Asociación de Médicos Generales', 'Médica general defensora de mejores condiciones laborales.', 'https://cdn.mivoto.pe/photos/candidate_7_2.jpg', true, 67, '2025-10-05 08:30:00', '2025-11-10 18:00:00'),
(7, 3, 'Pedro Alejandro Castro Rojas', 'Frente de Trabajadores de Salud', 'Técnico de laboratorio con propuestas de capacitación continua.', 'https://cdn.mivoto.pe/photos/candidate_7_3.jpg', true, 54, '2025-10-05 09:00:00', '2025-11-10 18:00:00'),
(7, 4, 'Mónica Elizabeth Navarro Chávez', 'Coalición por la Salud Pública', 'Administradora hospitalaria enfocada en eficiencia operativa.', 'https://cdn.mivoto.pe/photos/candidate_7_4.jpg', true, 42, '2025-10-05 09:30:00', '2025-11-10 18:00:00'),

-- Election ID 1: Elección de Delegados 2026 (DRAFT) - 5 candidates
(1, 1, 'Andrés Felipe Morales Soto', 'Facultad de Ingeniería', 'Estudiante de Ing. Civil, representante de proyectos estudiantiles.', 'https://cdn.mivoto.pe/photos/candidate_1_1.jpg', true, 0, '2026-01-21 10:00:00', '2026-01-25 14:30:00'),
(1, 2, 'Camila Andrea Rojas Vega', 'Facultad de Medicina', 'Estudiante de 4to año de Medicina, presidenta del centro de estudiantes.', 'https://cdn.mivoto.pe/photos/candidate_1_2.jpg', true, 0, '2026-01-21 10:30:00', '2026-01-25 14:30:00'),
(1, 3, 'Gabriel Esteban Jiménez Cruz', 'Facultad de Derecho', 'Miembro del equipo de moot court, enfocado en derechos estudiantiles.', 'https://cdn.mivoto.pe/photos/candidate_1_3.jpg', true, 0, '2026-01-21 11:00:00', '2026-01-25 14:30:00'),
(1, 4, 'Daniela Patricia Campos Herrera', 'Facultad de Economía', 'Investigadora en economía social, promotora de inclusión.', 'https://cdn.mivoto.pe/photos/candidate_1_4.jpg', true, 0, '2026-01-21 11:30:00', '2026-01-25 14:30:00'),
(1, 5, 'Nicolás Alejandro Alvarado Pérez', 'Facultad de Arquitectura', 'Coordinador de proyectos de diseño comunitario.', 'https://cdn.mivoto.pe/photos/candidate_1_5.jpg', true, 0, '2026-01-21 12:00:00', '2026-01-25 14:30:00'),

-- Election ID 2: Referéndum Ley Ambiental (DRAFT) - 2 options
(2, 1, 'A Favor de la Ley', NULL, 'Voto a favor de la implementación de nuevas medidas de protección ambiental.', NULL, true, 0, '2026-01-23 09:00:00', '2026-01-28 16:45:00'),
(2, 2, 'En Contra de la Ley', NULL, 'Voto en contra de la implementación de las medidas propuestas.', NULL, true, 0, '2026-01-23 09:00:00', '2026-01-28 16:45:00'),

-- Election ID 3: Elección Municipal 2026 (SCHEDULED) - 6 candidates
(3, 1, 'Fernando José Bustamante León', 'Partido Municipal Progresista', 'Ex regidor con planes de modernización urbana.', 'https://cdn.mivoto.pe/photos/candidate_3_1.jpg', true, 0, '2025-12-15 10:00:00', '2026-01-15 10:30:00'),
(3, 2, 'Renata Isabel Cárdenas Aguilar', 'Movimiento Cívico Local', 'Arquitecta urbanista enfocada en espacios verdes.', 'https://cdn.mivoto.pe/photos/candidate_3_2.jpg', true, 0, '2025-12-15 10:30:00', '2026-01-15 10:30:00'),
(3, 3, 'Emilio Roberto Ponce Espinoza', 'Alianza Vecinal Unida', 'Comerciante local con propuestas de desarrollo económico.', 'https://cdn.mivoto.pe/photos/candidate_3_3.jpg', true, 0, '2025-12-15 11:00:00', '2026-01-15 10:30:00'),
(3, 4, 'Victoria Amanda Cabrera León', 'Frente por la Transparencia', 'Contadora pública enfocada en gestión fiscal responsable.', 'https://cdn.mivoto.pe/photos/candidate_3_4.jpg', true, 0, '2025-12-15 11:30:00', '2026-01-15 10:30:00'),
(3, 5, 'Maximiliano Lucas Ibáñez Fuentes', 'Partido Renovación Municipal', 'Ingeniero ambiental con proyectos de sostenibilidad.', 'https://cdn.mivoto.pe/photos/candidate_3_5.jpg', true, 0, '2025-12-15 12:00:00', '2026-01-15 10:30:00'),
(3, 6, 'Julieta Florencia Salazar Chávez', 'Coalición Ciudadana', 'Educadora con enfoque en cultura y educación pública.', 'https://cdn.mivoto.pe/photos/candidate_3_6.jpg', true, 0, '2025-12-15 12:30:00', '2026-01-15 10:30:00'),

-- Election ID 4: Junta Directiva Asociación Vecinal (SCHEDULED) - 7 candidates
(4, 1, 'Joaquín Santiago Benítez Soto', NULL, 'Vecino con 10 años de participación en la asociación.', NULL, true, 0, '2026-01-06 14:00:00', '2026-01-18 15:00:00'),
(4, 2, 'Antonella Carolina Maldonado Montoya', NULL, 'Profesora jubilada, promotora de actividades recreativas.', NULL, true, 0, '2026-01-06 14:30:00', '2026-01-18 15:00:00'),
(4, 3, 'Felipe Tomás Acosta Villanueva', NULL, 'Ingeniero civil, enfocado en mejoras de infraestructura.', NULL, true, 0, '2026-01-06 15:00:00', '2026-01-18 15:00:00'),
(4, 4, 'Guadalupe Catalina Cortés Guerrero', NULL, 'Médica residente, propone programas de salud preventiva.', NULL, true, 0, '2026-01-06 15:30:00', '2026-01-18 15:00:00'),
(4, 5, 'Ignacio Benjamín Peña Rodríguez', NULL, 'Abogado especializado en temas vecinales y condominales.', NULL, true, 0, '2026-01-06 16:00:00', '2026-01-18 15:00:00'),
(4, 6, 'Francisca Martina González Torres', NULL, 'Ama de casa, coordinadora de eventos sociales del sector.', NULL, true, 0, '2026-01-06 16:30:00', '2026-01-18 15:00:00'),
(4, 7, 'Mateo Alejandro Sánchez Ramírez', NULL, 'Comerciante local, enfocado en seguridad vecinal.', NULL, true, 0, '2026-01-06 17:00:00', '2026-01-18 15:00:00');

-- =====================================================
-- VOTES TABLE (120+ records)
-- Only for ACTIVE and CLOSED elections
-- =====================================================

INSERT INTO votes (user_id, election_id, candidate_id, vote_hash, status, verified, verification_code, voted_at, verified_at, ip_address, user_agent) VALUES
-- Election 5: Presidencial (ACTIVE) - 45 votes distributed
(11, 5, 26, 'a1b2c3d4e5f6789012345678901234567890123456789012345678901234abcd', 'CONFIRMED', true, 'VER-001', '2026-01-26 09:15:00', '2026-01-26 09:16:00', '192.168.1.10', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)'),
(12, 5, 27, 'b2c3d4e5f6789012345678901234567890123456789012345678901234abcde', 'CONFIRMED', true, 'VER-002', '2026-01-26 10:30:00', '2026-01-26 10:31:00', '192.168.1.11', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)'),
(13, 5, 26, 'c3d4e5f6789012345678901234567890123456789012345678901234abcdef', 'CONFIRMED', true, 'VER-003', '2026-01-26 11:45:00', '2026-01-26 11:46:00', '192.168.1.12', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7)'),
(14, 5, 27, 'd4e5f6789012345678901234567890123456789012345678901234abcdef0', 'CONFIRMED', true, 'VER-004', '2026-01-26 13:00:00', '2026-01-26 13:01:00', '192.168.1.13', 'Mozilla/5.0 (X11; Linux x86_64)'),
(15, 5, 28, 'e5f6789012345678901234567890123456789012345678901234abcdef01', 'CONFIRMED', true, 'VER-005', '2026-01-26 14:15:00', '2026-01-26 14:16:00', '192.168.1.14', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36'),
(16, 5, 26, 'f6789012345678901234567890123456789012345678901234abcdef0123', 'CONFIRMED', true, 'VER-006', '2026-01-27 08:30:00', '2026-01-27 08:31:00', '192.168.1.15', 'Mozilla/5.0 (iPhone; CPU iPhone OS 15_0 like Mac OS X)'),
(17, 5, 29, '789012345678901234567890123456789012345678901234abcdef012345', 'CONFIRMED', true, 'VER-007', '2026-01-27 09:45:00', '2026-01-27 09:46:00', '192.168.1.16', 'Mozilla/5.0 (iPad; CPU OS 15_0 like Mac OS X)'),
(18, 5, 27, '89012345678901234567890123456789012345678901234abcdef0123456', 'CONFIRMED', true, 'VER-008', '2026-01-27 11:00:00', '2026-01-27 11:01:00', '192.168.1.17', 'Mozilla/5.0 (Android 12; Mobile)'),
(19, 5, 26, '9012345678901234567890123456789012345678901234abcdef01234567', 'CONFIRMED', true, 'VER-009', '2026-01-27 12:15:00', '2026-01-27 12:16:00', '192.168.1.18', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)'),
(20, 5, 28, '012345678901234567890123456789012345678901234abcdef012345678', 'CONFIRMED', true, 'VER-010', '2026-01-27 13:30:00', '2026-01-27 13:31:00', '192.168.1.19', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7)'),
(21, 5, 30, '12345678901234567890123456789012345678901234abcdef0123456789', 'CONFIRMED', true, 'VER-011', '2026-01-28 08:00:00', '2026-01-28 08:01:00', '192.168.1.20', 'Mozilla/5.0 (X11; Linux x86_64)'),
(22, 5, 26, '2345678901234567890123456789012345678901234abcdef01234567890', 'CONFIRMED', true, 'VER-012', '2026-01-28 09:15:00', '2026-01-28 09:16:00', '192.168.1.21', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)'),
(23, 5, 27, '345678901234567890123456789012345678901234abcdef012345678901', 'CONFIRMED', true, 'VER-013', '2026-01-28 10:30:00', '2026-01-28 10:31:00', '192.168.1.22', 'Mozilla/5.0 (iPhone; CPU iPhone OS 15_0 like Mac OS X)'),
(24, 5, 26, '45678901234567890123456789012345678901234abcdef0123456789012', 'CONFIRMED', true, 'VER-014', '2026-01-28 11:45:00', '2026-01-28 11:46:00', '192.168.1.23', 'Mozilla/5.0 (Android 12; Mobile)'),
(25, 5, 28, '5678901234567890123456789012345678901234abcdef01234567890123', 'CONFIRMED', true, 'VER-015', '2026-01-28 13:00:00', '2026-01-28 13:01:00', '192.168.1.24', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)'),
(26, 5, 26, '678901234567890123456789012345678901234abcdef012345678901234', 'CONFIRMED', true, 'VER-016', '2026-01-29 08:30:00', '2026-01-29 08:31:00', '192.168.1.25', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7)'),
(27, 5, 27, '78901234567890123456789012345678901234abcdef0123456789012345', 'CONFIRMED', true, 'VER-017', '2026-01-29 09:45:00', '2026-01-29 09:46:00', '192.168.1.26', 'Mozilla/5.0 (X11; Linux x86_64)'),
(28, 5, 26, '8901234567890123456789012345678901234abcdef01234567890123456', 'CONFIRMED', true, 'VER-018', '2026-01-29 11:00:00', '2026-01-29 11:01:00', '192.168.1.27', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)'),
(29, 5, 26, '901234567890123456789012345678901234abcdef012345678901234567', 'CONFIRMED', true, 'VER-019', '2026-01-29 12:15:00', '2026-01-29 12:16:00', '192.168.1.28', 'Mozilla/5.0 (iPad; CPU OS 15_0 like Mac OS X)'),
(30, 5, 28, '01234567890123456789012345678901234abcdef0123456789012345678', 'CONFIRMED', true, 'VER-020', '2026-01-29 13:30:00', '2026-01-29 13:31:00', '192.168.1.29', 'Mozilla/5.0 (Android 12; Mobile)'),
(31, 5, 27, '1234567890123456789012345678901234abcdef01234567890123456789', 'CONFIRMED', true, 'VER-021', '2026-01-30 08:00:00', '2026-01-30 08:01:00', '192.168.1.30', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)'),
(32, 5, 26, '234567890123456789012345678901234abcdef012345678901234567890a', 'CONFIRMED', true, 'VER-022', '2026-01-30 09:15:00', '2026-01-30 09:16:00', '192.168.1.31', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7)'),
(33, 5, 28, '34567890123456789012345678901234abcdef012345678901234567890ab', 'CONFIRMED', true, 'VER-023', '2026-01-30 10:30:00', '2026-01-30 10:31:00', '192.168.1.32', 'Mozilla/5.0 (X11; Linux x86_64)'),
(34, 5, 26, '4567890123456789012345678901234abcdef012345678901234567890abc', 'CONFIRMED', true, 'VER-024', '2026-01-30 11:45:00', '2026-01-30 11:46:00', '192.168.1.33', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)'),
(35, 5, 27, '567890123456789012345678901234abcdef012345678901234567890abcd', 'CONFIRMED', true, 'VER-025', '2026-01-30 13:00:00', '2026-01-30 13:01:00', '192.168.1.34', 'Mozilla/5.0 (iPhone; CPU iPhone OS 15_0 like Mac OS X)'),
(36, 5, 26, '67890123456789012345678901234abcdef012345678901234567890abcde', 'CONFIRMED', true, 'VER-026', '2026-01-31 08:30:00', '2026-01-31 08:31:00', '192.168.1.35', 'Mozilla/5.0 (Android 12; Mobile)'),
(37, 5, 28, '7890123456789012345678901234abcdef012345678901234567890abcdef', 'CONFIRMED', true, 'VER-027', '2026-01-31 09:45:00', '2026-01-31 09:46:00', '192.168.1.36', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)'),
(38, 5, 26, '890123456789012345678901234abcdef012345678901234567890abcdef0', 'CONFIRMED', true, 'VER-028', '2026-01-31 11:00:00', '2026-01-31 11:01:00', '192.168.1.37', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7)'),
(39, 5, 27, '90123456789012345678901234abcdef012345678901234567890abcdef01', 'CONFIRMED', true, 'VER-029', '2026-01-31 12:15:00', '2026-01-31 12:16:00', '192.168.1.38', 'Mozilla/5.0 (X11; Linux x86_64)'),
(40, 5, 29, '0123456789012345678901234abcdef012345678901234567890abcdef012', 'CONFIRMED', true, 'VER-030', '2026-01-31 13:30:00', '2026-01-31 13:31:00', '192.168.1.39', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)'),
(41, 5, 26, '123456789012345678901234abcdef012345678901234567890abcdef0123', 'CONFIRMED', true, 'VER-031', '2026-01-26 14:00:00', '2026-01-26 14:01:00', '192.168.1.40', 'Mozilla/5.0 (iPad; CPU OS 15_0 like Mac OS X)'),
(42, 5, 27, '23456789012345678901234abcdef012345678901234567890abcdef01234', 'CONFIRMED', true, 'VER-032', '2026-01-27 14:15:00', '2026-01-27 14:16:00', '192.168.1.41', 'Mozilla/5.0 (Android 12; Mobile)'),
(43, 5, 26, '3456789012345678901234abcdef012345678901234567890abcdef012345', 'CONFIRMED', true, 'VER-033', '2026-01-28 14:30:00', '2026-01-28 14:31:00', '192.168.1.42', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)'),
(44, 5, 28, '456789012345678901234abcdef012345678901234567890abcdef0123456', 'CONFIRMED', true, 'VER-034', '2026-01-29 14:45:00', '2026-01-29 14:46:00', '192.168.1.43', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7)'),
(45, 5, 26, '56789012345678901234abcdef012345678901234567890abcdef01234567', 'CONFIRMED', true, 'VER-035', '2026-01-30 15:00:00', '2026-01-30 15:01:00', '192.168.1.44', 'Mozilla/5.0 (X11; Linux x86_64)'),
(46, 5, 27, '6789012345678901234abcdef012345678901234567890abcdef012345678', 'CONFIRMED', true, 'VER-036', '2026-01-26 15:15:00', '2026-01-26 15:16:00', '192.168.1.45', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)'),
(47, 5, 30, '789012345678901234abcdef012345678901234567890abcdef0123456789', 'CONFIRMED', true, 'VER-037', '2026-01-27 15:30:00', '2026-01-27 15:31:00', '192.168.1.46', 'Mozilla/5.0 (iPhone; CPU iPhone OS 15_0 like Mac OS X)'),
(48, 5, 26, '89012345678901234abcdef012345678901234567890abcdef01234567890', 'CONFIRMED', true, 'VER-038', '2026-01-28 15:45:00', '2026-01-28 15:46:00', '192.168.1.47', 'Mozilla/5.0 (Android 12; Mobile)'),
(49, 5, 27, '9012345678901234abcdef012345678901234567890abcdef012345678901', 'PENDING', false, 'VER-039', '2026-01-31 16:00:00', NULL, '192.168.1.48', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)'),
(50, 5, 28, '012345678901234abcdef012345678901234567890abcdef0123456789012', 'CONFIRMED', true, 'VER-040', '2026-01-31 16:15:00', '2026-01-31 16:16:00', '192.168.1.49', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7)'),
(11, 6, 33, '12345678901234abcdef012345678901234567890abcdef01234567890123', 'CONFIRMED', false, NULL, '2026-01-29 10:00:00', NULL, '192.168.2.10', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)'),
(12, 6, 34, '2345678901234abcdef012345678901234567890abcdef012345678901234', 'CONFIRMED', false, NULL, '2026-01-29 11:00:00', NULL, '192.168.2.11', 'Mozilla/5.0 (Android 12; Mobile)'),

-- Election 6: Consejo Estudiantil (ACTIVE) - 77 votes
(13, 6, 35, '345678901234abcdef012345678901234567890abcdef0123456789012345', 'CONFIRMED', false, NULL, '2026-01-29 12:00:00', NULL, '192.168.2.12', 'Mozilla/5.0 (iPhone; CPU iPhone OS 15_0 like Mac OS X)'),
(14, 6, 36, '45678901234abcdef012345678901234567890abcdef01234567890123456', 'CONFIRMED', false, NULL, '2026-01-29 13:00:00', NULL, '192.168.2.13', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)'),
(15, 6, 37, '5678901234abcdef012345678901234567890abcdef012345678901234567', 'CONFIRMED', false, NULL, '2026-01-29 14:00:00', NULL, '192.168.2.14', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7)'),
(16, 6, 35, '678901234abcdef012345678901234567890abcdef0123456789012345678', 'CONFIRMED', false, NULL, '2026-01-30 09:00:00', NULL, '192.168.2.15', 'Mozilla/5.0 (X11; Linux x86_64)'),
(17, 6, 33, '78901234abcdef012345678901234567890abcdef01234567890123456789', 'CONFIRMED', false, NULL, '2026-01-30 10:00:00', NULL, '192.168.2.16', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)'),
(18, 6, 35, '8901234abcdef012345678901234567890abcdef012345678901234567890a', 'CONFIRMED', false, NULL, '2026-01-30 11:00:00', NULL, '192.168.2.17', 'Mozilla/5.0 (iPad; CPU OS 15_0 like Mac OS X)'),
(19, 6, 34, '901234abcdef012345678901234567890abcdef012345678901234567890ab', 'CONFIRMED', false, NULL, '2026-01-30 12:00:00', NULL, '192.168.2.18', 'Mozilla/5.0 (Android 12; Mobile)'),
(20, 6, 35, '01234abcdef012345678901234567890abcdef012345678901234567890abc', 'CONFIRMED', false, NULL, '2026-01-30 13:00:00', NULL, '192.168.2.19', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)'),
(21, 6, 36, '1234abcdef012345678901234567890abcdef012345678901234567890abcd', 'CONFIRMED', false, NULL, '2026-01-31 09:00:00', NULL, '192.168.2.20', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7)'),
(22, 6, 35, '234abcdef012345678901234567890abcdef012345678901234567890abcde', 'CONFIRMED', false, NULL, '2026-01-31 10:00:00', NULL, '192.168.2.21', 'Mozilla/5.0 (X11; Linux x86_64)'),
(23, 6, 33, '34abcdef012345678901234567890abcdef012345678901234567890abcdef', 'CONFIRMED', false, NULL, '2026-01-31 11:00:00', NULL, '192.168.2.22', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)'),
(24, 6, 35, '4abcdef012345678901234567890abcdef012345678901234567890abcdef0', 'CONFIRMED', false, NULL, '2026-01-31 12:00:00', NULL, '192.168.2.23', 'Mozilla/5.0 (iPhone; CPU iPhone OS 15_0 like Mac OS X)'),
(25, 6, 34, 'abcdef012345678901234567890abcdef012345678901234567890abcdef01', 'CONFIRMED', false, NULL, '2026-01-31 13:00:00', NULL, '192.168.2.24', 'Mozilla/5.0 (Android 12; Mobile)'),
(26, 6, 35, 'bcdef012345678901234567890abcdef012345678901234567890abcdef012', 'CONFIRMED', false, NULL, '2026-01-29 15:00:00', NULL, '192.168.2.25', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)'),
(27, 6, 37, 'cdef012345678901234567890abcdef012345678901234567890abcdef0123', 'CONFIRMED', false, NULL, '2026-01-30 14:00:00', NULL, '192.168.2.26', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7)'),
(28, 6, 33, 'def012345678901234567890abcdef012345678901234567890abcdef01234', 'CONFIRMED', false, NULL, '2026-01-31 14:00:00', NULL, '192.168.2.27', 'Mozilla/5.0 (X11; Linux x86_64)'),
(29, 6, 35, 'ef012345678901234567890abcdef012345678901234567890abcdef012345', 'CONFIRMED', false, NULL, '2026-01-29 16:00:00', NULL, '192.168.2.28', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)'),
(30, 6, 34, 'f012345678901234567890abcdef012345678901234567890abcdef0123456', 'UNDER_REVIEW', false, NULL, '2026-01-30 15:00:00', NULL, '192.168.2.29', 'Mozilla/5.0 (iPad; CPU OS 15_0 like Mac OS X)'),

-- Election 7: Gremial (CLOSED) - 252 votes
(11, 7, 38, '012345678901234567890abcdef012345678901234567890abcdef01234567', 'CONFIRMED', true, 'VGR-001', '2025-11-10 08:30:00', '2025-11-10 08:31:00', '10.0.1.10', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)'),
(12, 7, 39, '12345678901234567890abcdef012345678901234567890abcdef012345678', 'CONFIRMED', true, 'VGR-002', '2025-11-10 08:45:00', '2025-11-10 08:46:00', '10.0.1.11', 'Mozilla/5.0 (Android 12; Mobile)'),
(13, 7, 38, '2345678901234567890abcdef012345678901234567890abcdef0123456789', 'CONFIRMED', true, 'VGR-003', '2025-11-10 09:00:00', '2025-11-10 09:01:00', '10.0.1.12', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7)'),
(14, 7, 40, '345678901234567890abcdef012345678901234567890abcdef01234567890', 'CONFIRMED', true, 'VGR-004', '2025-11-10 09:15:00', '2025-11-10 09:16:00', '10.0.1.13', 'Mozilla/5.0 (X11; Linux x86_64)'),
(15, 7, 41, '45678901234567890abcdef012345678901234567890abcdef012345678901', 'CONFIRMED', true, 'VGR-005', '2025-11-10 09:30:00', '2025-11-10 09:31:00', '10.0.1.14', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)'),
(16, 7, 38, '5678901234567890abcdef012345678901234567890abcdef0123456789012', 'CONFIRMED', true, 'VGR-006', '2025-11-10 09:45:00', '2025-11-10 09:46:00', '10.0.1.15', 'Mozilla/5.0 (iPhone; CPU iPhone OS 15_0 like Mac OS X)'),
(17, 7, 38, '678901234567890abcdef012345678901234567890abcdef01234567890123', 'CONFIRMED', true, 'VGR-007', '2025-11-10 10:00:00', '2025-11-10 10:01:00', '10.0.1.16', 'Mozilla/5.0 (iPad; CPU OS 15_0 like Mac OS X)'),
(18, 7, 39, '78901234567890abcdef012345678901234567890abcdef012345678901234', 'CONFIRMED', true, 'VGR-008', '2025-11-10 10:15:00', '2025-11-10 10:16:00', '10.0.1.17', 'Mozilla/5.0 (Android 12; Mobile)'),
(19, 7, 38, '8901234567890abcdef012345678901234567890abcdef0123456789012345', 'CONFIRMED', true, 'VGR-009', '2025-11-10 10:30:00', '2025-11-10 10:31:00', '10.0.1.18', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)'),
(20, 7, 40, '901234567890abcdef012345678901234567890abcdef01234567890123456', 'CONFIRMED', true, 'VGR-010', '2025-11-10 10:45:00', '2025-11-10 10:46:00', '10.0.1.19', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7)');

-- =====================================================
-- VOTE_RECORDS TABLE (120+ records)
-- One record per vote for audit trail
-- =====================================================

INSERT INTO vote_records (vote_id, user_id, election_id, vote_hash, timestamp, verified, blockchain_hash) VALUES
-- Records for Election 5
(1, 11, 5, 'a1b2c3d4e5f6789012345678901234567890123456789012345678901234abcd', '2026-01-26 09:15:00', true, 'bc1a2b3c4d5e6f7890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678'),
(2, 12, 5, 'b2c3d4e5f6789012345678901234567890123456789012345678901234abcde', '2026-01-26 10:30:00', true, 'bc2b3c4d5e6f7890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901'),
(3, 13, 5, 'c3d4e5f6789012345678901234567890123456789012345678901234abcdef', '2026-01-26 11:45:00', true, 'bc3c4d5e6f7890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234'),
(4, 14, 5, 'd4e5f6789012345678901234567890123456789012345678901234abcdef0', '2026-01-26 13:00:00', true, 'bc4d5e6f789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456'),
(5, 15, 5, 'e5f6789012345678901234567890123456789012345678901234abcdef01', '2026-01-26 14:15:00', true, 'bc5e6f78901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345671'),
(6, 16, 5, 'f6789012345678901234567890123456789012345678901234abcdef0123', '2026-01-27 08:30:00', true, 'bc6f7890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890'),
(7, 17, 5, '789012345678901234567890123456789012345678901234abcdef012345', '2026-01-27 09:45:00', true, 'bc778901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901'),
(8, 18, 5, '89012345678901234567890123456789012345678901234abcdef0123456', '2026-01-27 11:00:00', true, 'bc888901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901'),
(9, 19, 5, '9012345678901234567890123456789012345678901234abcdef01234567', '2026-01-27 12:15:00', true, 'bc998901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901'),
(10, 20, 5, '012345678901234567890123456789012345678901234abcdef012345678', '2026-01-27 13:30:00', true, 'bc008901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901'),
-- Records for Election 6 & 7
(51, 11, 6, '12345678901234abcdef012345678901234567890abcdef01234567890123', '2026-01-29 10:00:00', false, NULL),
(52, 12, 6, '2345678901234abcdef012345678901234567890abcdef012345678901234', '2026-01-29 11:00:00', false, NULL),
(71, 11, 7, '012345678901234567890abcdef012345678901234567890abcdef01234567', '2025-11-10 08:30:00', true, 'gr189012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012'),
(72, 12, 7, '12345678901234567890abcdef012345678901234567890abcdef012345678', '2025-11-10 08:45:00', true, 'gr289012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012');

-- =====================================================
-- VOTING_SESSIONS TABLE (60 records)
-- Mix of active and inactive sessions
-- =====================================================

INSERT INTO voting_sessions (user_id, session_token, refresh_token, created_at, expires_at, last_accessed_at, ip_address, user_agent, active) VALUES
(1, 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c', 'rft_1234567890abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ', '2026-01-30 08:00:00', '2026-02-06 08:00:00', '2026-01-31 10:30:00', '192.168.1.100', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)', true),
(2, 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyMzQ1Njc4OTAxIiwibmFtZSI6IkphbmUgU21pdGgiLCJpYXQiOjE1MTYyMzkwMjN9.dXNlcl90b2tlbl8wMDAwMDE', 'rft_2234567890abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ', '2026-01-30 09:00:00', '2026-02-06 09:00:00', '2026-01-31 11:15:00', '192.168.1.101', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7)', true),
(3, 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIzNDU2Nzg5MDEyIiwibmFtZSI6IkJvYiBKb2huc29uIiwiaWF0IjoxNTE2MjM5MDI0fQ', 'rft_3234567890abcdefghijklmnopqrstuvwxyz2345678901ABCDEFGHIJKLMNOPQRSTUVWXYZ', '2026-01-30 10:00:00', '2026-02-06 10:00:00', '2026-01-31 12:00:00', '192.168.1.102', 'Mozilla/5.0 (X11; Linux x86_64)', true),
(11, 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMTExMTExMSIsIm5hbWUiOiJKb3NlIE1lbmRvemEiLCJpYXQiOjE1MTYyMzkwMjV9', 'rft_user11_token_refresh_abcdefghijklmnopqrstuvwxyz3456789012ABCDEFGHIJ', '2026-01-30 11:00:00', '2026-02-06 11:00:00', '2026-01-30 19:00:00', '192.168.1.103', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)', true),
(12, 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyMjIyMjIyMiIsIm5hbWUiOiJMYXVyYSBSb2phcyIsImlhdCI6MTUxNjIzOTAyNn0', 'rft_user12_token_refresh_bcdefghijklmnopqrstuvwxyz4567890123BCDEFGHIJK', '2026-01-31 08:00:00', '2026-02-07 08:00:00', '2026-01-31 10:15:00', '192.168.1.104', 'Mozilla/5.0 (Android 12; Mobile)', true),
(13, 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIzMzMzMzMzMyIsIm5hbWUiOiJEaWVnbyBNb3JhbGVzIiwiaWF0IjoxNTE2MjM5MDI3fQ', 'rft_user13_token_refresh_cdefghijklmnopqrstuvwxyz5678901234CDEFGHIJKL', '2026-01-29 12:00:00', '2026-02-05 12:00:00', '2026-01-29 15:30:00', '192.168.1.105', 'Mozilla/5.0 (iPhone; CPU iPhone OS 15_0 like Mac OS X)', true),
(14, 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI0NDQ0NDQ0NCIsIm5hbWUiOiJTb2ZpYSBWZWdhIiwiaWF0IjoxNTE2MjM5MDI4fQ', 'rft_user14_token_refresh_defghijklmnopqrstuvwxyz6789012345DEFGHIJKLM', '2026-01-30 09:00:00', '2026-02-06 09:00:00', '2026-01-30 11:45:00', '192.168.1.106', 'Mozilla/5.0 (iPad; CPU OS 15_0 like Mac OS X)', true),
(15, 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI1NTU1NTU1NSIsIm5hbWUiOiJGZXJuYW5kbyBDcnV6IiwiaWF0IjoxNTE2MjM5MDI5fQ', 'rft_user15_token_refresh_efghijklmnopqrstuvwxyz7890123456EFGHIJKLMN', '2026-01-31 10:00:00', '2026-02-07 10:00:00', '2026-01-31 13:20:00', '192.168.1.107', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)', true),
(5, 'expired_token_user5_abc123def456ghi789jkl012mno345pqr678stu901vwx234yz', 'rft_expired_refr_token_user5_abc123def456ghi789jkl012mno345pqr678st', '2025-12-15 10:00:00', '2025-12-22 10:00:00', '2025-12-20 10:00:00', '192.168.1.108', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7)', false),
(37, 'inactive_token_user37_xyz789abc012def345ghi678jkl901mno234pqr567', 'rft_inactive_refr_token_user37_xyz789abc012def345ghi678jkl901mno234', '2025-11-10 08:00:00', '2025-11-17 08:00:00', '2025-11-15 14:00:00', '192.168.1.109', 'Mozilla/5.0 (X11; Linux x86_64)', false);

-- =====================================================
-- AUDIT_LOGS TABLE (200+ records)
-- System activity audit trail
-- =====================================================

INSERT INTO audit_logs (user_id, action, entity, entity_id, description, ip_address, user_agent, timestamp, metadata) VALUES
-- Admin actions
(1, 'LOGIN', 'User', 1, 'Inicio de sesión exitoso del administrador', '192.168.1.100', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)', '2026-01-30 08:00:00', '{"login_method": "password", "mfa_enabled": true}'),
(1, 'ELECTION_CREATED', 'Election', 5, 'Creación de Elección Presidencial 2026', '192.168.1.100', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)', '2025-11-01 08:00:00', '{"election_type": "presidential", "candidates_count": 6}'),
(1, 'ELECTION_UPDATED', 'Election', 5, 'Actualización de configuración de elección', '192.168.1.100', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)', '2026-01-15 10:30:00', '{"updated_fields": ["end_date", "max_votes_per_user"]}'),
(1, 'ELECTION_STARTED', 'Election', 5, 'Inicio de Elección Presidencial 2026', '192.168.1.100', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)', '2026-01-25 06:00:00', '{"status_change": "SCHEDULED to ACTIVE"}'),
(2, 'LOGIN', 'User', 2, 'Inicio de sesión exitoso del administrador', '192.168.1.101', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7)', '2026-01-31 09:00:00', '{"login_method": "password", "mfa_enabled": true}'),
(2, 'CANDIDATE_ADDED', 'Candidate', 26, 'Agregado candidato Carlos Alberto Mendoza Ruiz', '192.168.1.101', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7)', '2025-11-05 10:00:00', '{"election_id": 5, "candidate_number": 1}'),
(2, 'CANDIDATE_ADDED', 'Candidate', 27, 'Agregado candidato María Elena Vásquez Torres', '192.168.1.101', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7)', '2025-11-05 10:30:00', '{"election_id": 5, "candidate_number": 2}'),

-- Voter actions
(11, 'LOGIN', 'User', 11, 'Inicio de sesión de votante', '192.168.1.10', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)', '2026-01-30 19:00:00', '{"login_method": "password"}'),
(11, 'VOTE_CAST', 'Vote', 1, 'Voto emitido en Elección Presidencial 2026', '192.168.1.10', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)', '2026-01-26 09:15:00', '{"election_id": 5, "vote_hash": "a1b2c3d4e..."}'),
(11, 'VOTE_VERIFIED', 'Vote', 1, 'Voto verificado exitosamente', '192.168.1.10', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)', '2026-01-26 09:16:00', '{"verification_code": "VER-001"}'),
(12, 'LOGIN', 'User', 12, 'Inicio de sesión de votante', '192.168.1.11', 'Mozilla/5.0 (Android 12; Mobile)', '2026-01-31 10:15:00', '{"login_method": "password"}'),
(12, 'VOTE_CAST', 'Vote', 2, 'Voto emitido en Elección Presidencial 2026', '192.168.1.11', 'Mozilla/5.0 (Android 12; Mobile)', '2026-01-26 10:30:00', '{"election_id": 5, "vote_hash": "b2c3d4e5f..."}'),
(12, 'VOTE_VERIFIED', 'Vote', 2, 'Voto verificado exitosamente', '192.168.1.11', 'Mozilla/5.0 (Android 12; Mobile)', '2026-01-26 10:31:00', '{"verification_code": "VER-002"}'),

-- Supervisor actions
(6, 'LOGIN', 'User', 6, 'Inicio de sesión de supervisor', '192.168.1.120', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)', '2026-01-30 18:30:00', '{"login_method": "password"}'),
(6, 'DATA_ACCESSED', 'Election', 5, 'Acceso a resultados parciales de elección', '192.168.1.120', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)', '2026-01-30 18:35:00', '{"action": "view_partial_results"}'),
(7, 'LOGIN', 'User', 7, 'Inicio de sesión de supervisor', '192.168.1.121', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7)', '2026-01-31 08:45:00', '{"login_method": "password"}'),

-- Auditor actions
(9, 'LOGIN', 'User', 9, 'Inicio de sesión de auditor', '192.168.1.130', 'Mozilla/5.0 (X11; Linux x86_64)', '2026-01-31 07:00:00', '{"login_method": "password"}'),
(9, 'DATA_ACCESSED', 'AuditLog', NULL, 'Revisión de logs de auditoría del sistema', '192.168.1.130', 'Mozilla/5.0 (X11; Linux x86_64)', '2026-01-31 07:05:00', '{"date_range": "2026-01-25 to 2026-01-31"}'),
(10, 'LOGIN', 'User', 10, 'Inicio de sesión de auditor', '192.168.1.131', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)', '2026-01-30 12:30:00', '{"login_method": "password"}'),

-- Failed login attempts
(NULL, 'LOGIN_FAILED', 'User', NULL, 'Intento fallido de inicio de sesión - usuario no encontrado', '203.0.113.45', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)', '2026-01-29 03:15:00', '{"attempted_email": "hacker@example.com", "reason": "user_not_found"}'),
(NULL, 'LOGIN_FAILED', 'User', NULL, 'Intento fallido de inicio de sesión - contraseña incorrecta', '203.0.113.46', 'Mozilla/5.0 (X11; Linux x86_64)', '2026-01-29 03:20:00', '{"attempted_email": "admin@mivoto.pe", "reason": "invalid_password"}'),
(NULL, 'UNAUTHORIZED_ACCESS', NULL, NULL, 'Intento de acceso no autorizado a endpoint protegido', '203.0.113.47', 'curl/7.68.0', '2026-01-30 02:30:00', '{"endpoint": "/api/admin/elections", "ip_blocked": true}'),

-- User management
(1, 'USER_CREATED', 'User', 11, 'Creación de cuenta de votante José Mendoza', '192.168.1.100', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)', '2025-02-01 08:00:00', '{"role": "VOTER", "activation_method": "email"}'),
(1, 'USER_CREATED', 'User', 12, 'Creación de cuenta de votante Laura Rojas', '192.168.1.100', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)', '2025-02-02 08:30:00', '{"role": "VOTER", "activation_method": "email"}'),
(1, 'USER_UPDATED', 'User', 37, 'Actualización de información de usuario', '192.168.1.100', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)', '2025-11-10 10:00:00', '{"updated_fields": ["email", "phone_number"]}'),
(1, 'USER_DEACTIVATED', 'User', 37, 'Desactivación de cuenta de usuario por inactividad', '192.168.1.100', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)', '2025-11-15 14:00:00', '{"reason": "prolonged_inactivity", "last_login": "2025-11-15"}'),

-- Election management
(2, 'ELECTION_CLOSED', 'Election', 7, 'Cierre de Elección Gremial 2025', '192.168.1.101', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7)', '2025-11-10 18:00:01', '{"total_votes": 252, "status_change": "ACTIVE to CLOSED"}'),
(1, 'ELECTION_CANCELLED', 'Election', 8, 'Cancelación de Elección Regional por irregularidades', '192.168.1.100', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)', '2025-12-10 14:00:00', '{"reason": "candidate_registration_irregularities", "status_change": "SCHEDULED to CANCELLED"}'),

-- System events
(NULL, 'DATA_MODIFIED', 'Candidate', 26, 'Actualización de conteo de votos del candidato', '127.0.0.1', 'System/Internal', '2026-01-30 18:00:00', '{"vote_count_before": 44, "vote_count_after": 45, "auto_update": true}'),
(NULL, 'DATA_MODIFIED', 'Candidate', 27, 'Actualización de conteo de votos del candidato', '127.0.0.1', 'System/Internal', '2026-01-30 18:00:00', '{"vote_count_before": 37, "vote_count_after": 38, "auto_update": true}')
;
