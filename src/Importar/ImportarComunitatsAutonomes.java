package Importar;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ImportarComunitatsAutonomes {
    public static void main() {
        System.out.println("Important comunitats autònomes...");
        String filePath = "C:\\Users\\34622\\Desktop\\02201911_MESA\\07021911.DAT"; // Ruta del archivo .DAT
        //String dbURL = "jdbc:mysql://10.2.157.223:3306/eleccions_generals?useUnicode=true&characterEncoding=utf-8"; // URL de la BBDD real
        String dbURL = "jdbc:mysql://192.168.56.103:3306/eleccions_generals?useUnicode=true&characterEncoding=utf-8"; // URL de la BBDD real
        String usuari = "perepi"; // Nombre de usuario de MySQL
        String contrasenya = "pastanaga"; // Contraseña de MySQL

        try (Connection conn = DriverManager.getConnection(dbURL, usuari, contrasenya)) {

            String query = "INSERT INTO comunitats_autonomes (codi_ine, nom) "
                    + "SELECT ?, ? "
                    + "WHERE NOT EXISTS (SELECT 1 FROM comunitats_autonomes WHERE nom = ?)";
            PreparedStatement statement = conn.prepareStatement(query);

            BufferedReader reader = new BufferedReader(new FileReader(filePath, StandardCharsets.ISO_8859_1));
            String line;
            while ((line = reader.readLine()) != null) {
                String codi_ine = line.substring(9, 11).trim();
                String nom = line.substring(14, 64).trim();
                String num = line.substring(11,13).trim();

                // Insertar los valores en la base de datos si se cumplen las condiciones
                if (num.equals("99") && !nom.equals("Total nacional")) {
                    statement.setString(1, codi_ine);
                    statement.setString(2, nom);
                    statement.setString(3, nom);
                    statement.executeUpdate(); // Ejecutar la sentencia SQL
                }
            }

            reader.close();

            System.out.println("Importació realitzada amb èxit");
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
