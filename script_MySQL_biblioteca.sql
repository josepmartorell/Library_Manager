
DROP DATABASE IF EXISTS `biblioteca`;

CREATE DATABASE IF NOT EXISTS `biblioteca` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `biblioteca`;

DROP TABLE IF EXISTS libros;
DROP TABLE IF EXISTS generos;
DROP TABLE IF EXISTS actividad_usuarios;
DROP TABLE IF EXISTS codificacion_actividades;
DROP TABLE IF EXISTS usuarios_biblioteca;

CREATE TABLE usuarios_biblioteca
( 
  id_usuario               CHAR(15),
  password                 VARCHAR(12), 
  CONSTRAINT pk_usuariosbiblioteca PRIMARY KEY (id_usuario)
);

CREATE TABLE codificacion_actividades
(  
  codigo                   INT,
  descripcion              VARCHAR(50),
  CONSTRAINT pk_codificacionactividades PRIMARY KEY (codigo)
);

CREATE TABLE actividad_usuarios
( 
  fecha_hora               DATETIME,
  id_usuario               CHAR(15),
  ip_cliente               VARCHAR(18), 
  codigo                   INT,
  CONSTRAINT fk_actividad_codificacion FOREIGN KEY (codigo) REFERENCES codificacion_actividades (codigo)  
);

CREATE TABLE generos
(  
  codigo                   CHAR(1),
  descripcion              VARCHAR(32),
  CONSTRAINT pk_generos PRIMARY KEY (codigo)
);

CREATE TABLE libros
(  
  id_libro                 CHAR(5),
  titulo                   VARCHAR(60),
  genero                   CHAR(1),
  fecha_edicion            DATETIME,
  numero_paginas           INT,
  premiado                 INT,
  CONSTRAINT pk_libros PRIMARY KEY (id_libro),
  CONSTRAINT fk_libros_generos FOREIGN KEY (genero) REFERENCES generos (codigo)  
);


-- -----------------------------------------------------------------------------------------------------------------------------
--     MEDIANTE LA SIGUIENTE TABLA, PROCEDIMIENTOS Y FUNCION SIMULAMOS EN MySQL LAS SECUENCIAS DE ORACLE
-- -----------------------------------------------------------------------------------------------------------------------------

DROP TABLE IF EXISTS secuencias;
DROP PROCEDURE IF EXISTS crea_secuencia;
DROP PROCEDURE IF EXISTS elimina_secuencia;
DROP PROCEDURE IF EXISTS secuencia_set_valor;
DROP PROCEDURE IF EXISTS secuencia_set_incremento;
DROP FUNCTION IF EXISTS secuencia_next_valor;


CREATE TABLE secuencias
(
  secuencia_nombre VARCHAR(35) NOT NULL PRIMARY KEY,
  secuencia_valor INT UNSIGNED NOT NULL,
  secuencia_incremento INT UNSIGNED NOT NULL
);


DELIMITER //

CREATE PROCEDURE crea_secuencia(secuenciaNombre VARCHAR(35), valorIncialSecuencia INT UNSIGNED, incremento INT UNSIGNED)
BEGIN
    IF (SELECT COUNT(*) FROM secuencias WHERE secuencia_nombre = secuenciaNombre) = 0 THEN
        INSERT INTO secuencias (secuencia_nombre, secuencia_valor, secuencia_incremento) VALUES (secuenciaNombre, valorIncialSecuencia, incremento);
    ELSE
        SIGNAL SQLSTATE '50000' SET MESSAGE_TEXT = "No puede crear la secuencia especificada porque ya existe";
    END IF;
END//


CREATE PROCEDURE elimina_secuencia(secuenciaNombre VARCHAR(35))
BEGIN
    IF (SELECT COUNT(*) FROM secuencias WHERE secuencia_nombre = secuenciaNombre) > 0 THEN
        DELETE FROM secuencias WHERE secuencia_nombre = secuenciaNombre;     
    ELSE
        SIGNAL SQLSTATE '50000' SET MESSAGE_TEXT = "La secuencia especificada no existe";
    END IF;
END//


