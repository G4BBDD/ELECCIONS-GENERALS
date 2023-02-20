DROP DATABASE IF EXISTS eleccions_generals;
CREATE DATABASE eleccions_generals;

USE eleccions_generals;

CREATE TABLE comunitats_autonomes (
    comunitat_aut_id	TINYINT UNSIGNED AUTO_INCREMENT,
    nom 				VARCHAR(45),
    codi_ine			CHAR(2) NOT NULL,
    CONSTRAINT pk_comunitats_autonomes PRIMARY KEY (comunitat_aut_id),
    CONSTRAINT uk_comunitats_autonomes_codi_ine    UNIQUE KEY (codi_ine)
);

CREATE TABLE provincies (
    provincia_id 		TINYINT UNSIGNED AUTO_INCREMENT,
    comunitat_aut_id	TINYINT UNSIGNED,
    nom 				VARCHAR (45),
    codi_ine 			CHAR(2) NOT NULL,
    num_escons 			TINYINT UNSIGNED,
    CONSTRAINT pk_provincies PRIMARY KEY (provincia_id),
    CONSTRAINT uk_provincies_codi_ine UNIQUE KEY (codi_ine),
    CONSTRAINT fk_provincies_comunitats_autonomes FOREIGN KEY(comunitat_aut_id)
		REFERENCES comunitats_autonomes (comunitat_aut_id)
);

CREATE TABLE municipis (
    municipi_id 		SMALLINT UNSIGNED AUTO_INCREMENT,
    nom 				VARCHAR(100),
    codi_ine 			CHAR(3) NOT NULL,
    provincia_id 		TINYINT UNSIGNED NOT NULL,
    districte 			CHAR(2),
    CONSTRAINT pk_municipis PRIMARY KEY (municipi_id),
    CONSTRAINT uk_municipis_codi_ine UNIQUE KEY (codi_ine),
    CONSTRAINT fk_municipis_provincies FOREIGN KEY (provincia_id)
		REFERENCES provincies (provincia_id)
);

CREATE TABLE eleccions (
	eleccio_id		TINYINT UNSIGNED AUTO_INCREMENT,
    nom				VARCHAR(45),
    data			DATE,
    any				YEAR GENERATED ALWAYS AS (YEAR(data)) NOT NULL,
    mes				TINYINT GENERATED ALWAYS AS (MONTH(data)) NOT NULL,
    CONSTRAINT pk_eleccions PRIMARY KEY (eleccio_id)
);

CREATE TABLE candidatures (
	candidatura_id					INT UNSIGNED AUTO_INCREMENT,
	eleccio_id						TINYINT UNSIGNED NOT NULL,
    codi_candidatura				CHAR (6),
    nom_curt						VARCHAR (50),
    nom_llarg						VARCHAR (150),
    codi_acumulacio_provincia		CHAR (6),
    codi_acumulacio_ca				CHAR (6),
    codi_acumulacio_nacional		CHAR (6),
CONSTRAINT pk_candidatures PRIMARY KEY (candidatura_id),
CONSTRAINT fk_candidatures_eleccions FOREIGN KEY (eleccio_id)
	REFERENCES eleccions (eleccio_id)
);

CREATE TABLE persones (
	persona_id				INT UNSIGNED AUTO_INCREMENT,
    nom						VARCHAR (30),
    cognom1					VARCHAR (30),
    cognom2					VARCHAR (30),
    sexe					ENUM('M','F') COMMENT "M=Masculí, F=Femení",
    data_naixement			DATE,
    dni						CHAR (10) NOT NULL,
CONSTRAINT pk_persones PRIMARY KEY (persona_id),
CONSTRAINT uk_persones_dni UNIQUE KEY (dni)
);

