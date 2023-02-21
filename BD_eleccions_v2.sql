ALTER TABLE comunitats_autonomes DROP INDEX uk_comunitats_autonomes_codi_ine;
ALTER TABLE comunitats_autonomes ADD INDEX uk_comunitats_autonomes_codi_ine (codi_ine);
