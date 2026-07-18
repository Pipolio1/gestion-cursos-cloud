-- ============================================================
-- Script de creacion de base de datos Oracle
-- Proyecto: Gestion de Cursos Cloud Native
-- Autor: Duoc UC - Desarrollo Cloud Native
-- ============================================================

-- Eliminar tablas si existen (solo para desarrollo)
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE archivos CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE calificaciones CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE examenes CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE inscripciones CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE cursos CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE estudiantes CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

-- ============================================================
-- Secuencias
-- ============================================================
DROP SEQUENCE estudiante_seq;
DROP SEQUENCE curso_seq;
DROP SEQUENCE inscripcion_seq;
DROP SEQUENCE examen_seq;
DROP SEQUENCE calificacion_seq;
DROP SEQUENCE archivo_seq;

CREATE SEQUENCE estudiante_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE curso_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE inscripcion_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE examen_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE calificacion_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE archivo_seq START WITH 1 INCREMENT BY 1;

-- ============================================================
-- Tabla: estudiantes
-- ============================================================
CREATE TABLE estudiantes (
    id NUMBER(19) PRIMARY KEY,
    nombre VARCHAR2(255) NOT NULL,
    email VARCHAR2(255) NOT NULL UNIQUE,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE estudiantes IS 'Estudiantes registrados en la plataforma';
COMMENT ON COLUMN estudiantes.id IS 'Identificador unico del estudiante';
COMMENT ON COLUMN estudiantes.nombre IS 'Nombre completo del estudiante';
COMMENT ON COLUMN estudiantes.email IS 'Correo electronico del estudiante';
COMMENT ON COLUMN estudiantes.fecha_registro IS 'Fecha de registro del estudiante';

-- ============================================================
-- Tabla: cursos
-- ============================================================
CREATE TABLE cursos (
    id NUMBER(19) PRIMARY KEY,
    titulo VARCHAR2(255) NOT NULL,
    descripcion CLOB,
    instructor_email VARCHAR2(255) NOT NULL,
    fecha_inicio DATE,
    fecha_fin DATE,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE cursos IS 'Cursos disponibles en la plataforma';
COMMENT ON COLUMN cursos.id IS 'Identificador unico del curso';
COMMENT ON COLUMN cursos.titulo IS 'Titulo del curso';
COMMENT ON COLUMN cursos.descripcion IS 'Descripcion del curso';
COMMENT ON COLUMN cursos.instructor_email IS 'Email del instructor responsable';
COMMENT ON COLUMN cursos.fecha_inicio IS 'Fecha de inicio del curso';
COMMENT ON COLUMN cursos.fecha_fin IS 'Fecha de termino del curso';
COMMENT ON COLUMN cursos.fecha_registro IS 'Fecha de creacion del curso';

-- ============================================================
-- Tabla: inscripciones
-- ============================================================
CREATE TABLE inscripciones (
    id NUMBER(19) PRIMARY KEY,
    estudiante_id NUMBER(19) NOT NULL,
    curso_id NUMBER(19) NOT NULL,
    fecha_inscripcion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR2(50) DEFAULT 'ACTIVA' NOT NULL,
    CONSTRAINT fk_inscripcion_estudiante FOREIGN KEY (estudiante_id) REFERENCES estudiantes(id),
    CONSTRAINT fk_inscripcion_curso FOREIGN KEY (curso_id) REFERENCES cursos(id)
);

CREATE INDEX idx_inscripcion_estudiante ON inscripciones(estudiante_id);
CREATE INDEX idx_inscripcion_curso ON inscripciones(curso_id);

COMMENT ON TABLE inscripciones IS 'Inscripciones de estudiantes en cursos';
COMMENT ON COLUMN inscripciones.estado IS 'Estado de la inscripcion: ACTIVA, COMPLETADA, CANCELADA';

-- ============================================================
-- Tabla: examenes
-- ============================================================
CREATE TABLE examenes (
    id NUMBER(19) PRIMARY KEY,
    titulo VARCHAR2(255) NOT NULL,
    curso_id NUMBER(19) NOT NULL,
    fecha_examen TIMESTAMP,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_examen_curso FOREIGN KEY (curso_id) REFERENCES cursos(id)
);

CREATE INDEX idx_examen_curso ON examenes(curso_id);

COMMENT ON TABLE examenes IS 'Examenes asociados a cada curso';

-- ============================================================
-- Tabla: calificaciones
-- ============================================================
CREATE TABLE calificaciones (
    id NUMBER(19) PRIMARY KEY,
    examen_id NUMBER(19) NOT NULL,
    estudiante_id NUMBER(19) NOT NULL,
    nota NUMBER(5,2) NOT NULL,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_calificacion_examen FOREIGN KEY (examen_id) REFERENCES examenes(id),
    CONSTRAINT fk_calificacion_estudiante FOREIGN KEY (estudiante_id) REFERENCES estudiantes(id),
    CONSTRAINT uq_calificacion_examen_estudiante UNIQUE (examen_id, estudiante_id)
);

CREATE INDEX idx_calificacion_examen ON calificaciones(examen_id);
CREATE INDEX idx_calificacion_estudiante ON calificaciones(estudiante_id);

COMMENT ON TABLE calificaciones IS 'Calificaciones de estudiantes en examenes';
COMMENT ON COLUMN calificaciones.nota IS 'Nota del estudiante, escala 1.0 a 7.0';

-- ============================================================
-- Tabla: archivos
-- ============================================================
CREATE TABLE archivos (
    id NUMBER(19) PRIMARY KEY,
    nombre VARCHAR2(500) NOT NULL,
    s3_key VARCHAR2(1000) NOT NULL,
    url_publica VARCHAR2(2000),
    tipo_contenido VARCHAR2(255),
    curso_id NUMBER(19),
    fecha_subida TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_archivo_curso FOREIGN KEY (curso_id) REFERENCES cursos(id)
);

CREATE INDEX idx_archivo_curso ON archivos(curso_id);

COMMENT ON TABLE archivos IS 'Archivos subidos a S3 y asociados a cursos';
COMMENT ON COLUMN archivos.s3_key IS 'Clave del objeto dentro del bucket S3';

-- ============================================================
-- Datos de prueba (opcional)
-- ============================================================
INSERT INTO estudiantes (id, nombre, email) VALUES (estudiante_seq.NEXTVAL, 'Juan Perez', 'juan.perez@duocuc.cl');
INSERT INTO estudiantes (id, nombre, email) VALUES (estudiante_seq.NEXTVAL, 'Maria Gonzalez', 'maria.gonzalez@duocuc.cl');

INSERT INTO cursos (id, titulo, descripcion, instructor_email, fecha_inicio, fecha_fin)
VALUES (curso_seq.NEXTVAL, 'Desarrollo Cloud Native', 'Curso de desarrollo cloud native con Spring Boot', 'profesor@duocuc.cl', DATE '2025-03-01', DATE '2025-06-30');

INSERT INTO inscripciones (id, estudiante_id, curso_id, estado)
VALUES (inscripcion_seq.NEXTVAL, 1, 1, 'ACTIVA');

INSERT INTO examenes (id, titulo, curso_id, fecha_examen)
VALUES (examen_seq.NEXTVAL, 'Evaluacion 1', 1, TIMESTAMP '2025-04-15 10:00:00');

INSERT INTO calificaciones (id, examen_id, estudiante_id, nota)
VALUES (calificacion_seq.NEXTVAL, 1, 1, 6.5);

COMMIT;

-- ============================================================
-- Fin del script
-- ============================================================
