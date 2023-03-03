/* Mostra quants homes i quantes dones hi han en la BD */ 
SELECT SUM(sexe = 'M') AS Homes , SUM(sexe = 'F') AS Dones
	FROM persones;


/* Mostra el municipi amb mes vots de tots !!!! */
SELECT municipi_id, MAX(vots) AS Municipi_Max_Vots
	FROM vots_candidatures_mun
		GROUP BY municipi_id
			ORDER BY Municipi_Max_Vots DESC 
				LIMIT 1;


/* Mostra el numero de candidats que hi han per cada provincia */ 
SELECT provincia_id AS Provincia, COUNT(candidat_id) AS Candidats
	FROM candidats
		GROUP BY provincia_id;


/* Mostra el nom complet mès curt de la base de dades */
SELECT MIN(CONCAT(p.nom, ' ', p.cognom1, ' ', p.cognom2)) AS Nom_Curt
	FROM persones p;
        

/* Mostra el nom, el cognom de les persones el nom dels continguin 3 vocals seguidas*/ 
	SELECT nom, cognom1
		FROM persones 
			  WHERE nom RLIKE '[aeiou]{3}';