CREATE TABLE candidats (
	candidat_id					BIGINT UNSIGNED AUTO_INCREMENT,
	candidatura_id				INT UNSIGNED NOT NULL,
    persona_id					INT UNSIGNED NOT NULL,
    provincia_id				TINYINT UNSIGNED NOT NULL,
	num_ordre					TINYINT COMMENT "Num ordre del candidatdins la llista del partit dins de la circumpscripció que es presenta.",
	tipus						ENUM('T','S') COMMENT "T=Titular, S=Suplent",
CONSTRAINT pk_candidats PRIMARY KEY (candidat_id),
CONSTRAINT fk_candidats_candidatures FOREIGN KEY (candidatura_id)
	REFERENCES candidatures (candidatura_id),
CONSTRAINT fk_persona_persones FOREIGN KEY (persona_id)
	REFERENCES persones (persona_id),
CONSTRAINT fk_provincia_provincies FOREIGN KEY (provincia_id)
	REFERENCES provincies (provincia_id)
);

CREATE TABLE vots_candidatures_ca(
	comunitat_autonoma_id 	TINYINT UNSIGNED,
	candidatura_id 			INT UNSIGNED,
	vots 					INT UNSIGNED,
	CONSTRAINT fk_vots_candidatures_ca_candidatures FOREIGN KEY (candidatura_id)
		REFERENCES candidatures (candidatura_id),
	CONSTRAINT fk_vots_candidatures_ca_comunitats_autonomes FOREIGN KEY (comunitat_autonoma_id)
		REFERENCES comunitats_autonomes (comunitat_aut_id)
);

CREATE TABLE eleccions_municipis (
	eleccio_id 			TINYINT UNSIGNED NOT NULL,
    municipi_id 		SMALLINT UNSIGNED NOT NULL,
    num_meses 			SMALLINT UNSIGNED,
    cens 				INT UNSIGNED,
    vots_emesos 		INT UNSIGNED COMMENT "Número total de vots realitzats en el municipi" ,
    vots_valids 		INT UNSIGNED COMMENT "Número de vots es que tindran en compte: vots a candidatures + vots nuls",
    vots_candidatures 	INT UNSIGNED COMMENT "Total de vots a les candidatures",
    vots_blanc 			INT UNSIGNED,
    vots_nuls 			INT UNSIGNED,
    CONSTRAINT fk_eleccions_municipis_eleccions FOREIGN KEY (eleccio_id)
		REFERENCES eleccions (eleccio_id),
	CONSTRAINT fk_eleccions_municipis_municipis FOREIGN KEY (municipi_id)
		REFERENCES municipis (municipi_id)
);

CREATE TABLE vots_candidatures_mun(
	eleccio_id 			TINYINT UNSIGNED NOT NULL,
	municipi_id 		SMALLINT UNSIGNED NOT NULL,
	candidatura_id		INT UNSIGNED NOT NULL,
	vots 				INT UNSIGNED,
	CONSTRAINT fk_vots_candidatures_mun_candidatures FOREIGN KEY (candidatura_id)
		REFERENCES candidatures (candidatura_id),
	CONSTRAINT fk_vots_candidatures_mun_munipis FOREIGN KEY (municipi_id)
		REFERENCES municipis (municipi_id),
	CONSTRAINT fk_vots_candidatures_mun_eleccions FOREIGN KEY (eleccio_id)
		REFERENCES eleccions (eleccio_id)
	);
    
CREATE TABLE vots_candidatures_prov(
	provincia_id TINYINT UNSIGNED NOT NULL,
	candidatura_id INT UNSIGNED NOT NULL,
	vots INT UNSIGNED COMMENT "Número de vots obtinguts per la candidatura",
    candidats_obtinguts SMALLINT UNSIGNED COMMENT "Número de candidats obtinguts per la candidatura",
	CONSTRAINT fk_vots_candidatures_prov_candidatures FOREIGN KEY (candidatura_id)
		REFERENCES candidatures (caNdidatura_id),
	CONSTRAINT fk_vots_candidatures_prov_provincies FOREIGN KEY (provincia_id)
		REFERENCES provincies (provincia_id)
	);
    
