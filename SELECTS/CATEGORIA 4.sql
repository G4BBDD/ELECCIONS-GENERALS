/* Mostra el nombre de municipis per província, ordenats per nombre de municipis de més gran a més petit. */

WITH RECURSIVE contar_municipios AS (
    SELECT provincia_id, COUNT(*) AS num_municipios
		FROM municipis m
			GROUP BY provincia_id
    UNION
    SELECT pr.provincia_id, 0
		FROM provincies pr
			INNER JOIN contar_municipios c ON pr.provincia_id = c.provincia_id
				WHERE c.provincia_id IS NULL
)
SELECT pr.nom, cm.num_municipios
	FROM contar_municipios cm
		JOIN provincies pr ON cm.provincia_id = pr.provincia_id
			ORDER BY num_municipios DESC;
