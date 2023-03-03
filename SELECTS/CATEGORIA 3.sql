
/*Obten el nombre total de vots vàlids a les eleccions municipals de la Comunitat Valenciana el 2019*/
SELECT SUM(vots_valids) AS total_votos_validos 
	FROM eleccions_municipis em
		INNER JOIN municipis m ON m.municipi_id = em.municipi_id 
			WHERE em.eleccio_id = (SELECT eleccio_id FROM eleccions WHERE any = 2019) 
													AND 
												m.provincia_id IN 
								(SELECT comunitat_aut_id FROM comunitats_autonomes WHERE nom = 'Comunitat Valenciana');


/*Seleccionar tots els municipis de la provincia de Barcelona*/
SELECT nom AS "Municipis" 
	FROM municipis
		WHERE provincia_id = (SELECT provincia_id 
				      		FROM provincies
							WHERE nom = 'Barcelona');

/*Seleccionar a quina provincia pertany el candidat més jove*/
SELECT nom AS "Provincia" 
	FROM provincies
		WHERE provincia_id = (SELECT provincia_id 
						FROM candidats
							WHERE persona_id = (SELECT persona_id 
										FROM persones
										     WHERE data_naixement = (SELECT data_naixement 
														FROM persones
														     ORDER BY data_naixement ASC LIMIT 1)));

/*Seleccionar la candidatura amb més vots a la comunitat autònoma de Madrid (id = 12)*/
SELECT nom_curt AS "Candidatura" 
		FROM candidatures
			WHERE candidatura_id = (SELECT candidatura_id 
							FROM vots_candidatures_ca
								WHERE comunitat_aut_id = 12
									ORDER BY vots DESC LIMIT 1);

/*Obtén el nombre de candidatures presentades per cada partit polític a les eleccions generals de 2019*/
SELECT nom_curt, COUNT(*) AS num_candidaturas 
	FROM candidatures
		WHERE eleccio_id = (SELECT eleccio_id FROM eleccions WHERE any = 2019) 
			GROUP BY nom_curt;

