package Importar;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ImportarCandidats {

    public static void main() {
        //String dbURL = "jdbc:mysql://192.168.56.103:3306/eleccions_generals?useUnicode=true&characterEncoding=utf-8";
        String dbURL = "jdbc:mysql://10.2.157.223:3306/eleccions_generals?useUnicode=true&characterEncoding=utf-8"; // URL de la BBDD
        String usuari = "perepi";
        String contrasenya = "pastanaga";
        String filePath = "C:\\Users\\34622\\Desktop\\02201911_MESA\\04021911.DAT";
        System.out.println("Important Candidats...");
        try {
            // Conectarse a la base de datos
            Connection conexion = DriverManager.getConnection(dbURL, usuari, contrasenya);

            // Preparar la sentencia SQL para insertar los datos
            String sql = "INSERT INTO candidats (candidatura_id, persona_id, provincia_id, num_ordre, tipus) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = conexion.prepareStatement(sql);

            // Preparar la sentencia SQL para buscar la persona_id en la tabla persones
            String sql_buscar_persona_id = "SELECT persona_id FROM persones WHERE nom = ? AND cognom1 = ? AND cognom2 = ?";
            PreparedStatement statement_buscar_persona_id = conexion.prepareStatement(sql_buscar_persona_id);

            // Preparar la sentencia SQL para buscar el codi_candidatura en la tabla candidatures
            String sql_buscar_candidatura_id = "SELECT candidatura_id FROM candidatures WHERE codi_candidatura = ?";
            PreparedStatement statement_buscar_candidatura_id = conexion.prepareStatement(sql_buscar_candidatura_id);

            // Preparar la sentencia SQL para buscar la provincia_id en la tabla provincies
            String sql_buscar_provincia_id = "SELECT provincia_id FROM provincies WHERE codi_ine = ?";
            PreparedStatement statement_buscar_provincia_id = conexion.prepareStatement(sql_buscar_provincia_id);


            // Leer el archivo .DAT y procesar cada línea
            BufferedReader reader = new BufferedReader(new FileReader(filePath, StandardCharsets.ISO_8859_1));
            String line;
            while ((line = reader.readLine()) != null) {
                String codi_candidatura = line.substring(15, 21);
                String persona_id = "";
                String codi_ine = line.substring(9,11);
                String nom = line.substring(25, 50);
                String cognom1 = line.substring(50, 75);
                String cognom2 = line.substring(75, 100);
                String num_ordre = line.substring(23, 24).trim();
                String tipus = line.substring(119, 120).trim();

                // Si el tipus es "N", saltar al siguiente registro
                if (tipus.equals("N")) {
                    continue;
                }
                // Si el tipus no es "S", saltar al siguiente registro
                if (!tipus.equals("S")) {
                    continue;
                }

                // Buscar el ID de la persona en la tabla persones
                statement_buscar_persona_id.setString(1, nom);
                statement_buscar_persona_id.setString(2, cognom1);
                statement_buscar_persona_id.setString(3, cognom2);
                ResultSet result = statement_buscar_persona_id.executeQuery();
                if (result.next()) {
                    persona_id = result.getString("persona_id");
                } else {
                    // Manejar el caso en que no se encontró la persona en la tabla persones
                }
                result.close();


                // Buscar el ID de la candidatura en la tabla candidatures
                statement_buscar_candidatura_id.setString(1, codi_candidatura);
                ResultSet result_candidatura = statement_buscar_candidatura_id.executeQuery();
                String candidatura_id = null;
                if (result_candidatura.next()) {
                    candidatura_id = result_candidatura.getString("candidatura_id");
                }
                result_candidatura.close();

                // Buscar el ID de la provincia en la tabla provincies
                statement_buscar_provincia_id.setString(1, codi_ine);
                ResultSet result_provincia = statement_buscar_provincia_id.executeQuery();
                String provincia_id = null;
                if (result_provincia.next()) {
                    provincia_id = result_provincia.getString("provincia_id");
                }
                result_provincia.close();

                // Insertar los datos en la tabla
                // Insertar los datos en la tabla
                if (provincia_id != null) {
                    statement.setString(1, candidatura_id);
                    statement.setString(2, persona_id);
                    statement.setString(3, provincia_id);
                    statement.setString(4, num_ordre);
                    statement.setString(5, tipus);
                    statement.executeUpdate();
                }
            }

            // Cerrar recursos
            reader.close();
            statement.close();
            statement_buscar_persona_id.close();
            conexion.close();
            conexion.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        System.out.println("Importació realitzada amb èxit");
    }
}