CREATE PROCEDURE secuencia_set_valor(secuenciaNombre VARCHAR(35), secuenciaValor INT UNSIGNED)
BEGIN
    IF (SELECT COUNT(*) FROM secuencias WHERE secuencia_nombre = secuenciaNombre) > 0 THEN
        UPDATE secuencias SET secuencia_valor = secuenciaValor WHERE secuencia_nombre = secuenciaNombre;      
    ELSE
        SIGNAL SQLSTATE '50000' SET MESSAGE_TEXT = "No existe la secuencia";
    END IF;
END//


CREATE PROCEDURE secuencia_set_incremento(secuenciaNombre VARCHAR(35), incremento INT UNSIGNED)
BEGIN
    IF (SELECT COUNT(*) FROM secuencias WHERE secuencia_nombre = secuenciaNombre) > 0 THEN
        UPDATE secuencias SET secuencia_incremento = incremento WHERE secuencia_nombre = secuenciaNombre;      
    ELSE
        SIGNAL SQLSTATE '50000' SET MESSAGE_TEXT = "No existe la secuencia";
    END IF;
END//


CREATE FUNCTION secuencia_next_valor(secuenciaNombre VARCHAR(35)) RETURNS INT UNSIGNED
BEGIN
    DECLARE valorActual INT;

    SET valorActual = (SELECT secuencia_valor FROM secuencias WHERE secuencia_nombre = secuenciaNombre);
    IF valorActual IS NOT NULL THEN
        UPDATE secuencias SET secuencia_valor = valorActual + secuencia_incremento  WHERE secuencia_nombre = secuenciaNombre;        
    ELSE
        SIGNAL SQLSTATE '50000' SET MESSAGE_TEXT = "No existe la secuencia";
    END IF;

    RETURN valorActual;
END//

DELIMITER ;

-- -----------------------------------------------------------------------------------------------------------------------------

-- CALL elimina_secuencia("secuencia_libros");

CALL crea_secuencia("secuencia_libros", 1, 1);

-- CALL secuencia_set_valor("secuencia_libros", 100);

-- CALL secuencia_set_incremento("secuencia_libros", 5);

-- SELECT secuencia_next_valor("secuencia_libros");

-- -----------------------------------------------------------------------------------------------------------------------------

INSERT INTO usuarios_biblioteca VALUES ('usuario1','password1');
INSERT INTO usuarios_biblioteca VALUES ('usuario2','password2');
INSERT INTO usuarios_biblioteca VALUES ('usuario3','password3');

INSERT INTO codificacion_actividades VALUES (1,'op_menu - Conexion');
INSERT INTO codificacion_actividades VALUES (2,'op_menu - Desconexion');
INSERT INTO codificacion_actividades VALUES (3,'autenticacion');
INSERT INTO codificacion_actividades VALUES (4,'desconectar');
INSERT INTO codificacion_actividades VALUES (5,'op_menu - ConexionEfectuada');
INSERT INTO codificacion_actividades VALUES (6,'op_menu - Volcado a BD');
INSERT INTO codificacion_actividades VALUES (7,'volcarIncidencias');
INSERT INTO codificacion_actividades VALUES (8,'op_menu - Vista Formulario');
INSERT INTO codificacion_actividades VALUES (9,'comboLibros');
INSERT INTO codificacion_actividades VALUES (10,'nuevoLibro');
INSERT INTO codificacion_actividades VALUES (11,'aplicarCambios');
INSERT INTO codificacion_actividades VALUES (12,'eliminarLibro');
INSERT INTO codificacion_actividades VALUES (13,'actualizadoTitulo');
INSERT INTO codificacion_actividades VALUES (14,'actualizadoGenero');
INSERT INTO codificacion_actividades VALUES (15,'actualizadoFechaEdicion');
INSERT INTO codificacion_actividades VALUES (16,'actualizadoNumeroPaginas');
INSERT INTO codificacion_actividades VALUES (17,'actualizadoPremiado');
INSERT INTO codificacion_actividades VALUES (18,'op_menu - Estadisticas Actividad');
INSERT INTO codificacion_actividades VALUES (19,'consultarEstadistica');
INSERT INTO codificacion_actividades VALUES (20,'op_menu - Vista Unica Tabla');
INSERT INTO codificacion_actividades VALUES (21,'reordenar');
INSERT INTO codificacion_actividades VALUES (22,'insertarFila');
INSERT INTO codificacion_actividades VALUES (23,'cancelarInsercionFila');
INSERT INTO codificacion_actividades VALUES (24,'guardarFilaInsertada');
INSERT INTO codificacion_actividades VALUES (25,'eliminarFilaSeleccionada');
INSERT INTO codificacion_actividades VALUES (26,'actualizadaColumnaJTable');
INSERT INTO codificacion_actividades VALUES (27,'op_menu - Vista Paginada Tabla');
INSERT INTO codificacion_actividades VALUES (28,'botonPaginacionAnterior');
INSERT INTO codificacion_actividades VALUES (29,'botonPaginacionSiguiente');
INSERT INTO codificacion_actividades VALUES (30,'botonPaginacionNumerica');
INSERT INTO codificacion_actividades VALUES (31,'op_menu - CierreVentana');
INSERT INTO codificacion_actividades VALUES (32,'op_menu - Vista Arbol');
INSERT INTO codificacion_actividades VALUES (33,'Nuevo nodo');
INSERT INTO codificacion_actividades VALUES (34,'Editar nodo');
INSERT INTO codificacion_actividades VALUES (35,'Eliminar nodo');
INSERT INTO codificacion_actividades VALUES (36,'ratonClicked');
INSERT INTO codificacion_actividades VALUES (37,'cancelarEdicionNodo');
INSERT INTO codificacion_actividades VALUES (38,'op_menu - Configurar Documento');
INSERT INTO codificacion_actividades VALUES (39,'generarPDF');
INSERT INTO codificacion_actividades VALUES (40,'imprimir');
INSERT INTO codificacion_actividades VALUES (41,'copiarAlPortapapeles');

