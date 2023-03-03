/*Mostrar la quantitat de candidats per cada municipi i ordenals pels candidats de forma descendent */

SELECT m.nom AS Municipi, m.municipi_id AS Municipi_id, COUNT(c.candidat_id) AS Candidats
	FROM municipis m
			INNER JOIN provincies p ON p.provincia_id = m.provincia_id
            		INNER JOIN candidats c ON c.provincia_id = p.provincia_id
						GROUP BY m.municipi_id
							ORDER BY candidats DESC;



/* Mostra la quantitat de cada tipus de vots al mes de Juliol */

SELECT em.vots_emesos AS "Vots emesos", em.vots_candidatures AS "Vots candidatures", em.vots_valids AS "Vots valids", em.vots_blanc AS 'Vots en blanc', em.vots_nuls AS "Vots nuls"
    FROM eleccions_municipis em
		INNER JOIN eleccions e ON e.eleccio_id = em.eleccio_id
			WHERE e.mes = 7;
            

/* Mostra la quantitat de candidats per cada comunitat autònoma i la quantitat de vots */

SELECT c.comunitat_aut_id AS Comunitat_Aut_Id, c.nom AS Comunitat_Autonoma, vo.vots AS Vots, COUNT(DISTINCT ca.candidat_id) AS Candidats
	FROM comunitats_autonomes c
		INNER JOIN vots_candidatures_ca vo ON vo.comunitat_aut_id = c.comunitat_aut_id
       		INNER JOIN provincies p ON p.comunitat_aut_id = c.comunitat_aut_id
        	INNER JOIN candidats ca ON ca.provincia_id = p.provincia_id	
			GROUP BY c.comunitat_aut_id, c.nom, vo.vots
				ORDER BY vo.vots DESC;
                
/* Mostra els vots de cada comunitat autònoma, província i municipi */

SELECT voca.vots AS Comunitat_Autonomes, vop.vots AS Provincies, vom.vots AS Municipis
	FROM vots_candidatures_ca voca
		INNER JOIN comunitats_autonomes c ON c.comunitat_aut_id = voca.comunitat_aut_id
		INNER JOIN provincies p ON p.comunitat_aut_id = c.comunitat_aut_id
		RIGHT JOIN vots_candidatures_prov vop ON vop.provincia_id = p.provincia_id
		INNER JOIN municipis m ON m.provincia_id = p.provincia_id
		RIGHT JOIN vots_candidatures_mun vom ON vom.municipi_id = m.municipi_id
			GROUP BY voca.vots, vop.vots, vom.vots;

/* Obtén el nom complet del candidat, el seu partit i la província a què es presenta en una elecció específica. */

SELECT CONCAT(p.nom, ' ', p.cognom1, ' ', p.cognom2) AS nom_complet,  cd.nom_curt AS partit, pr.nom AS provincia 
	FROM candidats ca 
		INNER JOIN persones p ON ca.persona_id = p.persona_id 
		INNER JOIN candidatures cd ON cd.candidatura_id = ca.candidatura_id 
		INNER JOIN provincies AS pr ON ca.provincia_id = pr.provincia_id
			WHERE cd.eleccio_id = 1;





			
