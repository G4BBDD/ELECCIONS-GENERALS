package Importar;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.*;

import static java.nio.charset.StandardCharsets.*;

public class ImportarPersones {
    public static void main() {
        System.out.println("Importando Personas...");
        String filePath = "C:\\Users\\34622\\Desktop\\02201911_MESA\\04021911.DAT"; // Ruta del archivo .DAT
        String dbURL = "jdbc:mysql://192.168.56.103:3306/eleccions_generals?useUnicode=true&characterEncoding=utf-8"; // URL de la BBDD real
        //String dbURL = "jdbc:mysql://10.2.157.223:3306/eleccions_generals?useUnicode=true&characterEncoding=utf-8"; // URL de la BBDD
        String usuari = "perepi"; // Nombre de usuario de MySQL
        String contrasenya = "pastanaga"; // Contraseña de MySQL

        try (Connection conn = DriverManager.getConnection(dbURL, usuari, contrasenya)) {

            String query = "INSERT INTO persones (nom, cognom1, cognom2, sexe, data_naixement, dni) "
                    + "SELECT ?, ?, ?, ?, ?, ?";
            PreparedStatement statement = conn.prepareStatement(query);

            BufferedReader reader = new BufferedReader(new FileReader(filePath, StandardCharsets.ISO_8859_1));

            String line;
            while ((line = reader.readLine()) != null) {
                String nom = line.substring(25,50);
                String cognom1 = line.substring(50,75);
                String cognom2 = line.substring(75,100);
                String sexe = line.substring(100,101);
                String dia = line.substring(101,103);
                String mes = line.substring(103,105);
                String any = line.substring(105,109);
                String data_naixement = null;
                if (!dia.equals("00") && !mes.equals("00") && !any.equals("0000")) {
                    data_naixement = any + "-" + mes + "-" + dia;
                }
                String dni = null;
                if (line.substring(110,119).equals("000000000"));


                // Insertar los valores en la base de datos si se cumplen las condiciones
                statement.setString(1,nom);
                statement.setString(2,cognom1);
                statement.setString(3,cognom2);
                statement.setString(4,sexe);
                if (data_naixement != null) {
                    statement.setString(5, data_naixement);
                } else {
                    statement.setNull(5, Types.DATE);
                }
                statement.setString(6,dni);

                statement.executeUpdate(); // Ejecutar la sentencia SQL

            }

            reader.close();

            System.out.println("Importación realizada con éxito");
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}