INSERT INTO generos VALUES ('0','Generalidades');
INSERT INTO generos VALUES ('1','Filosofia. Psicologia');
INSERT INTO generos VALUES ('2','Religion. Teologia');
INSERT INTO generos VALUES ('3','Ciencias sociales');
INSERT INTO generos VALUES ('4','No clasificado');
INSERT INTO generos VALUES ('5','Matematicas. Ciencias naturales');
INSERT INTO generos VALUES ('6','Ciencias aplicadas');
INSERT INTO generos VALUES ('7','Bellas artes. Deportes');
INSERT INTO generos VALUES ('8','Literatura');
INSERT INTO generos VALUES ('9','Geografia. Historia');

INSERT INTO libros VALUES (LPAD(FORMAT(secuencia_next_valor("secuencia_libros"),0),5,'0'),'EL QUIJOTE','8','1987-04-12 00:00:00',890,1);
INSERT INTO libros VALUES (LPAD(FORMAT(secuencia_next_valor("secuencia_libros"),0),5,'0'),'PROGRAMACION ORIENTADA A OBJETOS EN JAVA','0','2019-12-11 00:00:00',588,0);
INSERT INTO libros VALUES (LPAD(FORMAT(secuencia_next_valor("secuencia_libros"),0),5,'0'),'POESIAS COMPLETAS','8','2006-06-18 00:00:00',540,1);
INSERT INTO libros VALUES (LPAD(FORMAT(secuencia_next_valor("secuencia_libros"),0),5,'0'),'CAMPOS DE CASTILLA','8','2006-06-18 00:00:00',289,1);
INSERT INTO libros VALUES (LPAD(FORMAT(secuencia_next_valor("secuencia_libros"),0),5,'0'),'HAMLET','8','1987-09-01 00:00:00',452,1);
INSERT INTO libros VALUES (LPAD(FORMAT(secuencia_next_valor("secuencia_libros"),0),5,'0'),'FUENTEOVEJUNA','8','1973-11-12 00:00:00',650,1);
INSERT INTO libros VALUES (LPAD(FORMAT(secuencia_next_valor("secuencia_libros"),0),5,'0'),'CUENTOS DE LOS HERMANOS GRIMM','8','1990-04-22 00:00:00',357,0);
INSERT INTO libros VALUES (LPAD(FORMAT(secuencia_next_valor("secuencia_libros"),0),5,'0'),'LOS IBEROS','9','1984-08-05 00:00:00',259,0);
INSERT INTO libros VALUES (LPAD(FORMAT(secuencia_next_valor("secuencia_libros"),0),5,'0'),'HISTORIA DE ROMA','9','1968-09-12 00:00:00',536,0);
INSERT INTO libros VALUES (LPAD(FORMAT(secuencia_next_valor("secuencia_libros"),0),5,'0'),'JULIO CESAR','9','2001-11-22 00:00:00',380,0);
INSERT INTO libros VALUES (LPAD(FORMAT(secuencia_next_valor("secuencia_libros"),0),5,'0'),'MANUAL DE AUTOAYUDA','1','2018-04-22 00:00:00',540,0);
INSERT INTO libros VALUES (LPAD(FORMAT(secuencia_next_valor("secuencia_libros"),0),5,'0'),'LA FOTOSINTESIS','5','2005-09-30 00:00:00',309,0);
INSERT INTO libros VALUES (LPAD(FORMAT(secuencia_next_valor("secuencia_libros"),0),5,'0'),'GENETICA','5','2007-12-02 00:00:00',519,0);
INSERT INTO libros VALUES (LPAD(FORMAT(secuencia_next_valor("secuencia_libros"),0),5,'0'),'LA CELULA','5','2001-11-12 00:00:00',648,0);
INSERT INTO libros VALUES (LPAD(FORMAT(secuencia_next_valor("secuencia_libros"),0),5,'0'),'EL SISTEMA LINFATICO','6','2005-04-15 00:00:00',719,0);
INSERT INTO libros VALUES (LPAD(FORMAT(secuencia_next_valor("secuencia_libros"),0),5,'0'),'EL APARATO DIGESTIVO','6','1998-08-12 00:00:00',817,0);
INSERT INTO libros VALUES (LPAD(FORMAT(secuencia_next_valor("secuencia_libros"),0),5,'0'),'MANUAL DE FISIOTERAPIA','6','2014-08-29 00:00:00',790,0);
INSERT INTO libros VALUES (LPAD(FORMAT(secuencia_next_valor("secuencia_libros"),0),5,'0'),'NEUROLOGIA','6','2010-12-19 00:00:00',583,0);
INSERT INTO libros VALUES (LPAD(FORMAT(secuencia_next_valor("secuencia_libros"),0),5,'0'),'TRAUMATOLOGIA','6','2009-10-23 00:00:00',490,0);
INSERT INTO libros VALUES (LPAD(FORMAT(secuencia_next_valor("secuencia_libros"),0),5,'0'),'LOS MISERABLES','8','1989-12-09 00:00:00',590,0);
INSERT INTO libros VALUES (LPAD(FORMAT(secuencia_next_valor("secuencia_libros"),0),5,'0'),'LA VUELTA AL MUNDO EN 80 DIAS','8','1985-04-10 00:00:00',457,0);
INSERT INTO libros VALUES (LPAD(FORMAT(secuencia_next_valor("secuencia_libros"),0),5,'0'),'LOS TRES MOSQUETEROS','8','1981-04-03 00:00:00',615,0);
INSERT INTO libros VALUES (LPAD(FORMAT(secuencia_next_valor("secuencia_libros"),0),5,'0'),'VIAJE AL CENTRO DE LA TIERRA','8','2000-04-30 00:00:00',403,0);
INSERT INTO libros VALUES (LPAD(FORMAT(secuencia_next_valor("secuencia_libros"),0),5,'0'),'HISTORIA DE DOS CIUDADES','8','1980-11-12 00:00:00',369,0);
INSERT INTO libros VALUES (LPAD(FORMAT(secuencia_next_valor("secuencia_libros"),0),5,'0'),'LA ILIADA','8','1987-04-12 00:00:00',245,'0');
INSERT INTO libros VALUES (LPAD(FORMAT(secuencia_next_valor("secuencia_libros"),0),5,'0'),'MIGUEL STROGOFF','8','2010-04-25 00:00:00',481,0);
INSERT INTO libros VALUES (LPAD(FORMAT(secuencia_next_valor("secuencia_libros"),0),5,'0'),'OLIVER TWIST','8','1987-08-10 00:00:00',340,0);
INSERT INTO libros VALUES (LPAD(FORMAT(secuencia_next_valor("secuencia_libros"),0),5,'0'),'LA ODISEA','8','1986-08-03 00:00:00',290,0);
INSERT INTO libros VALUES (LPAD(FORMAT(secuencia_next_valor("secuencia_libros"),0),5,'0'),'GUERRA Y PAZ','8','1987-11-12 00:00:00',810,0);
INSERT INTO libros VALUES (LPAD(FORMAT(secuencia_next_valor("secuencia_libros"),0),5,'0'),'EL LAZARILLO DE TORMES','8','1983-04-22 00:00:00',358,0);

COMMIT;
