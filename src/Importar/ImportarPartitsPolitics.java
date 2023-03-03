package Importar;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ImportarPartitsPolitics {
    public static void main() {
        System.out.println("Importando Partidos politicos...");
        String filePath = "C:\\Users\\34622\\Desktop\\02201911_MESA\\03021911.DAT"; // Ruta del archivo .DAT
        String dbURL = "jdbc:mysql://192.168.56.103:3306/eleccions_generals?useUnicode=true&characterEncoding=utf-8"; // URL de la BBDD real
        //String dbURL = "jdbc:mysql://10.2.157.223:3306/eleccions_generals?useUnicode=true&characterEncoding=utf-8"; // URL de la BBDD
        String usuari = "perepi"; // Nombre de usuario de MySQL
        String contrasenya = "pastanaga"; // Contraseña de MySQL

        try (Connection conn = DriverManager.getConnection(dbURL, usuari, contrasenya)) {

            String query = "INSERT INTO candidatures (eleccio_id, codi_candidatura, nom_curt, nom_llarg, codi_acumulacio_provincia, codi_acumulacio_ca, codi_acumulacio_nacional) "
                    + "SELECT ?, ?, ?, ?, ?, ?, ? ";
            PreparedStatement statement = conn.prepareStatement(query);

            BufferedReader reader = new BufferedReader(new FileReader(filePath, StandardCharsets.ISO_8859_1));
            String line;
            while ((line = reader.readLine()) != null) {
                String eleccio_id = String.valueOf("1");
                String codi_candidatura = line.substring(8, 14);
                String nom_curt = line.substring(14,64);
                String nom_llarg = line.substring(64,214);
                String codi_acumulacio_provincia = line.substring(214,220);
                String codi_acumulacio_ca = line.substring(220,226);
                String codi_acumulacio_nacional = line.substring(226,232);

                // Insertar los valores en la base de datos si se cumplen las condiciones
                statement.setString(1,eleccio_id);
                statement.setString(2,codi_candidatura);
                statement.setString(3,nom_curt);
                statement.setString(4,nom_llarg);
                statement.setString(5,codi_acumulacio_provincia);
                statement.setString(6,codi_acumulacio_ca);
                statement.setString(7,codi_acumulacio_nacional);
                statement.executeUpdate(); // Ejecutar la sentencia SQL

            }

            reader.close();

            System.out.println("Importación realizada con éxito");
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
