/*Aquesta consulta recursiva ens retorna tots els municipis que pertanyen a la mateixa comunitat autònoma que el municipi inicial que hem seleccionat*/
WITH RECURSIVE municipis_comunitat AS (
	SELECT m.municipi_id, m.nom, p.codi_ine, p.provincia_id, m.districte
		FROM municipis m
			INNER JOIN provincies p ON p.provincia_id = m.provincia_id
				WHERE p.codi_ine = '08' /* Utilitzarem el cas de Catalunya*/
				
	UNION ALL
    
	SELECT m.municipi_id, m.nom, m.codi_ine, m.provincia_id, m.districte
		FROM municipis m
			INNER JOIN municipis_comunitat mc ON m.provincia_id = mc.provincia_id
				WHERE m.codi_ine != '08' /* Excloem els municipis que ja hem inclòs en la consulta */
)
SELECT *
FROM municipis_comunitat;
