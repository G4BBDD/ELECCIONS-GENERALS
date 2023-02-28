ALTER TABLE comunitats_autonomes DROP INDEX uk_comunitats_autonomes_codi_ine;
ALTER TABLE comunitats_autonomes ADD INDEX uk_comunitats_autonomes_codi_ine (codi_ine);

ALTER TABLE municipis DROP INDEX uk_municipis_codi_ine;
ALTER TABLE municipis ADD INDEX uk_municipis_codi_ine (codi_ine);

ALTER TABLE persones MODIFY COLUMN data_naixement DATE NULL;

ALTER TABLE persones DROP INDEX uk_persones_dni;
ALTER TABLE persones ADD INDEX uk_persones_dni_nom (dni,nom);

INSERT INTO eleccions (nom,data)
	VALUES("Eleccions Generals 2019","2019-11-10")
