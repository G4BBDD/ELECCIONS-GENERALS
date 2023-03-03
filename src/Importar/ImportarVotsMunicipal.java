package Importar;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class ImportarVotsMunicipal {
    public static void main() {
        System.out.println("Importando Vots Mun...");
        String filePath = "C:\\Users\\34622\\Desktop\\02201911_MESA\\06021911.DAT"; // Ruta del archivo .DAT
        //String dbURL = "jdbc:mysql://10.2.157.223:3306/eleccions_generals?useUnicode=true&characterEncoding=utf-8"; // URL de la BBDD real
        String dbURL = "jdbc:mysql://192.168.56.103:3306/eleccions_generals?useUnicode=true&characterEncoding=utf-8"; // URL de la BBDD real
        String usuari = "perepi"; // Nombre de usuario de MySQL
        String contrasenya = "pastanaga"; // Contraseña de MySQL

        try (Connection conn = DriverManager.getConnection(dbURL, usuari, contrasenya);
             Statement stmt = conn.createStatement()) {

            // Preparar consulta para obtener municipi_id
            PreparedStatement pstmtMunicipi = conn.prepareStatement(
                    "SELECT municipi_id FROM municipis WHERE codi_ine = ?");
            pstmtMunicipi.setString(1, "");

            // Preparar consulta para obtener candidatura_id
            PreparedStatement pstmtCandidatura = conn.prepareStatement(
                    "SELECT candidatura_id FROM candidatures WHERE codi_candidatura = ?");
            pstmtCandidatura.setString(1, "");

            // Leer el archivo .DAT
            try (Scanner scanner = new Scanner(new File(filePath))) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();

                    // Obtener el codi_ine y codi_candidatura
                    String codi_ine = line.substring(11, 14).trim();
                    String codi_candidatura = line.substring(16, 22).trim();

                    // Obtener el municipi_id
                    pstmtMunicipi.setString(1, codi_ine);
                    ResultSet rsMunicipi = pstmtMunicipi.executeQuery();
                    int municipi_id = -1;
                    if (rsMunicipi.next()) {
                        municipi_id = rsMunicipi.getInt("municipi_id");
                    }

                    // Obtener el candidatura_id
                    pstmtCandidatura.setString(1, codi_candidatura);
                    ResultSet rsCandidatura = pstmtCandidatura.executeQuery();
                    int candidatura_id = -1;
                    if (rsCandidatura.next()) {
                        candidatura_id = rsCandidatura.getInt("candidatura_id");
                    }

                    // Obtener los vots
                    int vots = Integer.parseInt(line.substring(23, 30));

                    // Insertar el registro en la tabla vots_candidatures_mun
                    String sqlInsert = String.format("INSERT INTO vots_candidatures_mun (eleccio_id, municipi_id, candidatura_id, vots) VALUES (1, %d, %d, %d)", municipi_id, candidatura_id, vots);
                    stmt.executeUpdate(sqlInsert);
                }
            }
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("Importació realitzada amb èxit");
    }
}
