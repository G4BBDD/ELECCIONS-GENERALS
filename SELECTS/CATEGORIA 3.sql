/*Fer recompte dels candidats suplents majors de 30 anys*/
SELECT COUNT(CURDATE() - data_naixement > 30) AS 'Suplents majors de 30' FROM persones
	WHERE (SELECT tipus FROM candidats
			WHERE tipus = 'S');

/*Seleccionar tots els municipis de la provincia de Barcelona*/
SELECT nom AS municipis FROM municipis
	WHERE provincia_id = (SELECT provincia_id FROM provincies
			WHERE nom = 'Barcelona');

/*Seleccionar a quina provincia pertany el candidat més jove*/
SELECT nom AS provincia FROM provincies
	WHERE provincia_id = (SELECT provincia_id FROM candidats
							WHERE persona_id = (SELECT persona_id FROM persones
													WHERE data_naixement = (SELECT data_naixement FROM persones
														ORDER BY data_naixement ASC LIMIT 1)));

/*Seleccionar la candidatura amb més vots a la comunitat autònoma de Madrid (id = 12)*/
SELECT nom_curt AS candidatura FROM candidatures
	WHERE candidatura_id = (SELECT candidatura_id FROM vots_candidatures_ca
								WHERE comunitat_autonoma_id = 12
									ORDER BY vots DESC LIMIT 1);

/*Seleccionar tots els municipis de la Comunitat Valenciana (id = 17) que comencin amb la lletra D*/
SELECT nom AS municipis FROM municipis
	WHERE provincia_id = (SELECT provincia_id FROM provincies
							WHERE comunitat_aut_id = 17 AND nom LIKE 'D%');
                            
                            
